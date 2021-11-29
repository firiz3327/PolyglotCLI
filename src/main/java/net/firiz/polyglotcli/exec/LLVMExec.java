package net.firiz.polyglotcli.exec;

import net.firiz.polyglotcli.Project;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.IOException;

public class LLVMExec implements Exec {

    @Override
    public int exec(Project project) {
        try (final Context context = createContext(
                project,
                builder -> builder.arguments("llvm", project.getArguments()),
                "llvm"
        )) {
            final Value value = context.eval(Source.newBuilder("llvm", project.getMainFile()).build());
            final Value exit = value.execute();
            if (exit.isNumber()) {
                return exit.asInt();
            }
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 255;
    }

}
