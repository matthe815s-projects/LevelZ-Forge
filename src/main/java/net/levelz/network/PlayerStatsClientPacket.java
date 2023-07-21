package net.levelz.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.levelz.LevelzClient;
import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.data.LevelLists;
import net.levelz.data.LevelLoader;
import net.levelz.entity.LevelExperienceOrbEntity;
import net.levelz.init.ConfigInit;
import net.levelz.network.packets.*;
import net.levelz.stats.PlayerStatsManager;
import net.levelz.stats.Skill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerStatsClientPacket {

    public static void init() {
        LevelzClient.NETWORK.registerMessage(PacketClientSyncStats.class, PacketClientSyncStats.class, 4, Side.CLIENT);
        LevelzClient.NETWORK.registerMessage(PacketClientXP.class, PacketClientXP.class, 5, Side.CLIENT);
        LevelzClient.NETWORK.registerMessage(PacketClientLevel.class, PacketClientLevel.class, 6, Side.CLIENT);
        LevelzClient.NETWORK.registerMessage(PacketClientList.class, PacketClientList.class, 7, Side.CLIENT);
        LevelzClient.NETWORK.registerMessage(PacketClientStrength.class, PacketClientStrength.class, 8, Side.CLIENT);
        LevelzClient.NETWORK.registerMessage(PacketClientReset.class, PacketClientReset.class, 9, Side.CLIENT);
        LevelzClient.NETWORK.registerMessage(PacketClientConfigSync.class, PacketClientConfigSync.class, 10, Side.CLIENT);

//        ClientPlayNetworking.registerGlobalReceiver(PlayerStatsServerPacket.LEVEL_EXPERIENCE_ORB_PACKET, (client, handler, buf, sender) -> {
//            int id = buf.readVarInt();
//            double d = buf.readDouble();
//            double e = buf.readDouble();
//            double f = buf.readDouble();
//            int experienceAmount = buf.readShort();
//            client.execute(() -> {
//                LevelExperienceOrbEntity levelExperienceOrbEntity = new LevelExperienceOrbEntity(client.world, d, e, f, experienceAmount);
//                if (levelExperienceOrbEntity != null) {
//                    levelExperienceOrbEntity.updateTrackedPosition(d, e, f);
//                    levelExperienceOrbEntity.setYaw(0.0f);
//                    levelExperienceOrbEntity.setPitch(0.0f);
//                    levelExperienceOrbEntity.setId(id);
//                    client.world.addEntity(id, levelExperienceOrbEntity);
//                }
//            });
//        });
    }

    public static void writeC2SIncreaseLevelPacket(PlayerStatsManager playerStatsManager, Skill skill, int level) {
        int skillLevel = playerStatsManager.getSkillLevel(skill);
        level = Math.min(playerStatsManager.getSkillPoints(), level);
        level = Math.min(ConfigInit.CONFIG.maxLevel - skillLevel, level);

        if (ConfigInit.CONFIG.allowHigherSkillLevel) {
            level = Math.min(Math.abs(ConfigInit.CONFIG.maxLevel - skillLevel), level);
        }
        if (level < 1) {
            return;
        }
//
//        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
//        buf.writeString(skill.name());
//        buf.writeInt(level);
//
//        CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(PlayerStatsServerPacket.STATS_INCREASE_PACKET, buf);
//        MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
    }

    public static void writeC2SSyncConfigPacket() {
//        MinecraftClient.getInstance().getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(PlayerStatsServerPacket.SEND_CONFIG_SYNC_PACKET, new PacketByteBuf(Unpooled.buffer())));
    }

    public static void writeC2STagPacket(List<Object> list) {
    // PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
    // CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(PlayerStatsServerPacket.SEND_TAG_PACKET, buf);
    // MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
    }

    // Private executes
    private static void executeXPPacket(EntityPlayer player, PacketBuffer buf) {
//        PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) player).getPlayerStatsManager();
//        playerStatsManager.setLevelProgress(buf.readFloat());
//        playerStatsManager.setTotalLevelExperience(buf.readInt());
//        playerStatsManager.setOverallLevel(buf.readInt());
        // playerStatsManager.setLevel("level", buf.readInt());
    }

    private static void executeLevelPacket(EntityPlayer player, PacketBuffer buf) {
//        PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) player).getPlayerStatsManager();
//        playerStatsManager.setLevelProgress(buf.readFloat());
//        playerStatsManager.setTotalLevelExperience(buf.readInt());
//        playerStatsManager.setOverallLevel(buf.readInt());
//        playerStatsManager.setSkillPoints(buf.readInt());
//        for (Skill skill : Skill.values()) {
//            playerStatsManager.setSkillLevel(skill, buf.readInt());
//        }
//        // Set unlocked list
//        PlayerStatsServerPacket.syncLockedBlockList(playerStatsManager);
//        PlayerStatsServerPacket.syncLockedBrewingItemList(playerStatsManager);
//        PlayerStatsServerPacket.syncLockedSmithingItemList(playerStatsManager);
//        PlayerStatsServerPacket.syncLockedCraftingItemList(playerStatsManager);
    }

    private static void executeListPacket(PacketBuffer buf, EntityPlayer player) {
        LevelLoader.clearEveryList();
        ArrayList<String> list = new ArrayList<>();
//        while (buf.isReadable()) {
//            list.add(buf.readString());
//        }
        for (int i = 0; i < list.size(); i++) {
            String listName = list.get(i).toString();
            if (LevelLists.getListNames().contains(listName)) {
                int count = 2;
                int negativeCount = -2;
                if (listName.equals("minecraft:armor") || listName.equals("minecraft:tool") || listName.equals("minecraft:hoe") || listName.equals("minecraft:sword")
                        || listName.equals("minecraft:axe") || listName.equals("minecraft:custom_block") || listName.equals("minecraft:custom_item") || listName.equals("minecraft:custom_entity"))
                    negativeCount--;
                if (listName.equals("minecraft:enchanting_table")) {
                    count = 5;
                }
                for (int u = negativeCount; u < count; u++) {
                    addToList(listName, list.get(i + u));
                }
            } else if (listName.equals("mining:level")) {
                List<Integer> blockList = new ArrayList<>();
                LevelLists.miningLevelList.add(Integer.parseInt(list.get(i + 1)));
                for (int u = i + 2; u < list.size(); u++) {
                    if (list.get(u).equals("mining:level") || list.get(u).equals("brewing:level"))
                        break;
                    blockList.add(Integer.parseInt(list.get(u)));
                }
                LevelLists.miningBlockList.add(blockList);
            } else if (listName.equals("brewing:level")) {
                List<Integer> brewingItemList = new ArrayList<>();
                LevelLists.brewingLevelList.add(Integer.parseInt(list.get(i + 1)));
                for (int u = i + 2; u < list.size(); u++) {
                    if (list.get(u).equals("brewing:level") || list.get(u).equals("smithing:level"))
                        break;
                    brewingItemList.add(Integer.parseInt(list.get(u)));
                }
                LevelLists.brewingItemList.add(brewingItemList);
            } else if (listName.equals("smithing:level")) {
                List<Integer> smithingItemList = new ArrayList<>();
                LevelLists.smithingLevelList.add(Integer.parseInt(list.get(i + 1)));
                for (int u = i + 2; u < list.size(); u++) {
                    if (list.get(u).equals("smithing:level") || list.get(u).equals("crafting:level"))
                        break;
                    smithingItemList.add(Integer.parseInt(list.get(u)));
                }
                LevelLists.smithingItemList.add(smithingItemList);
            } else if (listName.equals("crafting:level")) {
                List<Integer> craftingItemList = new ArrayList<>();
                LevelLists.craftingLevelList.add(Integer.parseInt(list.get(i + 1)));
                LevelLists.craftingSkillList.add(String.valueOf(list.get(i + 2)));
                for (int u = i + 3; u < list.size(); u++) {
                    if (list.get(u).equals("crafting:level"))
                        break;
                    craftingItemList.add(Integer.parseInt(list.get(u)));
                }
                LevelLists.craftingItemList.add(craftingItemList);
            }
        }
        LevelLists.listOfAllLists.clear();
        LevelLoader.addAllInOneList();
//        PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) player).getPlayerStatsManager();
//        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
//                .setBaseValue(ConfigInit.CONFIG.healthBase + (double) playerStatsManager.getSkillLevel(Skill.HEALTH) * ConfigInit.CONFIG.healthBonus);
//        player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
//                .setBaseValue(ConfigInit.CONFIG.movementBase + (double) playerStatsManager.getSkillLevel(Skill.AGILITY) * ConfigInit.CONFIG.movementBonus);
//        player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)
//                .setBaseValue(ConfigInit.CONFIG.attackBase + (double) playerStatsManager.getSkillLevel(Skill.STRENGTH) * ConfigInit.CONFIG.attackBonus);
//        player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR)
//                .setBaseValue(ConfigInit.CONFIG.defenseBase + (double) playerStatsManager.getSkillLevel(Skill.DEFENSE) * ConfigInit.CONFIG.defenseBonus);
////        player.getAttributeInstance(EntityAttributes.GENERIC_LUCK).setBaseValue(ConfigInit.CONFIG.luckBase + (double) playerStatsManager.getSkillLevel(Skill.LUCK) * ConfigInit.CONFIG.luckBonus);
//        PlayerStatsServerPacket.syncLockedBlockList(playerStatsManager);
//        PlayerStatsServerPacket.syncLockedBrewingItemList(playerStatsManager);
//        PlayerStatsServerPacket.syncLockedSmithingItemList(playerStatsManager);
//        PlayerStatsServerPacket.syncLockedCraftingItemList(playerStatsManager);
    }

    private static void addToList(String listName, String object) {
        if (object.matches("-?(0|[1-9]\\d*)")) {
            LevelLists.getList(listName).add(Integer.parseInt(object));
        } else if (object.equals("false") || object.equals("true")) {
            LevelLists.getList(listName).add(Boolean.parseBoolean(object));
        } else
            LevelLists.getList(listName).add(object);
    }

    public static void writeC2SLevelUpPacket(int levels) {
        // Levelz level up
//        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
//        buf.writeInt(levels);
//        CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(PlayerStatsServerPacket.LEVEL_UP_BUTTON_PACKET, buf);
//        Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(packet);
    }

}