package com.matej21.intellij.betterPhpDoc

import com.intellij.lang.PsiBuilder
import com.jetbrains.php.lang.documentation.phpdoc.lexer.PhpDocTokenTypes
import com.jetbrains.php.lang.documentation.phpdoc.parser.PhpDocElementTypes
import com.jetbrains.php.lang.parser.PhpPsiBuilder
import com.jetbrains.php.lang.parser.parsing.Namespace

fun PhpPsiBuilder.parseTypes(): Boolean {

    var atLeastOneTypeParsed = false

    while (this.compare(PhpDocTokenTypes.DOC_IDENTIFIER)
            || this.compare(PhpDocTokenTypes.DOC_NAMESPACE)
            || this.compare(PhpDocTokenTypes.DOC_VARIABLE) && "\$this" == this.tokenText
            || this.compare(PhpDocTokenTypes.DOC_LPAREN)
            || this.compare(PhpDocTokenTypes.DOC_HASH)
            ) {
        this.compareAndEat(PhpDocTokenTypes.DOC_LPAREN)
        val type = this.mark()
        this.compareAndEat(PhpDocTokenTypes.DOC_HASH)
        Namespace.parseReference(this)
        if (!this.compareAndEat(PhpDocTokenTypes.DOC_IDENTIFIER) && "\$this" == this.tokenText) {
            this.advanceLexer()
        }

        var array: PsiBuilder.Marker
        array = this.mark()
        while (this.compareAndEat(PhpDocTokenTypes.DOC_LBRACKET) && this.compareAndEat(PhpDocTokenTypes.DOC_RBRACKET)) {
            array.drop()
            array = this.mark()
        }

        array.rollbackTo()
        if (this.compare(PhpDocTokenTypes.DOC_TEXT) && this.tokenText == "<") {
            this.advanceLexer()
            do {
                this.parseTypes()
            } while (this.compareAndEat(PhpDocTokenTypes.DOC_COMMA))
            if (this.compare(PhpDocTokenTypes.DOC_TEXT) && this.tokenText == ">") {
                this.advanceLexer()
            }
        }
        atLeastOneTypeParsed = true
        type.done(PhpDocElementTypes.phpDocType)
        this.compareAndEat(PhpDocTokenTypes.DOC_RPAREN)
        if (!this.compareAndEat(PhpDocTokenTypes.DOC_PIPE) && !this.compareAndEat(PhpDocTokenTypes.DOC_AMPERSAND)) {
            break
        }
    }

    return atLeastOneTypeParsed
}
