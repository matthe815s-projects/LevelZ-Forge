package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerSyncLevel implements IMessage, IMessageHandler<PacketServerSyncLevel, IMessage> {
    String skill = "";
    int level = 0;
    int skillPoints = 0;

    public PacketServerSyncLevel (String skill, int level, int skillPoints) {
        this.skill = skill;
        this.level = level;
        this.skillPoints = skillPoints;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.skill = String.valueOf(buf.readBytes(buf.readInt()));
        this.level = buf.readInt();
        this.skillPoints = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.skill.length());
        buf.writeBytes(this.skill.getBytes());
        buf.writeInt(this.level);
        buf.writeInt(this.skillPoints);
    }

    @Override
    public IMessage onMessage(PacketServerSyncLevel message, MessageContext ctx) {
        return null;
    }
}
