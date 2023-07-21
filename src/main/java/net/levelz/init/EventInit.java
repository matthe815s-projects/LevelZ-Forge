package net.levelz.init;

import java.util.ArrayList;

import net.levelz.access.PlayerSyncAccess;
import net.levelz.data.LevelLists;
import net.levelz.stats.PlayerStatsManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber
public class EventInit {

    @SubscribeEvent
    public void OnWorldChange (PlayerEvent.PlayerLoggedInEvent world) {
        ((PlayerSyncAccess) world.player).syncStats(false);
    }

    @SubscribeEvent
    public void OnItemUse (PlayerInteractEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!player.isCreative() && !player.isSpectator()) {
            ArrayList<Object> customList = LevelLists.customItemList;
            String string = ForgeRegistries.ITEMS.getKey(player.getActiveItemStack().getItem()).toString();
            if (!customList.isEmpty() && !PlayerStatsManager.playerLevelisHighEnough(player, customList, string, true)) {
                player.sendMessage(
                        new TextComponentTranslation("item.levelz." + customList.get(customList.indexOf(string) + 1) + ".tooltip", customList.get(customList.indexOf(string) + 2)));
            }
        }
    }

    @SubscribeEvent
    public void OnBlockPlace (BlockEvent.EntityPlaceEvent event) {
        EntityPlayer player = (EntityPlayer) event.getEntity();
        BlockPos blockPos = event.getPos();

        if (!player.isCreative() && !player.isSpectator()) {
            if (event.getWorld().mayPlace(event.getPlacedBlock().getBlock(), blockPos, false, event.getEntity().getHorizontalFacing(), player)) {
                String string = ForgeRegistries.BLOCKS.getKey(event.getWorld().getBlockState(blockPos).getBlock()).toString();
                ArrayList<Object> customList = LevelLists.customBlockList;
                if (!customList.isEmpty() && customList.contains(string)) {
                    if (!PlayerStatsManager.playerLevelisHighEnough(player, customList, string, true)) {
                        player.sendMessage(new TextComponentTranslation("item.levelz." + customList.get(customList.indexOf(string) + 1) + ".tooltip", customList.get(customList.indexOf(string) + 2)));
                    }
                }
            }
        }
    }
}
