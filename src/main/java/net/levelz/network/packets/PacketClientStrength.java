package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientStrength implements IMessage, IMessageHandler<PacketClientStrength, IMessage> {
    double damageAttribute = 0;

    @Override
    public void fromBytes(ByteBuf buf) {
        damageAttribute = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(damageAttribute);
    }

    @Override
    public IMessage onMessage(PacketClientStrength message, MessageContext ctx) {
        ctx.getServerHandler().player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(damageAttribute);
        return null;
    }
}
