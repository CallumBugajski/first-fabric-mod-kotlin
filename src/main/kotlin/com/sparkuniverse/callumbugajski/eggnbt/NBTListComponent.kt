package com.sparkuniverse.callumbugajski.eggnbt

import gg.essential.elementa.UIComponent
import gg.essential.elementa.components.TreeArrowComponent
import gg.essential.elementa.components.TreeListComponent
import gg.essential.elementa.components.TreeNode
import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.inspector.ArrowComponent
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.constrain
import gg.essential.elementa.dsl.pixels
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList

class NBTListComponent(private val nbtCompound: NbtCompound) : TreeListComponent() {

    init {
        val roots = createTree()
        roots.forEach { c ->
            c.displayComponent.open(true)
        }
        setRoots(roots)
    }

    private fun createTree(): List<TreeNode> {
        val node = TextNode("Root")
        for (key in nbtCompound.keys) {
            createNode(nbtCompound.get(key)!!, node, key)
        }
        return listOf(node)
    }

    private fun createNode(nbtElement: NbtElement, treeNode: TreeNode, name: String) {
        when (nbtElement) {
            is NbtCompound -> {
                val node = TextNode(name)
                treeNode.addChild(node)
                for (key in nbtElement.keys) {
                    createNode(nbtElement.get(key)!!, node, key)
                }
            }
            is NbtList -> {
                val node = TextNode(name)
                treeNode.addChild(node)
                for (internalElement in nbtElement.listIterator()) {
                    createNode(internalElement, node, "List Item")
                }
            }
            else -> {
                treeNode.addChild(TextNode(name + ": " + nbtElement.asString()))
            }
        }
    }

    class TextNode(private val text: String) : TreeNode() {

        override fun getArrowComponent(): TreeArrowComponent {
            return ArrowComponent(false)
        }

        override fun getPrimaryComponent(): UIComponent {
            return UIText(text).constrain {
                x = SiblingConstraint()
                y = 1.pixels()
            }
        }

    }

}