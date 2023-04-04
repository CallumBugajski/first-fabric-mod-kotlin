package com.sparkuniverse.callumbugajski.mixin;

import com.sparkuniverse.callumbugajski.eggnbt.EggNBTModKt;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class CrosshairMixin {
    @Inject(at = @At("HEAD"), method = "renderCrosshair(Lnet/minecraft/client/util/math/MatrixStack;)V", cancellable = true)
    private void renderCrosshair(MatrixStack matrices, CallbackInfo info) {
        if (EggNBTModKt.shouldRenderCrosshair()) {
            return;
        }
        try {
            EggNBTModKt.renderEggCrosshair(matrices);
            info.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
