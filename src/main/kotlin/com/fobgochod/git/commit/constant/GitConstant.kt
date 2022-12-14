package com.fobgochod.git.commit.constant

import java.util.regex.Pattern

object GitConstant {

    /**
     * 常用类型个数
     */
    const val COMMON_TYPE_COUNT = 3;

    val DEFAULT_TEMPLATE = """
                <type>(<scope>): <subject>
                <BLANK LINE>
                <body>
                <BLANK LINE>
                <footer>
            """.trimIndent()

    /**
     * https://stackoverflow.com/a/2120040/5138796
     */
    const val MAX_LINE_LENGTH = 72

    const val BREAKING_CHANGE_PREFIX = "BREAKING CHANGE: "
    const val CLOSES_PREFIX = "Closes "
    const val SKIP_CI = "[skip ci]"

    val HEADER_PATTERN: Pattern = Pattern.compile("^([a-z]+)(\\((.+)\\))?: (.+)")
    val CLOSED_ISSUES_PATTERN: Pattern = Pattern.compile("Closes (.+)")
}
