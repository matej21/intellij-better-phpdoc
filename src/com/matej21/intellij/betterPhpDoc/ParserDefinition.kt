package com.matej21.intellij.betterPhpDoc


import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.documentation.phpdoc.parser.PhpDocParser
import com.jetbrains.php.lang.documentation.phpdoc.parser.tags.PhpDocTagParserRegistry
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocPsiCreator
import com.jetbrains.php.lang.parser.PhpParserDefinition


class ParserDefinition() : PhpParserDefinition() {


    override fun createElement(node: ASTNode): PsiElement {

        val type = node.elementType
        if (type === PhpDocPsiCreator.phpDocParam) {
            return MyPhpDocParamTag(node)
        } else if (type === PhpDocPsiCreator.phpDocType) {
            return MyPhpDocType(node)
        }
        return super.createElement(node)
    }

    init {
        PhpDocParser() //initialize phpdoc parser registry
        PhpDocTagParserRegistry.register("@param", MyPhpDocParamTagParser())
        PhpDocTagParserRegistry.register("@property", MyPhpDocPropertyTagParser())
        PhpDocTagParserRegistry.register("@return", MyPhpDocReturnTagParser())
        PhpDocTagParserRegistry.register("@var", MyPhpDocVarTagParser())
    }

}
