package net.levelz.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.levelz.data.LevelLoader;
import net.levelz.network.PlayerStatsServerPacket;

public class JsonReaderInit {

    public static final Logger LOGGER = LogManager.getLogger("LevelZ");

    public static void init() {
//        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new LevelLoader());
//        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success) -> {
//            if (success) {
//                for (int i = 0; i < server.getPlayerManager().getPlayerList().size(); i++)
//                    PlayerStatsServerPacket.writeS2CListPacket(server.getPlayerManager().getPlayerList().get(i));
//                LOGGER.info("Finished reload on {}", Thread.currentThread());
//            } else
//                LOGGER.error("Failed to reload on {}", Thread.currentThread());
//        });
    }

}
