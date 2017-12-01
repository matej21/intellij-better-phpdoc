package com.matej21.intellij.betterPhpDoc

import com.jetbrains.php.lang.documentation.phpdoc.parser.tags.PhpDocReturnTagParser
import com.jetbrains.php.lang.parser.PhpPsiBuilder

class MyPhpDocReturnTagParser() : PhpDocReturnTagParser() {
    override fun parseContents(builder: PhpPsiBuilder): Boolean {
        builder.parseTypes()
        return true
    }
}
