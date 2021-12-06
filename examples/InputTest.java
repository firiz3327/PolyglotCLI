import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class InputTest {
    public static void main(String[] var0) {
        System.out.println("input");
        try (InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(is);){
            String moji = reader.readLine();
            System.out.println("text: " + moji);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
