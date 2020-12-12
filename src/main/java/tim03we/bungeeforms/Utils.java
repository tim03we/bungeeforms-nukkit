package tim03we.bungeeforms;

public class Utils {

    public static boolean isOutOfBound(String[] data, int i) {
        try {
            String str = data[i];
            return false;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return true;
        }
    }

    public static boolean isOutOfBound(String string, String split, int i) {
        try {
            String str = string.split(split)[i];
            return false;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return true;
        }
    }
}
