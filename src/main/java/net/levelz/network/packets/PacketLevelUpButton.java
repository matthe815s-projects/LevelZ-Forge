package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.levelz.access.PlayerSyncAccess;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketLevelUpButton implements IMessage, IMessageHandler<PacketLevelUpButton, IMessage> {
    int levelup = 0;

    @Override
    public void fromBytes(ByteBuf buf) {
        levelup = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(levelup);
    }

    @Override
    public IMessage onMessage(PacketLevelUpButton message, MessageContext ctx) {
        ((PlayerSyncAccess) ctx.getServerHandler().player).levelUp(levelup, true, false);
        return null;
    }
}
