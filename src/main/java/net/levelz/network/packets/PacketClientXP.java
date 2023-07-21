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

public class PacketClientXP implements IMessage,IMessageHandler<PacketClientXP, IMessage>
    {
        @Override
        public void fromBytes(ByteBuf buf) {

    }

        @Override
        public void toBytes(ByteBuf buf) {

    }

        @Override
    public IMessage onMessage(PacketClientXP message, MessageContext ctx) {
            PacketByteBuf newBuffer = new PacketByteBuf(Unpooled.buffer());
            newBuffer.writeFloat(buf.readFloat());
            newBuffer.writeInt(buf.readInt());
            newBuffer.writeInt(buf.readInt());
            client.execute(() -> {
                executeXPPacket(client.player, newBuffer);
            });
    }
}
