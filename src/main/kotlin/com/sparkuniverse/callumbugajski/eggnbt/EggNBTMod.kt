package com.sparkuniverse.callumbugajski.eggnbt

import com.llamalad7.mixinextras.MixinExtrasBootstrap
import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.components.Window
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.dsl.childOf
import gg.essential.elementa.dsl.constrain
import gg.essential.universal.UMatrixStack
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.SpawnEggItem
import net.minecraft.nbt.NbtCompound

@Suppress("unused")
fun init() {
    MixinExtrasBootstrap.init();
    println("Loaded Egg NBT Mod!")
}

/**
 * Return true if egg and nbt tree is rendered
 */
fun renderEggCrosshair(matrices: MatrixStack): Boolean {
    val window = Window(ElementaVersion.V2)

    val entity = MinecraftClient.getInstance().targetedEntity ?: return false

    val spawnEggItem = SpawnEggItem.forEntity(entity.type) ?: return false

    UIItemImage(spawnEggItem.defaultStack, true).constrain {
        x = CenterConstraint()
        y = CenterConstraint()
    } childOf window

    val nbtCompound = NbtCompound()
    entity.writeNbt(nbtCompound)

    NBTListComponent(nbtCompound) childOf window

    window.draw(UMatrixStack(matrices))
    return true
}
