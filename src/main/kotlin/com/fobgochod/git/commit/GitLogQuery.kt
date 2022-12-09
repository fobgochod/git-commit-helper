package com.fobgochod.git.commit

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern

class GitLogQuery(private val workingDirectory: File) {

    companion object {
        const val GIT_LOG_COMMAND: String = "git log --all --format=%s";
        val COMMIT_FIRST_LINE_FORMAT: Pattern = Pattern.compile("^[a-z]+\\((.+)\\):.*");
    }

    fun execute(): Result {
        try {
            val osName: String = System.getProperty("os.name");
            val processBuilder: ProcessBuilder = if (osName.contains("Windows")) {
                ProcessBuilder("cmd", "/C", GIT_LOG_COMMAND);
            } else {
                ProcessBuilder("sh", "-c", GIT_LOG_COMMAND);
            }

            val process: Process = processBuilder
                .directory(workingDirectory)
                .start();

            val reader = BufferedReader(InputStreamReader(process.inputStream));
            val output: List<String> = reader.lines().toList();

            process.waitFor(2, TimeUnit.SECONDS);
            process.destroy();
            process.waitFor();

            return Result(process.exitValue(), output);
        } catch (e: Exception) {
            return Result(-1);
        }
    }

    inner class Result(private val exitValue: Int) {

        private var logs: List<String> = emptyList()

        constructor(exitValue: Int, logs: List<String>) : this(exitValue) {
            this.logs = logs;
        }

        fun isSuccess(): Boolean {
            return exitValue == 0;
        }

        fun getScopes(): Set<String> {
            val scopes: Set<String> = HashSet();

            logs.forEach { log ->
                val matcher: Matcher = COMMIT_FIRST_LINE_FORMAT.matcher(log);
                if (matcher.find()) {
                    scopes.plus(matcher.group(1));
                }
            }
            return scopes;
        }
    }

}