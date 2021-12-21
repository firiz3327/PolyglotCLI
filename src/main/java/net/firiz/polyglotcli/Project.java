package net.firiz.polyglotcli;

import net.firiz.polyglotcli.exceptions.ProjectException;
import net.firiz.polyglotcli.language.LanguageType;
import net.firiz.polyglotcli.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class Project {

    /**
     * Main Arguments
     */
    @Argument(metaVar = "language", required = true, usage = "Sets a language")
    private String language;
    @Argument(index = 1, metaVar = "main", required = true, usage = "Sets a main")
    private String main;
    @Nullable
    @Argument(index = 2, metaVar = "arguments", usage = "Sets a arguments")
    private String[] arguments;
    @Nullable
    @Option(name = "-m", metaVar = "module", usage = "Sets a module")
    @SuppressWarnings("all")
    private String module = "default";
    @Nullable
    @Option(name = "-w", metaVar = "workingDirectory", usage = "Sets a working directory")
    private File workingDirectory;

    /**
     * Java Options
     */
    @Nullable
    @Option(name = "-classpath", usage = "Sets a classpath")
    private String classPath;
    @Nullable
    @Option(name = "-jdk", usage = "Sets a jdk")
    private String jdk;

    private static final Pattern windowsPathPattern = Pattern.compile("[A-Z]:");

    @Nullable
    private LanguageType languageType;

    public void check() {
        if (language == null) {
            throw new ProjectException("If you want the module to be graalvm, please set the language.");
        }
        languageType = LanguageType.search(language);
        if (!languageType.isSupported()) {
            throw new ProjectException(language + " is not supported.");
        }
        if (main == null) {
            throw new ProjectException("main is null.");
        }
        if (languageType == LanguageType.JAVA) {
            if (classPath == null) {
                throw new ProjectException("In the java language, the classes argument is mandatory.");
            }
        } else { // parse linux path
            if (StringUtils.isLinux() && windowsPathPattern.matcher(main).find()) {
                main = StringUtils.linuxPath(main);
            }
            final File mainFile = new File(main);
            if (!mainFile.exists() || mainFile.isDirectory()) {
                throw new ProjectException("Failed to load the main file.");
            }
        }
        // classpath parse linux path
        if (StringUtils.isLinux()) {
            if (workingDirectory != null && windowsPathPattern.matcher(workingDirectory.getPath()).find()) {
                workingDirectory = new File(StringUtils.linuxPath(workingDirectory.getPath()));
            }
            if (classPath != null) {
                final StringJoiner next = new StringJoiner(";");
                for (final String path : classPath.split(";")) {
                    if (windowsPathPattern.matcher(path).find()) {
                        next.add(StringUtils.linuxPath(path));
                    } else {
                        next.add(path);
                    }
                }
                classPath = next.toString();
            }
        }
    }

    public String getMain() {
        return main;
    }

    public File getMainFile() {
        return new File(main);
    }

    public String[] getArguments() {
        return arguments;
    }

    public @Nullable String getModule() {
        return module;
    }

    public @NotNull File getWorkingDirectory() {
        return workingDirectory == null ? getMainFile().getParentFile() : workingDirectory;
    }

    public @Nullable String getLanguage() {
        return language;
    }

    public @Nullable String getClassPath() {
        return classPath;
    }

    public @Nullable String getJdk() {
        return jdk;
    }

    public @Nullable LanguageType getLanguageType() {
        return languageType;
    }

    @Override
    public String toString() {
        return "Project{" +
                "language='" + language + '\'' +
                ", main='" + main + '\'' +
                ", arguments=" + Arrays.toString(arguments) +
                ", module='" + module + '\'' +
                ", workingDirectory=" + workingDirectory +
                ", classPath='" + classPath + '\'' +
                '}';
    }
}
