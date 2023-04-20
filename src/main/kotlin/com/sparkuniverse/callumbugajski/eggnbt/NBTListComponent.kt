package com.sparkuniverse.callumbugajski.eggnbt

import gg.essential.elementa.UIComponent
import gg.essential.elementa.components.TreeArrowComponent
import gg.essential.elementa.components.TreeListComponent
import gg.essential.elementa.components.TreeNode
import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.inspector.ArrowComponent
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.constrain
import net.minecraft.nbt.AbstractNbtList
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement

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
            addNodeToTree(nbtCompound.get(key)!!, node, key)
        }
        return listOf(node)
    }

    private fun addNodeToTree(nbtElement: NbtElement, treeNode: TreeNode, name: String) {
        when (nbtElement) {
            is NbtCompound -> {
                val node = TextNode(name)
                treeNode.addChild(node)
                for (key in nbtElement.keys) {
                    addNodeToTree(nbtElement.get(key)!!, node, key)
                }
            }

            is AbstractNbtList<*> -> {
                val node = TextNode(name)
                treeNode.addChild(node)
                for (internalElement in nbtElement) {
                    addNodeToTree(internalElement, node, "List Item")
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
                y = CenterConstraint()
            }
        }

    }

}