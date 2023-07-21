package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientList implements IMessage, IMessageHandler<PacketClientList, IMessage> {

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public IMessage onMessage(PacketClientList message, MessageContext ctx) {
//        PacketByteBuf newBuffer = new PacketByteBuf(Unpooled.buffer());
//        while (buf.isReadable()) {
//            newBuffer.writeString(buf.readString());
//        }
//        client.execute(() -> {
//            executeListPacket(newBuffer, client.player);
//        });
        return null;
    }
}
