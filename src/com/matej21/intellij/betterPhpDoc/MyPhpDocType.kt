package com.matej21.intellij.betterPhpDoc

import com.intellij.lang.ASTNode
import com.jetbrains.php.lang.documentation.phpdoc.psi.impl.PhpDocTypeImpl
import com.jetbrains.php.lang.documentation.phpdoc.parser.PhpDocElementTypes;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocType
import com.jetbrains.php.lang.psi.resolve.types.PhpType

class MyPhpDocType(node: ASTNode) : PhpDocTypeImpl(node) {
    override fun getType(): PhpType {
        val type = super.getType()
        if (!this.text.contains("<")) {
            return type
        }
        val subTypes = this.findChildrenByType<PhpDocType>(PhpDocElementTypes.phpDocType)

        when (this.name) {
            "array", "iterable" -> {
                when (subTypes.size) {
                    1 -> subTypes[0].type.pluralise()
                    2 -> subTypes[1].type.pluralise()
                    else -> null
                }
            }
            else -> null
        }?.apply {
            type.add(this)
        }

        return type
    }
}
