package tracker;

public class Checker {
    public static boolean isNameValid(String name) {
        return name.matches("(?i)^([A-Za-z]['-]?)+[A-Za-z]$");
    }

    public static boolean isEmailValid(String email) {
        return email.matches("[A-Za-z'--.0-9]+@[A-Za-z'--0-9]+\\.[A-Za-z0-9]+");
    }

    public static boolean isCredsValid(String[] creds) {
        return creds.length > 1 && (creds[creds.length - 1].contains(".") || creds[creds.length - 1].contains("@"));
    }
}
