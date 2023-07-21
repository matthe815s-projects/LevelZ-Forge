package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.init.ConfigInit;
import net.levelz.stats.PlayerStatsManager;
import net.levelz.stats.Skill;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientReset implements IMessage, IMessageHandler<PacketClientReset, IMessage> {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public IMessage onMessage(PacketClientReset message, MessageContext ctx) {
        if (client.player != null) {
            String skillString = buf.readString();

            // Sync attributes on client
            client.execute(() -> {
                Skill skill = Skill.valueOf(skillString.toUpperCase());
                PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) client.player).getPlayerStatsManager();
                int skillLevel = playerStatsManager.getSkillLevel(skill);
                playerStatsManager.setSkillPoints(playerStatsManager.getSkillPoints() + skillLevel);
                playerStatsManager.setSkillLevel(skill, 0);

                switch (skill) {
                    case HEALTH -> {
                        client.player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(ConfigInit.CONFIG.healthBase);
                        client.player.setHealth(client.player.getMaxHealth());
                    }
                    case STRENGTH -> client.player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(ConfigInit.CONFIG.attackBase);
                    case AGILITY -> client.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(ConfigInit.CONFIG.movementBase);
                    case DEFENSE -> client.player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(ConfigInit.CONFIG.defenseBase);
                    case LUCK -> client.player.getAttributeInstance(EntityAttributes.GENERIC_LUCK).setBaseValue(ConfigInit.CONFIG.luckBase);
                    default -> {
                    }
                }
            });

        }
        return null;
    }
}
