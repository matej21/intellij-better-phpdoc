package com.matej21.intellij.betterPhpDoc

import com.jetbrains.php.lang.documentation.phpdoc.parser.tags.PhpDocTagParser
import com.jetbrains.php.lang.documentation.phpdoc.parser.tags.PhpDocVarTagParser
import com.jetbrains.php.lang.parser.PhpPsiBuilder

class MyPhpDocVarTagParser() : PhpDocVarTagParser() {

    override fun parseContents(builder: PhpPsiBuilder): Boolean {
        if (PhpDocTagParser.parseVar(builder)) {
            builder.parseTypes()
            return true
        } else {
            val start = builder.mark()
            if (builder.parseTypes() && PhpDocTagParser.parseVar(builder)) {
                start.drop()
                return true
            } else {
                start.rollbackTo()
                if (!builder.parseTypes()) {
                }

                return false
            }
        }
    }

}
