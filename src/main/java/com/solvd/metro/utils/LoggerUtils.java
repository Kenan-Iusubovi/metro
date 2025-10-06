package com.solvd.metro.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

public class LoggerUtils {

    private static final Logger logger = LogManager.getLogger(LoggerUtils.class);
    private static PrintStream originalSystemOut;
    private static PrintStream originalSystemErr;
    private static boolean isSystemOutRedirected = false;
    private static boolean isSystemErrRedirected = false;
    private static StringBuilder currentLine = new StringBuilder();
    private static Logger currentLogger = null;

    public static void redirectAllSystemStreams() {
        redirectSystemOut();
        redirectSystemErr();
    }

    public static void redirectSystemOut() {
        if (!isSystemOutRedirected) {
            originalSystemOut = System.out;

            PrintStream outPrintStream = new PrintStream(originalSystemOut) {
                @Override
                public void println(String message) {
                    logFullLine(message, "info");
                    logFullLine("", "info");
                }

                @Override
                public void println(Object messageObject) {
                    logFullLine(String.valueOf(messageObject), "info");
                    logFullLine("", "info");
                }

                @Override
                public void print(String message) {
                    logFullLine(message, "info");
                }

                @Override
                public void print(Object messageObject) {
                    logFullLine(String.valueOf(messageObject), "info");
                }

                @Override
                public void print(char[] charArray) {
                    logFullLine(String.valueOf(charArray), "info");
                }

                @Override
                public void println(char[] charArray) {
                    logFullLine(String.valueOf(charArray), "info");
                    logFullLine("", "info");
                }

                @Override
                public void print(char c) {
                    logFullLine(String.valueOf(c), "info");
                }

                @Override
                public void println() {
                    logFullLine("", "info");
                }
            };

            System.setOut(outPrintStream);
            isSystemOutRedirected = true;
            logger.info("System.out has been redirected to logger");
        }
    }

    public static void redirectSystemErr() {
        if (!isSystemErrRedirected) {
            originalSystemErr = System.err;

            PrintStream errPrintStream = new PrintStream(originalSystemErr) {
                @Override
                public void println(String errorMessage) {
                    logFullLine(errorMessage, "error");
                    logFullLine("", "error");
                }

                @Override
                public void println(Object errorObject) {
                    logFullLine(String.valueOf(errorObject), "error");
                    logFullLine("", "error");
                }

                @Override
                public void print(String errorMessage) {
                    logFullLine(errorMessage, "error");
                }

                @Override
                public void print(Object errorObject) {
                    logFullLine(String.valueOf(errorObject), "error");
                }

                @Override
                public void print(char[] charArray) {
                    logFullLine(String.valueOf(charArray), "error");
                }

                @Override
                public void println(char[] charArray) {
                    logFullLine(String.valueOf(charArray), "error");
                    logFullLine("", "error");
                }

                @Override
                public void print(char c) {
                    logFullLine(String.valueOf(c), "error");
                }

                @Override
                public void println() {
                    logFullLine("", "error");
                }
            };

            System.setErr(errPrintStream);
            isSystemErrRedirected = true;
            logger.info("System.err has been redirected to logger");
        }
    }

    private static void logFullLine(String message, String level) {
        Logger logger = getCallingLogger();

        currentLine.append(message);

        if (message.isEmpty() || message.endsWith("\n")) {
            String fullMessage = currentLine.toString().trim();
            if (!fullMessage.isEmpty()) {
                if ("error".equals(level)) {
                    logger.error("[SYSTEM_ERR] " + fullMessage);
                } else {
                    logger.info("[SYSTEM_OUT] " + fullMessage);
                }
            }
            currentLine.setLength(0);
            currentLogger = null;
        }
    }

    private static Logger getCallingLogger() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (int i = 0; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();

            if (!className.equals(LoggerUtils.class.getName()) &&
                    !className.startsWith("java.") &&
                    !className.startsWith("sun.") &&
                    !className.startsWith("jdk.internal.") &&
                    !className.contains("LoggerUtils$")) {

                String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
                return LogManager.getLogger(simpleClassName);
            }
        }

        return LogManager.getLogger("Main");
    }

    public static void restoreSystemOut() {
        if (isSystemOutRedirected && originalSystemOut != null) {
            System.setOut(originalSystemOut);
            isSystemOutRedirected = false;
            logger.info("System.out has been restored");
        }
    }

    public static void restoreSystemErr() {
        if (isSystemErrRedirected && originalSystemErr != null) {
            System.setErr(originalSystemErr);
            isSystemErrRedirected = false;
            logger.info("System.err has been restored");
        }
    }

    public static void restoreAllSystemStreams() {
        restoreSystemOut();
        restoreSystemErr();
    }

    public static Logger getLogger() {
        return logger;
    }
}