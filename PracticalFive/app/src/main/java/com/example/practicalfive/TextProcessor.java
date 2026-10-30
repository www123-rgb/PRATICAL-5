package com.example.practicalfive;

import java.util.Locale;

/**
 * Formats municipality text before it is shown or shared.
 */
public final class TextProcessor {

    private TextProcessor() {
    }

    public static boolean isValidIndex(int index, int length) {
        return index >= 0 && index < length;
    }

    public static String formatTitle(String name) {
        if (name == null) {
            return "";
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        return trimmed.substring(0, 1).toUpperCase(Locale.ROOT) + trimmed.substring(1);
    }

    public static String normalizeHistory(String history) {
        if (history == null) {
            return "";
        }
        return history.trim().replaceAll("\\s+", " ");
    }

    public static int wordCount(String text) {
        String normalized = normalizeHistory(text);
        if (normalized.isEmpty()) {
            return 0;
        }
        return normalized.split(" ").length;
    }

    public static String buildShareMessage(String name, String history, String footer) {
        return formatTitle(name) + "\n\n" + normalizeHistory(history) + "\n\n" + footer;
    }
}
