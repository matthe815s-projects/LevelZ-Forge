package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.init.ConfigInit;
import net.levelz.network.PlayerStatsServerPacket;
import net.levelz.stats.PlayerStatsManager;
import net.levelz.stats.Skill;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientSyncStats implements IMessage, IMessageHandler<PacketClientSyncStats, IMessage> {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public IMessage onMessage(PacketClientSyncStats message, MessageContext ctx) {
        String skillString = buf.readString().toUpperCase();
        int level = buf.readInt();
        int points = buf.readInt();
        client.execute(() -> {
            PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) client.player).getPlayerStatsManager();
            Skill skill = Skill.valueOf(skillString);

            playerStatsManager.setSkillLevel(skill, level);
            playerStatsManager.setSkillPoints(points);

            if (skill == Skill.STRENGTH) {
                playerStatsManager.getPlayerEntity().getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                        .setBaseValue(ConfigInit.CONFIG.attackBase + (double) playerStatsManager.getSkillLevel(Skill.STRENGTH) * ConfigInit.CONFIG.attackBonus);
            }
            PlayerStatsServerPacket.syncLockedCraftingItemList(playerStatsManager);
            switch (skill) {
                case SMITHING -> PlayerStatsServerPacket.syncLockedSmithingItemList(playerStatsManager);
                case MINING -> PlayerStatsServerPacket.syncLockedBlockList(playerStatsManager);
                case ALCHEMY -> PlayerStatsServerPacket.syncLockedBrewingItemList(playerStatsManager);
                default -> {
                }
            }
        });
        return null;
    }
}
