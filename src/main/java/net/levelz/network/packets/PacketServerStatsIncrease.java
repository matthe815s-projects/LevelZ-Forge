package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.init.ConfigInit;
import net.levelz.init.CriteriaInit;
import net.levelz.network.PlayerStatsServerPacket;
import net.levelz.stats.PlayerStatsManager;
import net.levelz.stats.Skill;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerStatsIncrease implements IMessage, IMessageHandler<PacketServerStatsIncrease, IMessage> {
    String skillString = "";
    int level = 0;

    @Override
    public void fromBytes(ByteBuf buf) {
        skillString = String.valueOf(buf.readBytes(buf.readInt()));
        level = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(skillString.length());
        buf.writeBytes(skillString.getBytes());
        buf.writeInt(level);
    }

    @Override
    public IMessage onMessage(PacketServerStatsIncrease message, MessageContext ctx) {
        EntityPlayerMP player =  ctx.getServerHandler().player;

        PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) player).getPlayerStatsManager();
        if (playerStatsManager.getSkillPoints() - level >= 0) {
            Skill skill = Skill.valueOf(skillString);
            if (!ConfigInit.CONFIG.allowHigherSkillLevel && playerStatsManager.getSkillLevel(skill) >= ConfigInit.CONFIG.maxLevel) {
                return null;
            }

            for (int i = 1; i <= level; i++) {
                CriteriaInit.SKILL_UP.trigger(player, skillString.toLowerCase(), playerStatsManager.getSkillLevel(skill) + level);
            }

            playerStatsManager.setSkillLevel(skill, playerStatsManager.getSkillLevel(skill) + level);
            playerStatsManager.setSkillPoints(playerStatsManager.getSkillPoints() - level);

            if (skill == Skill.HEALTH) {
                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                        .setBaseValue(player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() + ConfigInit.CONFIG.healthBonus * level);
                player.setHealth(player.getHealth() + (float) ConfigInit.CONFIG.healthBonus * level);
            } else if (skill == Skill.STRENGTH) {
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
                        .setBaseValue(player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() + ConfigInit.CONFIG.attackBonus * level);
            } else if (skill == Skill.AGILITY) {
                player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                        .setBaseValue(player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() + ConfigInit.CONFIG.movementBonus * level);
            } else if (skill == Skill.DEFENSE) {
                player.getEntityAttribute(SharedMonsterAttributes.ARMOR)
                        .setBaseValue(player.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue() + ConfigInit.CONFIG.defenseBonus * level);
            } else if (skill == Skill.LUCK) {
                player.getEntityAttribute(SharedMonsterAttributes.LUCK)
                        .setBaseValue(player.getEntityAttribute(SharedMonsterAttributes.LUCK).getAttributeValue() + ConfigInit.CONFIG.luckBonus * level);
            }

            //case MINING -> syncLockedBlockList(playerStatsManager);
            //case ALCHEMY -> syncLockedBrewingItemList(playerStatsManager);
            //case SMITHING -> syncLockedSmithingItemList(playerStatsManager);
            //syncLockedCraftingItemList(playerStatsManager);

            PlayerStatsServerPacket.writeS2CSyncLevelPacket(playerStatsManager, player, skill);
        }

        return null;
    }
}
