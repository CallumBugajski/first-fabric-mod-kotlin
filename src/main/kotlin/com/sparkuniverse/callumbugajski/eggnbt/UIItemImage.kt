package com.sparkuniverse.callumbugajski.eggnbt

import gg.essential.elementa.UIComponent
import gg.essential.universal.UMatrixStack
import net.minecraft.client.MinecraftClient
import net.minecraft.item.ItemStack

class UIItemImage(private val itemStack: ItemStack, private val centred: Boolean) : UIComponent() {

    override fun draw(matrixStack: UMatrixStack) {
        // This is necessary because if it isn't here, effects will never be applied.
        beforeDrawCompat(matrixStack)

        val itemX = this.getLeft().toInt() - if (centred) 8 else 0
        val itemY = this.getTop().toInt() - if (centred) 8 else 0

        MinecraftClient.getInstance().itemRenderer.renderGuiItemIcon(
            matrixStack.toMC(),
            itemStack,
            itemX,
            itemY
        )

        super.draw(matrixStack)
    }

}