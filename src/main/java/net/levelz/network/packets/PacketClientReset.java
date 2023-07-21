package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.init.ConfigInit;
import net.levelz.stats.PlayerStatsManager;
import net.levelz.stats.Skill;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scala.Byte;
import sun.security.provider.SHA;

public class PacketClientReset implements IMessage, IMessageHandler<PacketClientReset, IMessage> {
    String skillString = "";

    @Override
    public void fromBytes(ByteBuf buf) {
        skillString = String.valueOf(buf.readBytes(buf.readInt()));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(skillString.length());
        buf.writeBytes(skillString.getBytes());
    }

    @Override
    public IMessage onMessage(PacketClientReset message, MessageContext ctx) {
        // Sync attributes on client
        Skill skill = Skill.valueOf(skillString.toUpperCase());
        PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) ctx.getServerHandler().player).getPlayerStatsManager();
        int skillLevel = playerStatsManager.getSkillLevel(skill);
        playerStatsManager.setSkillPoints(playerStatsManager.getSkillPoints() + skillLevel);
        playerStatsManager.setSkillLevel(skill, 0);

        switch (skill) {
            case HEALTH:
                ctx.getServerHandler().player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigInit.CONFIG.healthBase);
                ctx.getServerHandler().player.setHealth(ctx.getServerHandler().player.getMaxHealth());
                break;
            case STRENGTH:
                ctx.getServerHandler().player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ConfigInit.CONFIG.attackBase);
                break;
            case AGILITY:
                ctx.getServerHandler().player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ConfigInit.CONFIG.movementBase);
                break;
            case DEFENSE:
                ctx.getServerHandler().player.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ConfigInit.CONFIG.defenseBase);
                break;
            case LUCK:
                ctx.getServerHandler().player.getEntityAttribute(SharedMonsterAttributes.LUCK).setBaseValue(ConfigInit.CONFIG.luckBase);
                break;
        }

        return null;
    }
}
