package net.firiz.polyglotcli.language;

import net.firiz.polyglotcli.APIConstants;
import net.firiz.polyglotcli.exec.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum LanguageType {
    UNKNOWN(NotSupportExec::new, "unknown"),
    JS("js", "javascript"),
    PYTHON("python", "py"),
    RUBY("ruby", "rb"),
    LLVM(LLVMExec::new, "llvm", "c", "c++", "cpp"),
    JAVA(JavaExec::new, "java"),
//    NODEJS(NodeJSExec::new, "nodejs")
//    R(t -> new NotSupportExec(), false, "R", "r"),
//    WASM(WASMExec::new, "wasm")
    ;

    // 実際の実行環境のインストールは下記参照
    // https://www.graalvm.org/docs/getting-started/

    @NotNull
    private final Exec exec;
    @NotNull
    private final String name;
    @NotNull
    private final List<String> alias;
    private final boolean supported;

    LanguageType(@NotNull final Supplier<Exec> exec, final String... alias) {
        this(exec, APIConstants.INSTALLED_LANGUAGES.contains(alias[0]), alias);
    }

    LanguageType(final String... alias) {
        this.exec = new ScriptExec();
        this.name = alias[0];
        this.alias = Arrays.asList(alias);
        this.supported = APIConstants.INSTALLED_LANGUAGES.contains(name);
    }

    LanguageType(@NotNull final Supplier<Exec> exec, boolean supported, final String... alias) {
        this.exec = exec.get();
        this.name = alias[0];
        this.alias = Arrays.asList(alias);
        this.supported = supported;
    }

    @NotNull
    public static LanguageType search(@Nullable final String name) {
        if (name == null) {
            return UNKNOWN;
        }
        return Arrays.stream(values()).filter(type -> type.alias.contains(name.toLowerCase())).findFirst().orElse(UNKNOWN);
    }

    @NotNull
    public Exec getExec() {
        return exec;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public boolean isSupported() {
        return supported;
    }
}
