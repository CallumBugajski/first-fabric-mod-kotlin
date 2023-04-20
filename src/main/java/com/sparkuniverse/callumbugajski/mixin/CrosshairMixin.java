package com.sparkuniverse.callumbugajski.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.sparkuniverse.callumbugajski.eggnbt.EggNBTModKt;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class CrosshairMixin {

    @WrapWithCondition(method = "renderCrosshair(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"))
    private boolean renderCrosshair(MatrixStack matrixStack, int x, int y, int u, int v, int width, int height) {
        try {
            return !EggNBTModKt.renderEggCrosshair(matrixStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
