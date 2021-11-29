package net.firiz.polyglotcli;

import org.graalvm.polyglot.Engine;

import java.util.Collections;
import java.util.Set;

public final class APIConstants {

    private APIConstants() {
    }

    public static final Set<String> INSTALLED_LANGUAGES;

    static {
        INSTALLED_LANGUAGES = Collections.unmodifiableSet(Engine.create().getLanguages().keySet());
    }
}
