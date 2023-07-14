package net.levelz.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.levelz.LevelzClient;
import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.access.PlayerSyncAccess;
import net.levelz.data.LevelLists;
import net.levelz.entity.LevelExperienceOrbEntity;
import net.levelz.init.ConfigInit;
import net.levelz.init.CriteriaInit;
import net.levelz.network.packets.PacketLevelUpButton;
import net.levelz.network.packets.PacketServerConfigSync;
import net.levelz.network.packets.PacketServerSendTag;
import net.levelz.network.packets.PacketServerStatsIncrease;
import net.levelz.stats.PlayerStatsManager;
import net.levelz.stats.Skill;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerStatsServerPacket {

    public static final ResourceLocation STATS_INCREASE_PACKET = new ResourceLocation("levelz", "player_increase_stats");
    public static final ResourceLocation STATS_SYNC_PACKET = new ResourceLocation("levelz", "player_sync_stats");
    public static final ResourceLocation XP_PACKET = new ResourceLocation("levelz", "player_level_xp");
    public static final ResourceLocation LEVEL_PACKET = new ResourceLocation("levelz", "player_level_stats");
    public static final ResourceLocation LIST_PACKET = new ResourceLocation("levelz", "unlocking_list");
    public static final ResourceLocation STRENGTH_PACKET = new ResourceLocation("levelz", "strength_sync");
    public static final ResourceLocation RESET_PACKET = new ResourceLocation("levelz", "reset_skill");
    public static final ResourceLocation LEVEL_EXPERIENCE_ORB_PACKET = new ResourceLocation("levelz", "level_experience_orb");
    public static final ResourceLocation SEND_CONFIG_SYNC_PACKET = new ResourceLocation("levelz", "send_config_sync_packet");
    public static final ResourceLocation CONFIG_SYNC_PACKET = new ResourceLocation("levelz", "config_sync_packet");
    public static final ResourceLocation TAG_PACKET = new ResourceLocation("levelz", "tag_packet");
    public static final ResourceLocation SEND_TAG_PACKET = new ResourceLocation("levelz", "send_tag_packet");
    public static final ResourceLocation LEVEL_UP_BUTTON_PACKET = new ResourceLocation("levelz", "level_up_button");

    public static void init() {
        LevelzClient.NETWORK.registerMessage(PacketServerStatsIncrease.class, PacketServerStatsIncrease.class, 0, Side.SERVER);
        LevelzClient.NETWORK.registerMessage(PacketServerConfigSync.class, PacketServerConfigSync.class, 1, Side.SERVER);
        LevelzClient.NETWORK.registerMessage(PacketServerSendTag.class, PacketServerSendTag.class, 2, Side.SERVER);
        LevelzClient.NETWORK.registerMessage(PacketLevelUpButton.class, PacketLevelUpButton.class, 3, Side.SERVER);
    }

    public static void writeS2CSyncLevelPacket(PlayerStatsManager playerStatsManager, ServerPlayerEntity serverPlayerEntity, Skill skill) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(skill.name());
        buf.writeInt(playerStatsManager.getSkillLevel(skill));
        buf.writeInt(playerStatsManager.getSkillPoints());
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(STATS_SYNC_PACKET, buf);
        serverPlayerEntity.networkHandler.sendPacket(packet);
    }

    public static void writeS2CXPPacket(PlayerStatsManager playerStatsManager, ServerPlayerEntity serverPlayerEntity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeFloat(playerStatsManager.getLevelProgress());
        buf.writeInt(playerStatsManager.getTotalLevelExperience());
        buf.writeInt(playerStatsManager.getOverallLevel());
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(XP_PACKET, buf);
        serverPlayerEntity.networkHandler.sendPacket(packet);
    }

    public static void writeS2CSkillPacket(PlayerStatsManager playerStatsManager, ServerPlayerEntity serverPlayerEntity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeFloat(playerStatsManager.getLevelProgress());
        buf.writeInt(playerStatsManager.getTotalLevelExperience());
        buf.writeInt(playerStatsManager.getOverallLevel());
        buf.writeInt(playerStatsManager.getSkillPoints());
        for (Skill skill : Skill.values()) {
            buf.writeInt(playerStatsManager.getSkillLevel(skill));
        }

        // Set on server
        syncLockedBlockList(playerStatsManager);
        syncLockedBrewingItemList(playerStatsManager);
        syncLockedSmithingItemList(playerStatsManager);
        syncLockedCraftingItemList(playerStatsManager);

        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(LEVEL_PACKET, buf);
        serverPlayerEntity.networkHandler.sendPacket(packet);
    }

    public static void writeS2CStrengthPacket(ServerPlayerEntity serverPlayerEntity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeDouble(serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue());
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(STRENGTH_PACKET, buf);
        serverPlayerEntity.networkHandler.sendPacket(packet);
    }

    public static void syncLockedBlockList(PlayerStatsManager playerStatsManager) {
        playerStatsManager.lockedBlockIds.clear();
        for (int i = 0; i < LevelLists.miningLevelList.size(); i++) {
            if (LevelLists.miningLevelList.get(i) > playerStatsManager.getSkillLevel(Skill.MINING)) {
                for (int u = 0; u < LevelLists.miningBlockList.get(i).size(); u++) {
                    if (!playerStatsManager.lockedBlockIds.contains(LevelLists.miningBlockList.get(i).get(u)))
                        playerStatsManager.lockedBlockIds.add(LevelLists.miningBlockList.get(i).get(u));
                }
            }
        }
    }

    public static void syncLockedBrewingItemList(PlayerStatsManager playerStatsManager) {
        playerStatsManager.lockedbrewingItemIds.clear();
        for (int i = 0; i < LevelLists.brewingLevelList.size(); i++) {
            if (LevelLists.brewingLevelList.get(i) > playerStatsManager.getSkillLevel(Skill.ALCHEMY)) {
                for (int u = 0; u < LevelLists.brewingItemList.get(i).size(); u++) {
                    if (!playerStatsManager.lockedbrewingItemIds.contains(LevelLists.brewingItemList.get(i).get(u)))
                        playerStatsManager.lockedbrewingItemIds.add(LevelLists.brewingItemList.get(i).get(u));
                }
            }
        }
    }

    public static void syncLockedSmithingItemList(PlayerStatsManager playerStatsManager) {
        playerStatsManager.lockedSmithingItemIds.clear();
        for (int i = 0; i < LevelLists.smithingLevelList.size(); i++) {
            if (LevelLists.smithingLevelList.get(i) > playerStatsManager.getSkillLevel(Skill.SMITHING)) {
                for (int u = 0; u < LevelLists.smithingItemList.get(i).size(); u++) {
                    if (!playerStatsManager.lockedSmithingItemIds.contains(LevelLists.smithingItemList.get(i).get(u)))
                        playerStatsManager.lockedSmithingItemIds.add(LevelLists.smithingItemList.get(i).get(u));
                }
            }
        }
    }

    public static void syncLockedCraftingItemList(PlayerStatsManager playerStatsManager) {
        playerStatsManager.lockedCraftingItemIds.clear();
        for (int i = 0; i < LevelLists.craftingLevelList.size(); i++) {
            if (LevelLists.craftingLevelList.get(i) > playerStatsManager.getSkillLevel(Skill.valueOf(LevelLists.craftingSkillList.get(i).toString().toUpperCase()))) {
                for (int u = 0; u < LevelLists.craftingItemList.get(i).size(); u++) {
                    if (!playerStatsManager.lockedCraftingItemIds.contains(LevelLists.craftingItemList.get(i).get(u)))
                        playerStatsManager.lockedCraftingItemIds.add(LevelLists.craftingItemList.get(i).get(u));
                }
            }
        }
    }

    public static void writeS2CConfigSyncPacket(ServerPlayerEntity serverPlayerEntity, List<Object> list) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof Integer)
                buf.writeInt((int) list.get(i));
            else if (list.get(i) instanceof Float)
                buf.writeFloat((float) list.get(i));
            else if (list.get(i) instanceof Double)
                buf.writeDouble((double) list.get(i));
            else if (list.get(i) instanceof Boolean)
                buf.writeBoolean((boolean) list.get(i));
        }
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(CONFIG_SYNC_PACKET, buf);
        serverPlayerEntity.networkHandler.sendPacket(packet);
    }

    public static void writeS2CTagPacket(ServerPlayerEntity serverPlayerEntity, ResourceLocation ResourceLocation) {
        // PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        // System.out.println(Blocks.ACACIA_LOG.getRegistryEntry().isIn(BlockTags.ACACIA_LOGS));
        // System.out.println(Registry.BLOCK.getOrCreateEntryList(BlockTags.ACACIA_LOGS));
        // System.out.println(Registry.BLOCK.getOrCreateEntryList(TagKey.of(Registry.BLOCK_KEY, ResourceLocation)));
    }

    public static void writeS2CListPacket(ServerPlayerEntity serverPlayerEntity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        for (int i = 0; i < LevelLists.getListNames().size(); i++) {
            String listName = LevelLists.getListNames().get(i);
            ArrayList<Object> list = LevelLists.getList(listName);
            for (int u = 0; u < list.size(); u++) {
                buf.writeString(list.get(u).toString());
            }
        }
        for (int k = 0; k < LevelLists.miningLevelList.size(); k++) {
            buf.writeString("mining:level");
            buf.writeString(LevelLists.miningLevelList.get(k).toString());
            for (int u = 0; u < LevelLists.miningBlockList.get(k).size(); u++) {
                buf.writeString(LevelLists.miningBlockList.get(k).get(u).toString());
            }
        }
        for (int k = 0; k < LevelLists.brewingLevelList.size(); k++) {
            buf.writeString("brewing:level");
            buf.writeString(LevelLists.brewingLevelList.get(k).toString());
            for (int u = 0; u < LevelLists.brewingItemList.get(k).size(); u++) {
                buf.writeString(LevelLists.brewingItemList.get(k).get(u).toString());
            }
        }
        for (int k = 0; k < LevelLists.smithingLevelList.size(); k++) {
            buf.writeString("smithing:level");
            buf.writeString(LevelLists.smithingLevelList.get(k).toString());
            for (int u = 0; u < LevelLists.smithingItemList.get(k).size(); u++) {
                buf.writeString(LevelLists.smithingItemList.get(k).get(u).toString());
            }
        }
        for (int k = 0; k < LevelLists.craftingLevelList.size(); k++) {
            buf.writeString("crafting:level");
            buf.writeString(LevelLists.craftingLevelList.get(k).toString());
            buf.writeString(LevelLists.craftingSkillList.get(k).toString());
            for (int u = 0; u < LevelLists.craftingItemList.get(k).size(); u++) {
                buf.writeString(LevelLists.craftingItemList.get(k).get(u).toString());
            }
        }
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(LIST_PACKET, buf);
        serverPlayerEntity.networkHandler.sendPacket(packet);
    }

    public static void writeS2CResetSkillPacket(ServerPlayerEntity serverPlayerEntity, Skill skill) {
        // Sync attributes on server
        PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) serverPlayerEntity).getPlayerStatsManager();
        int skillLevel = playerStatsManager.getSkillLevel(skill);
        switch (skill) {
        case HEALTH -> {
            serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(ConfigInit.CONFIG.healthBase + skillLevel * ConfigInit.CONFIG.healthBonus);
            serverPlayerEntity.setHealth(serverPlayerEntity.getMaxHealth());
        }
        case STRENGTH -> serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(ConfigInit.CONFIG.attackBase + skillLevel * ConfigInit.CONFIG.attackBonus);
        case AGILITY -> serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(ConfigInit.CONFIG.movementBase + skillLevel * ConfigInit.CONFIG.movementBonus);
        case DEFENSE -> serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(ConfigInit.CONFIG.defenseBase + skillLevel * ConfigInit.CONFIG.defenseBonus);
        case LUCK -> serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_LUCK).setBaseValue(ConfigInit.CONFIG.luckBase + skillLevel * ConfigInit.CONFIG.luckBonus);
        default -> {
        }
        }
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(skill.name());
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(RESET_PACKET, buf);
        serverPlayerEntity.networkHandler.sendPacket(packet);
    }

    public Packet<ClientPlayPacketListener> createS2CLevelExperienceOrbPacket(LevelExperienceOrbEntity levelExperienceOrbEntity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeVarInt(levelExperienceOrbEntity.getId());
        buf.writeDouble(levelExperienceOrbEntity.getX());
        buf.writeDouble(levelExperienceOrbEntity.getY());
        buf.writeDouble(levelExperienceOrbEntity.getZ());
        buf.writeShort(levelExperienceOrbEntity.getExperienceAmount());
        return ServerPlayNetworking.createS2CPacket(LEVEL_EXPERIENCE_ORB_PACKET, buf);
    }

}
