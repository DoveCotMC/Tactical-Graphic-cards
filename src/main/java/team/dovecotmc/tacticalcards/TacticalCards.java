package team.dovecotmc.tacticalcards;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import team.dovecotmc.tacticalcards.config.CommonConfig;
import team.dovecotmc.tacticalcards.utils.RegistryHandler;

@Mod(TacticalCards.MOD_ID)
public class TacticalCards {
    public static final String MOD_ID = "tactical_cards";

    public TacticalCards() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.CFG);
        RegistryHandler.register(bus);
    }
}
