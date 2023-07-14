package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerSendTag implements IMessage, IMessageHandler<PacketServerSendTag, IMessage> {
    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(PacketServerSendTag message, MessageContext ctx) {
        //writeS2CTagPacket(player, buffer.readResourceLocation());
        return null;
    }
}
