package tracker;

public class Utils {

    public static String calcHash(String value) {
        return "" + Math.abs(value.hashCode());
    }
}
