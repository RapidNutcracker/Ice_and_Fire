package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.entity.tile.TileEntityLectern;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageUpdateBestiary {

    private final BlockPos blockPos;
    private final ItemStack bookStack;
    private final int page;

    public MessageUpdateBestiary(BlockPos blockPos, ItemStack bookStack, int page) {
        this.blockPos = blockPos;
        this.bookStack = bookStack;
        this.page = page;
    }

    public MessageUpdateBestiary(FriendlyByteBuf buf) {
        this.blockPos = buf.readBlockPos();
        this.bookStack = buf.readItem();
        this.page = buf.readInt();
    }

    public static MessageUpdateBestiary read(FriendlyByteBuf buf) {
        return new MessageUpdateBestiary(buf.readBlockPos(), buf.readItem(), buf.readInt());
    }

    public static void write(MessageUpdateBestiary message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.blockPos);
        buf.writeItem(message.bookStack);
        buf.writeInt(message.page);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            ServerLevel level = player.getLevel();

            if (level.getBlockEntity(blockPos) instanceof TileEntityLectern lectern) {
                ItemStack bookStack = lectern.getItem(0);
                if (bookStack.getItem() == IafItemRegistry.BESTIARY.get()) {
                    EnumBestiaryPages.addPage(EnumBestiaryPages.fromInt(page), bookStack);
                }
                lectern.randomizePages(bookStack);
            }
        });
        context.get().setPacketHandled(true);
    }
}
