package net.levelz.init;

import net.levelz.config.LevelzConfig;
import net.levelz.network.PlayerStatsClientPacket;
import net.levelz.network.PlayerStatsServerPacket;
import net.minecraft.util.ActionResult;
import net.minecraftforge.fml.common.Loader;

public class ConfigInit {
//
    public static final boolean isOriginsLoaded = Loader.instance().isModLoaded("origins");
//
    public static LevelzConfig CONFIG = new LevelzConfig();
//
//    public static void init() {
//        AutoConfig.register(LevelzConfig.class, JanksonConfigSerializer::new);
//        CONFIG = AutoConfig.getConfigHolder(LevelzConfig.class).getConfig();
//
//        AutoConfig.getConfigHolder(LevelzConfig.class).registerSaveListener((manager, data) -> {
//            if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT) && !MinecraftClient.getInstance().isInSingleplayer()
//                    && MinecraftClient.getInstance().getNetworkHandler() != null)
//                PlayerStatsClientPacket.writeC2SSyncConfigPacket();
//            return ActionResult.SUCCESS;
//        });
//        AutoConfig.getConfigHolder(LevelzConfig.class).registerLoadListener((manager, newData) -> {
//            if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT) && !MinecraftClient.getInstance().isInSingleplayer()
//                    && MinecraftClient.getInstance().getNetworkHandler() != null)
//                PlayerStatsClientPacket.writeC2SSyncConfigPacket();
//            return ActionResult.SUCCESS;
//        });
//        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
//            // if (!server.isSingleplayer()) // set in sp too
//            server.execute(() -> {
//                PlayerStatsServerPacket.writeS2CConfigSyncPacket(handler.player, ConfigInit.CONFIG.getConfigList());
//            });
//
//        });
//
//    }

}