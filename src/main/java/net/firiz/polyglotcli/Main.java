package net.firiz.polyglotcli;

import net.firiz.polyglotcli.language.LanguageType;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public final class Main {

    /**
     * 渡されたコードを実行し結果を標準出力へ書き込みます<br>
     * <br>
     * コードの実行に成功した場合、標準出力にhtmlエスケープされたjson(log及びreturnValue)を書き込みます<br>
     * コードの実行に失敗した場合、標準エラー出力にエラー内容を書き込みます<br>
     * <br>
     * LLVM以外ではタイムアウト処理をしていないので、送信側で施してください<br>
     *
     * @param args プログラム実行時の引数
     */
    private void start(String[] args) {
        final Project project = new Project();
        final CmdLineParser parser = new CmdLineParser(project);
        int exit = -1;
        try {
            parser.parseArgument(args);
            project.check();
            final LanguageType languageType = project.getLanguageType();
            if (languageType != null) {
                exit = languageType.getExec().exec(project);
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
        System.exit(exit);
    }

    public static void main(String[] args) {
        new Main().start(args);
    }

    private Main() {
    }

}
