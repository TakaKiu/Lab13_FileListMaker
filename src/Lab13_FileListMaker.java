import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;

    class ListmakerApp {
    private static final String FILE_EXTENSION = ".txt";
    private static ArrayList<String> itemList = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            String choice = getValidMenuChoice();

            switch (choice.toUpperCase()) {
                case "A":
                    addItemToList();
                    break;
                case "D":
                    deleteItemFromList();
                    break;
                case "V": // Changed 'P' to 'V' for View
                    printListWithNumbers();
                    break;
                case "O": // Open a list file from disk
                    openList();
                    break;
                case "S": // Save the current list file to disk
                    saveList();
                    break;
                case "C": // Clear the list
                    clearList();
                    break;
                case "Q": // Quit the program
                    if (confirmQuit()) {
                        System.out.println("Goodbye!");
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("V - View the list");
        System.out.println("O - Open a list from disk");
        System.out.println("S - Save the list to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit the program");
    }

    private static String getValidMenuChoice() {
        String regex = "[AaDdVvOoSsCcQq]";
        String prompt = "Enter your choice (A/D/V/O/S/C/Q): ";
        return SafeInput.getRegExString(regex, prompt);
    }

    private static void addItemToList() {
        System.out.print("Enter item to add: ");
        String item = scanner.nextLine();
        itemList.add(item);
        needsToBeSaved = true;
        System.out.println("Item added to the list.");
    }

    private static void deleteItemFromList() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty. Nothing to delete.");
            return;
        }

        printListWithNumbers();
        int itemNumber = SafeInput.getRangedInt(1, itemList.size(), "Enter the item number to delete: ");
        itemList.remove(itemNumber - 1);
        needsToBeSaved = true;
        System.out.println("Item deleted from the list.");
    }

    private static void printList() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("List items:");
            for (String item : itemList) {
                System.out.println(item);
            }
        }
    }

    private static void printListWithNumbers() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("Numbered List:");
            for (int i = 0; i < itemList.size(); i++) {
                System.out.println((i + 1) + ". " + itemList.get(i));
            }
        }
    }

    private static void openList() {
        if (needsToBeSaved) {
            System.out.println("Current list has unsaved changes. Do you want to save it? (Y/N)");
            char choice = Character.toUpperCase(scanner.nextLine().charAt(0));
            if (choice == 'Y') {
                saveList();
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                itemList.clear();
                String line;
                while ((line = reader.readLine()) != null) {
                    itemList.add(line);
                }
                needsToBeSaved = false;
                System.out.println("List loaded from " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    private static void saveList() {
        if (!itemList.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.getName().endsWith(FILE_EXTENSION)) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + FILE_EXTENSION);
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                    for (String item : itemList) {
                        writer.write(item);
                        writer.newLine();
                    }
                    needsToBeSaved = false;
                    System.out.println("List saved to " + selectedFile.getAbsolutePath());
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            } else {
                System.out.println("No file selected.");
            }
        } else {
            System.out.println("The list is empty. Nothing to save.");
        }
    }

    private static void clearList() {
        if (!itemList.isEmpty()) {
            System.out.println("Are you sure you want to clear the list? (Y/N)");
            char choice = Character.toUpperCase(scanner.nextLine().charAt(0));
            if (choice == 'Y') {
                itemList.clear();
                needsToBeSaved = true;
                System.out.println("List cleared.");
            }
        } else {
            System.out.println("The list is already empty.");
        }
    }

    private static boolean confirmQuit() {
        if (needsToBeSaved) {
            System.out.println("Current list has unsaved changes. Do you want to save it before quitting? (Y/N)");
            char choice = Character.toUpperCase(scanner.nextLine().charAt(0));
            if (choice == 'Y') {
                saveList();
            }
        }

        return true;
    }
}