package net.detectivekaktus.client.render.gui;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;

import net.detectivekaktus.DotcConfig;
import net.detectivekaktus.DefenseOfTheCraft;
import net.detectivekaktus.attach.PlayerMana;
import net.detectivekaktus.attach.PlayerStats;

import net.detectivekaktus.client.core.DotcColors;

public class DotcStatusBar {
    private static final ResourceLocation STRENGTH_ICON = ResourceLocation.fromNamespaceAndPath(
            DefenseOfTheCraft.MOD_ID,
            "textures/gui/strength_icon.png"
    );
    private static final ResourceLocation AGILITY_ICON = ResourceLocation.fromNamespaceAndPath(
            DefenseOfTheCraft.MOD_ID,
            "textures/gui/agility_icon.png"
    );
    private static final ResourceLocation INTELLIGENCE_ICON = ResourceLocation.fromNamespaceAndPath(
            DefenseOfTheCraft.MOD_ID,
            "textures/gui/intelligence_icon.png"
    );

    private static final int HOTBAR_WIDTH = 182; // vanilla hotbar width
    private static final int HOTBAR_HEIGHT = 22; // vanilla hotbar height

    private static final int MANA_BAR_HEIGHT = 9;

    private static final int ICON_TO_TEXT_MARGIN = 13;
    private static final int STAT_TO_STAT_MARGIN = 25;
    private static final int STATS_TO_MANA_MARGIN = 10;

    private static int getStatusBarXPos(int width) {
        return (width - HOTBAR_WIDTH) / 2;
    }

    private static int getStatusBarYPos(int height) {
        return height - (int) (HOTBAR_HEIGHT * 3.25f);
    }

    private static void drawMana(GuiGraphics graphics, int statusBarStartX, int x1, int y1) {
        var client = Minecraft.getInstance();
        var mana = PlayerMana.get(client.player);
        var current = mana.getCurrentMana();
        var max = mana.getMaxMana();
        var manaPercent = max > 0 ? Math.clamp(current / max, 0, 1) : 0;

        var x2 = statusBarStartX + HOTBAR_WIDTH;
        var manaBarWidth = x2 - x1;
        var y2 = y1 + MANA_BAR_HEIGHT;
        graphics.fill(x1, y1, x2, y2, DotcColors.MANA_BAR_COLOR);

        var currentManaX = x1 + (int) (manaBarWidth * manaPercent);
        graphics.fill(x1, y1, currentManaX, y2, DotcColors.CURRENT_MANA_COLOR);

        var strX = (x1 + x2) / 2;
        var str = String.valueOf((int) current);
        graphics.drawCenteredString(client.font, str, strX, y1, DotcColors.TEXT_COLOR);
    }

    private static int drawIconAndValue(GuiGraphics graphics, ResourceLocation icon, int value, int x, int y) {
        var client = Minecraft.getInstance();
        graphics.blit(icon, x, y, 0, 0, 8, 8, 8, 8);

        var strX = x + ICON_TO_TEXT_MARGIN;
        var strValue = String.valueOf(value);
        graphics.drawString(client.font, strValue, strX, y, DotcColors.TEXT_COLOR);

        return strX + STAT_TO_STAT_MARGIN;
    }

    private static int drawStats(GuiGraphics graphics, int x, int y) {
        var client = Minecraft.getInstance();
        var stats = PlayerStats.get(client.player);
        x = drawIconAndValue(graphics, STRENGTH_ICON, stats.getStrength(), x, y);
        x = drawIconAndValue(graphics, AGILITY_ICON, stats.getAgility(), x, y);
        x = drawIconAndValue(graphics, INTELLIGENCE_ICON, stats.getIntelligence(), x, y);
        return x;
    }

    private static boolean shouldSkipDrawing(Minecraft client) {
        return client.options.hideGui
                || client.player == null
                || client.gameMode == null
                || client.gameMode.getPlayerMode() == GameType.CREATIVE
                || client.gameMode.getPlayerMode() == GameType.SPECTATOR;
    }

    public static void draw(GuiGraphics graphics, DeltaTracker tickCounter) {
        if (!DotcConfig.HANDLER.instance().showStatusBar)
            return;

        var client = Minecraft.getInstance();
        if (shouldSkipDrawing(client))
            return;

        var width = client.getWindow().getGuiScaledWidth();
        var height = client.getWindow().getGuiScaledHeight();
        var statusBarStartX = getStatusBarXPos(width);
        var statusBarStartY = getStatusBarYPos(height);

        var statusBarAfterStatsX = drawStats(graphics, statusBarStartX, statusBarStartY);
        statusBarAfterStatsX += STATS_TO_MANA_MARGIN;
        drawMana(graphics, statusBarStartX, statusBarAfterStatsX, statusBarStartY);
    }
}
