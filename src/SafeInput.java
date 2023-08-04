import java.util.Scanner;
import java.util.regex.Pattern;

public class SafeInput {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getRegExString(String regex, String prompt) {
        Pattern pattern = Pattern.compile(regex);
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (pattern.matcher(input).matches()) {
                return input;
            }
            System.out.println("Invalid input. Try again.");
        }
    }

    public static int getRangedInt(int min, int max, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Input out of range. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static boolean getYNConfirm(String prompt) {
        while (true) {
            System.out.print(prompt);
            String response = scanner.nextLine().toUpperCase();
            if (response.equals("Y")) {
                return true;
            } else if (response.equals("N")) {
                return false;
            }
            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
        }
    }
}
