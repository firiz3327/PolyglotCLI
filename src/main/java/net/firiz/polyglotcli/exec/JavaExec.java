package net.firiz.polyglotcli.exec;

import net.firiz.polyglotcli.Project;
import net.firiz.polyglotcli.utils.StringUtils;
import net.firiz.polyglotcli.exceptions.ProjectException;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JavaExec implements Exec {

    @Override
    public int exec(Project project) {
        String module = project.getModule();
        if (module == null || module.equals("default")) {
            module = System.getProperty("java.home");
        } else if (StringUtils.isLinux()) {
            module = StringUtils.linuxPath(module);
        }
        return espresso(project, module);
    }

    private int espresso(Project project, String module) {
        if (project.getClassPath() == null) {
            throw new ProjectException("project is not checked.");
        }
        final Map<String, String> options = new HashMap<>();
        options.put("java.Classpath", project.getClassPath());
        options.put("java.JavaHome", module);
        try (final Context context = createContext(
                project,
                builder -> builder.options(options),
                "java"
        )) {
            final Value mainClass = context.getBindings("java").getMember(project.getMain());
            mainClass.invokeMember("main", (Object) project.getArguments());
            return 0; // not support System.exit
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 255;
    }

}
