package com.matej21.intellij.betterPhpDoc

import com.jetbrains.php.lang.documentation.phpdoc.parser.tags.PhpDocPropertyTagParser
import com.jetbrains.php.lang.documentation.phpdoc.parser.tags.PhpDocTagParser
import com.jetbrains.php.lang.parser.PhpPsiBuilder

class MyPhpDocPropertyTagParser() : PhpDocPropertyTagParser() {
    override fun parseContents(builder: PhpPsiBuilder): Boolean {
        builder.parseTypes()
        PhpDocTagParser.parseProperty(builder)
        return true
    }
}
