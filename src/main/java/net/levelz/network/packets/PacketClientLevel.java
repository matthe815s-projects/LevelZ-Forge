package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientLevel implements IMessage, IMessageHandler<PacketClientLevel, IMessage> {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public IMessage onMessage(PacketClientLevel message, MessageContext ctx) {
//        PacketByteBuf newBuffer = new PacketByteBuf(Unpooled.buffer());
//        newBuffer.writeFloat(buf.readFloat());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        newBuffer.writeInt(buf.readInt());
//        client.execute(() -> {
//            executeLevelPacket(client.player, newBuffer);
//        });
        return null;
    }
}
