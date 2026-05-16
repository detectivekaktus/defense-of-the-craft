package net.detectivekaktus;

import com.google.gson.GsonBuilder;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.resources.ResourceLocation;

public class DotcConfig {
    public static ConfigClassHandler<DotcConfig> HANDLER = ConfigClassHandler.createBuilder(DotcConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "config"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(FabricLoader.getInstance().getConfigDir().resolve(DefenseOfTheCraft.MOD_ID + ".json5"))
                            .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                            .setJson5(true)
                            .build()
            )
            .build();

    @SerialEntry
    public boolean changeButtonSounds = false;
    @SerialEntry
    public boolean addScreenshotSound = false;
    @SerialEntry
    public boolean addWhisperingSound = false;
    @SerialEntry
    public boolean enableStreakAnnouncement = true;
}
