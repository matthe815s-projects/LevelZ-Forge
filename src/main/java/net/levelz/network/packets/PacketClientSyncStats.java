package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.init.ConfigInit;
import net.levelz.network.PlayerStatsServerPacket;
import net.levelz.stats.PlayerStatsManager;
import net.levelz.stats.Skill;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientSyncStats implements IMessage, IMessageHandler<PacketClientSyncStats, IMessage> {
    String skillString = "";
    int level = 0;
    int points = 0;

    @Override
    public void fromBytes(ByteBuf buf) {
        skillString = String.valueOf(buf.readBytes(buf.readInt()));
        level = buf.readInt();
        points= buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(skillString.length());
        buf.writeBytes(skillString.getBytes());
        buf.writeInt(level);
        buf.writeInt(points);
    }

    @Override
    public IMessage onMessage(PacketClientSyncStats message, MessageContext ctx) {
        PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) ctx.getServerHandler().player).getPlayerStatsManager();
        Skill skill = Skill.valueOf(skillString);

        playerStatsManager.setSkillLevel(skill, level);
        playerStatsManager.setSkillPoints(points);

        if (skill == Skill.STRENGTH) {
            playerStatsManager.getPlayerEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
                    .setBaseValue(ConfigInit.CONFIG.attackBase + (double) playerStatsManager.getSkillLevel(Skill.STRENGTH) * ConfigInit.CONFIG.attackBonus);
        }

        PlayerStatsServerPacket.syncLockedCraftingItemList(playerStatsManager);

        switch (skill) {
            case SMITHING:
                PlayerStatsServerPacket.syncLockedSmithingItemList(playerStatsManager);
                break;
            case MINING:
                PlayerStatsServerPacket.syncLockedBlockList(playerStatsManager);
                break;
            case ALCHEMY:
                PlayerStatsServerPacket.syncLockedBrewingItemList(playerStatsManager);
                break;
        }
        return null;
    }
}
