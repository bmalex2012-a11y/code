import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputHelper {
    private static final Scanner SC = new Scanner(System.in);

    private static String readLine(String prompt) {
        System.out.print(prompt);
        try {
            return SC.nextLine().trim();
        } catch (NoSuchElementException e) {
            System.out.println("\nEnd of input. Goodbye.");
            System.exit(0);
            return "";
        }
    }

    public static String readNonEmpty(String prompt) {
        while (true) {
            String s = readLine(prompt);
            if (!s.isEmpty()) return s;
            System.out.println("  Empty value, try again.");
        }
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(readLine(prompt));
                if (v >= min && v <= max) return v;
                System.out.println("  Enter a number between " + min + " et " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid integer.");
            }
        }
    }

    public static double readPositiveDouble(String prompt) {
        while (true) {
            try {
                double v = Double.parseDouble(readLine(prompt).replace(",", "."));
                if (v > 0 && !Double.isInfinite(v)) return v;
                System.out.println("  The amount must be strictly positive.");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid number.");
            }
        }
    }

    public static boolean confirm(String prompt) {
        while (true) {
            String s = readLine(prompt + " (y/n) : ").toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("  Answer with y or n.");
        }
    }
}
