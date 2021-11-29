package net.firiz.polyglotcli.exec;

import net.firiz.polyglotcli.Project;

public class NotSupportExec implements Exec {
    @Override
    public int exec(Project project) {
        return 255;
    }
}
