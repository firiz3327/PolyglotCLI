package net.firiz.polyglotcli.exec;

import net.firiz.polyglotcli.Project;
import org.graalvm.polyglot.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Consumer;

public interface Exec {

    int exec(Project project);

    default Context createContext(@NotNull Project project, @NotNull String... permittedLanguages) throws IOException {
        return createContext(project, null, permittedLanguages);
    }

    default Context createContext(@NotNull Project project, @Nullable Consumer<Context.Builder> consumer, @NotNull String... permittedLanguages) throws IOException {
        try (final InputStream inputStream = new ByteArrayInputStream(String.join(System.lineSeparator(), Arrays.asList(project.getArguments())).getBytes(StandardCharsets.UTF_8))) {
            final Context.Builder builder = Context.newBuilder(permittedLanguages)
                    .in(inputStream)
                    .out(System.out)
                    .err(System.err)
                    .allowExperimentalOptions(true)
                    .allowAllAccess(true)
                    .currentWorkingDirectory(project.getWorkingDirectory().getAbsoluteFile().toPath());
            if (consumer != null) {
                consumer.accept(builder);
            }
            return builder.build();
        }
    }

}
