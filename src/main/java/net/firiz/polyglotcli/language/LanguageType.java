package net.firiz.polyglotcli.language;

import net.firiz.polyglotcli.APIConstants;
import net.firiz.polyglotcli.exec.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public enum LanguageType {
    UNKNOWN(new NotSupportExec(), "unknown"),
    JS("js", "javascript"),
    PYTHON("python", "py"),
    RUBY("ruby", "rb"),
    LLVM(new LLVMExec(), "llvm", "c", "c++", "cpp"),
    JAVA(new JavaExec(), "java"),
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

    LanguageType(final String... alias) {
        this(new ScriptExec(), alias);
    }

    LanguageType(@NotNull final Exec exec, final String... alias) {
        this.exec = exec;
        this.name = alias[0];
        this.alias = Arrays.asList(alias);
        this.supported = APIConstants.INSTALLED_LANGUAGES.contains(name);
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
