package net.levelz.network.mixin.entity;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.levelz.data.LevelLists;
import net.levelz.stats.PlayerStatsManager;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

@Mixin(SnowGolemEntity.class)
public class SnowGolemEntityMixin {

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SnowGolemEntity;sheared(Lnet/minecraft/sound/SoundCategory;)V"), cancellable = true)
    protected void interactMobMixin(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        ArrayList<Object> levelList = LevelLists.snowGolemList;
        if (!PlayerStatsManager.playerLevelisHighEnough(player, levelList, null, true)) {
            player.sendMessage(Text.translatable("item.levelz." + levelList.get(0) + ".tooltip", levelList.get(1)).formatted(Formatting.RED), true);
            info.setReturnValue(ActionResult.FAIL);
        }
    }

}
