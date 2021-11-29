import java.util.Arrays;

public class Test {
    public static void main(String[] var0) {
        System.out.println("Hello World! args: " + Arrays.toString(var0));
        System.out.println(System.getProperty("java.home"));
//        System.exit(1); // PolyglotException が発生する
    }
}
