package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityLectern;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageUpdateLectern {

    public BlockPos blockPos;
    public int selectedPages1;
    public int selectedPages2;
    public int selectedPages3;

    public MessageUpdateLectern(BlockPos blockPos, int selectedPages1, int selectedPages2, int selectedPages3) {
        this.blockPos = blockPos;
        this.selectedPages1 = selectedPages1;
        this.selectedPages2 = selectedPages2;
        this.selectedPages3 = selectedPages3;

    }

    public MessageUpdateLectern() { }

    public static MessageUpdateLectern read(FriendlyByteBuf buf) {
        return new MessageUpdateLectern(buf.readBlockPos(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void write(MessageUpdateLectern message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.blockPos);
        buf.writeInt(message.selectedPages1);
        buf.writeInt(message.selectedPages2);
        buf.writeInt(message.selectedPages3);
    }

    public static void handle(MessageUpdateLectern message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (Minecraft.getInstance().level.getBlockEntity(message.blockPos) instanceof TileEntityLectern lectern) {
                lectern.selectedPages[0] = EnumBestiaryPages.fromInt(message.selectedPages1);
                lectern.selectedPages[1] = EnumBestiaryPages.fromInt(message.selectedPages2);
                lectern.selectedPages[2] = EnumBestiaryPages.fromInt(message.selectedPages3);
            }
        });
        context.get().setPacketHandled(true);
    }
}