package net.firiz.polyglotcli.exec;

import net.firiz.polyglotcli.Project;
import net.firiz.polyglotcli.exceptions.ProjectException;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

import java.io.IOException;

public class ScriptExec implements Exec {

    @Override
    public int exec(Project project) {
        if (project.getLanguageType() == null || project.getLanguage() == null) {
            throw new ProjectException("project is not checked.");
        }
        try (final Context context = createContext(project, project.getLanguageType().getName())) {
            context.eval(Source.newBuilder(project.getLanguage(), project.getMainFile()).build());
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 255;
    }
}
