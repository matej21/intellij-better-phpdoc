package com.matej21.intellij.betterPhpDoc

import com.intellij.lang.ASTNode
import com.intellij.openapi.util.NullableLazyValue
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment
import com.jetbrains.php.lang.documentation.phpdoc.psi.impl.tags.PhpDocParamTagImpl
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocParamTag
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.Parameter
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.resolve.types.PhpType


class MyPhpDocParamTag(node: ASTNode) : PhpDocParamTagImpl(node) {

    override fun getVarName(): String? {
        val varName = super.getVarName()
        if (varName != null && !varName.isEmpty()) {
            return varName
        }
        val parameter = getCorrespondingParameter()
        if (parameter != null) {
            return parameter.name
        }
        return ""
    }

    override fun getType(): PhpType {
        val type = super.getType()
        val hasVarNameDefinition = !(super.getVarName() ?: "").isEmpty()
        if (hasVarNameDefinition) {
            return type
        }
        val parameter = getCorrespondingParameter() ?: return type
       type.add(parameter.declaredType)

        return type
    }

    private fun getCorrespondingParameter(): Parameter? {
        var prevDocTag = prevPsiSibling
        var nthParameter: Int = 0
        while (prevDocTag != null && prevDocTag is PhpDocTag) {
            if (prevDocTag is PhpDocParamTag) {
                nthParameter++
            }
            prevDocTag = prevDocTag.prevPsiSibling
        }
        val comment = parent as PhpDocComment
        val nextSibling = comment.nextPsiSibling
        val method = nextSibling as? Method ?: return null
        if (method.parameters.size > nthParameter) {
            return method.parameters[nthParameter]
        }
        return null
    }

}
