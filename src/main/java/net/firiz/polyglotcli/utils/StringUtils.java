package net.firiz.polyglotcli.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public final class StringUtils {

    private static final Pattern numberPattern = Pattern.compile("^[0-9]+$");

    private StringUtils() {
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().startsWith("linux");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean containOrs(@NotNull String value, String... values) {
        Objects.requireNonNull(value);
        for (final String v : values) {
            if (value.contains(v)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containOrs(@NotNull String value, List<String> values) {
        Objects.requireNonNull(value);
        for (final String v : values) {
            if (value.contains(v)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(@NotNull String value, String... values) {
        Objects.requireNonNull(value);
        for (final String v : values) {
            if (!value.contains(v)) {
                return false;
            }
        }
        return true;
    }

    public static boolean contains(@NotNull String value, List<String> values) {
        Objects.requireNonNull(value);
        for (final String v : values) {
            if (!value.contains(v)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkFileName(File parent, String name) {
        return checkFileName(parent, name, null, null);
    }

    private static final String[] forbiddenWords = new String[]{"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};

    public static boolean checkFileName(File parent, String name, Runnable forbidden, Runnable exists) {
        if (StringUtils.containOrs(name, forbiddenWords)) {
            if (forbidden != null) {
                forbidden.run();
            }
            return false;
        } else if (new File(parent, name).exists()) {
            if (exists != null) {
                exists.run();
            }
            return false;
        }
        return true;
    }

    public static String replace(String name, String replacement, String... words) {
        if (name == null) {
            return null;
        }
        for (String word : words) {
            name = name.replace(word, replacement);
        }
        return name;
    }

    public static String deletes(String name, String... words) {
        return replace(name, "", words);
    }

    public static String replaceForbiddenWords(String name) {
        return deletes(name, forbiddenWords);
    }

    public static boolean isNumber(String str) {
        return numberPattern.matcher(str).matches();
    }

    public static String linuxPath(String path) {
        return path.replaceAll("[A-Z]:", "/mnt/c").replace("\\", "/");
    }

}
