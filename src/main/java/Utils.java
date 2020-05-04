import java.util.Random;

public class Utils {

    final private static Random r = new Random(System.currentTimeMillis());

    public static Random getR() {
        return r;
    }
}
