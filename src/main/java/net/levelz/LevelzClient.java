package net.levelz;

import net.levelz.init.*;
import net.levelz.network.PlayerStatsClientPacket;
import net.levelz.network.PlayerStatsServerPacket;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = LevelzClient.MODID)
public class LevelzClient {
    public static final String MODID = "LEVEL-Z";

    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MODID);

    @Mod.EventHandler
    public void onInitialize(FMLInitializationEvent event) {
        System.out.println("Initialized");

        KeyInit.init();
        PlayerStatsClientPacket.init();
        RenderInit.init();

        CommandInit.init();
        CompatInit.init();
        ConfigInit.init();
        CriteriaInit.init();
        EntityInit.init();
        EventInit.init();
        JsonReaderInit.init();
        PlayerStatsServerPacket.init();
        TagInit.init();
        ItemInit.init();
    }

}