package team.dovecotmc.tacticalcards.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec CFG;
    public static final ForgeConfigSpec.BooleanValue destroysTerrain;
    public static final ForgeConfigSpec.BooleanValue causesFire;

    static {
        final var builder = new ForgeConfigSpec.Builder();
        builder.comment("Tactical Cards Settings").push("general");
        destroysTerrain = builder.comment("Set to true to make tactical cards' explosions break the terrain")
                .define("destroysTerrain", false);
        causesFire = builder.comment("Set to true to make tactical cards' explosions cause fire on nearby blocks")
                .define("causesFire", true);
        builder.pop();
        CFG = builder.build();
    }
}
