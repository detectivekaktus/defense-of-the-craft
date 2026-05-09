package net.detectivekaktus.core.player;

public enum ShadowWalkingSource {
    NONE(0),
    SHADOW_AMULET(1),
    SHADOW_BLADE(2),
    SILVER_EDGE(3);

    public final int id;

    ShadowWalkingSource(int id) {
        this.id = id;
    }

    public static ShadowWalkingSource fromId(int id) throws IllegalArgumentException {
        for (var val : ShadowWalkingSource.values()) {
            if (val.id == id)
                return val;
        }
        throw new IllegalArgumentException("Unknown id " + id);
    }
}
