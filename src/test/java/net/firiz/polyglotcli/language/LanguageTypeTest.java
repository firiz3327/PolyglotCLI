package net.firiz.polyglotcli.language;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTypeTest {

    @Test
    void search() {
        assertEquals(LanguageType.JAVA, LanguageType.search("Java"));
        assertEquals(LanguageType.JS, LanguageType.search("JS"));
        assertEquals(LanguageType.JS, LanguageType.search("JavaScript"));
    }
}