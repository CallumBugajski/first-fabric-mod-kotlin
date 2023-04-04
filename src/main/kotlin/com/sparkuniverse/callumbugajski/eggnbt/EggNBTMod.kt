package com.sparkuniverse.callumbugajski.eggnbt

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.Window
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedMaxSizeConstraint
import gg.essential.elementa.dsl.childOf
import gg.essential.elementa.dsl.constrain
import gg.essential.universal.UMatrixStack
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.SpawnEggItem
import net.minecraft.nbt.NbtCompound

@Suppress("unused")
fun init() {
    println("Loaded Egg NBT Mod!")
}

fun shouldRenderCrosshair(): Boolean {
    val targetedEntity = MinecraftClient.getInstance().targetedEntity
    return targetedEntity == null || SpawnEggItem.forEntity(targetedEntity.type) == null
}

fun renderEggCrosshair(matrices: MatrixStack) {
    val window = Window(ElementaVersion.V2)

    val entity = MinecraftClient.getInstance().targetedEntity ?: return

    val spawnEggItem = SpawnEggItem.forEntity(entity.type) ?: return

    val screen = UIContainer().constrain {
        width = ChildBasedMaxSizeConstraint()
        height = ChildBasedMaxSizeConstraint()
        x = CenterConstraint()
        y = CenterConstraint()
    } childOf window

    val eggX = CenterConstraint().getXPosition(screen).toInt() - 8
    val eggY = CenterConstraint().getYPosition(screen).toInt() - 8

    MinecraftClient.getInstance().itemRenderer.renderGuiItemIcon(matrices, spawnEggItem.defaultStack, eggX, eggY)

    val nbtCompound = NbtCompound()
    entity.writeNbt(nbtCompound)

    NBTListComponent(nbtCompound) childOf window

    window.draw(UMatrixStack(matrices))
}
