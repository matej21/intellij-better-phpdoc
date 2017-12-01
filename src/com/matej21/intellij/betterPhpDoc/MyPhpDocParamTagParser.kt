package com.matej21.intellij.betterPhpDoc

import com.jetbrains.php.lang.documentation.phpdoc.parser.tags.PhpDocParamTagParser
import com.jetbrains.php.lang.documentation.phpdoc.parser.tags.PhpDocTagParser
import com.jetbrains.php.lang.parser.PhpPsiBuilder


class MyPhpDocParamTagParser : PhpDocParamTagParser() {

    override fun parseContents(builder: PhpPsiBuilder): Boolean {
        if (PhpDocTagParser.parseVar(builder)) {
            return true
        }
        var start = builder.mark()
        if (builder.parseTypes() && PhpDocTagParser.parseVar(builder)) {
            start.drop()
            return true
        }
        start.rollbackTo()
        start = builder.mark()
        if (builder.parseTypes()) {
            start.drop()
            return true
        }
        start.rollbackTo()
        return false
    }


}
