import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Functions {
    //PRE: accepts both input files
    //POST: reads data and loads into MyArrayList, returns MyArrayList
    public static MyArrayList<String> loadData(String fileName) {
        MyArrayList<String> list = new MyArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    for (String value : line.split("\\s+")) {
                        list.add(value);
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
        return list;
    }
    //PRE: accepts scanner input
    //POST: prompts user to choose A1input or A2input files, if valid file, returns file name, else throws exception
    public static String getFileName(Scanner input) {
        while (true) {
            System.out.println("Please select a file to load:");
            System.out.println("1: A1input.txt");
            System.out.println("2: A2input.txt");
            String choice = input.nextLine();
            if (choice.equals("1")) {
                System.out.println("A1input.txt selected.");
                return "A1input.txt";
            } else if (choice.equals("2")) {
                System.out.println("A2input.txt selected.");
                return "A2input.txt";
            }
            System.out.println("Invalid file selection. Please choose 1 or 2.");
        }
    }
    //PRE: accepts scanner input
    //POST: returns user input for menu selection
    public static String getMenuItem(Scanner input) {
        System.out.println();
        System.out.println("Please select a menu item:");
        System.out.println("A: Add an value to the list");
        System.out.println("B: Add a collection of values to the list");
        System.out.println("C: Check if the list contains all values in a collection");
        System.out.println("D: Delete an value from the list");
        System.out.println("E: Remove a collection of values from the list");
        System.out.println("F: Find an value in the list");
        System.out.println("M: Modify an value in the list");
        System.out.println("P: Print the List");
        System.out.println("Q: Exit the program");
        return input.nextLine().toUpperCase();
    }
    //PRE: accepts scanner input and MyArrayList
    //POST: prompts user to enter a value to add to list
    //      reprompts if the input is not an integer
    public static void addValue(Scanner input, MyArrayList<String> list) {
        while (true) {
            String value = getValue(input, "Please enter one value to add to the list:");
            Integer integerValue = parseInteger(value.trim());
            if (integerValue == null) {
                System.out.println("Invalid input. Enter one integer value only.");
                continue;
            }
            String normalizedValue = integerValue.toString();
            list.add(normalizedValue);
            System.out.println("Added \"" + normalizedValue + "\" to the list.");
            return;
        }
    }
    //PRE: accepts scanner input and MyArrayList
    //POST: prompts user to enter a collection of values to add to array
    //      checks for valid integer input and reprompts if invalid
    public static void addValues(Scanner input, MyArrayList<String> list) {
        ArrayList<String> values = getIntegerValues(input,
                "Please enter values to add (separated by spaces or commas):");
        list.addAll(values);
        System.out.println("Added values: " + String.join(", ", values) + ".");
    }
    //PRE: accepts scanner input and MyArrayList
    //POST: prompts user to enter a collection of values to check against the list
    public static void checkValues(Scanner input, MyArrayList<String> list) {
        ArrayList<String> values = getIntegerValues(input,
                "Please enter integer values to check (separated by spaces or commas):");
        if (list.containsAll(values)) {
            System.out.println("The list contains all of the requested values.");
        } else {
            System.out.println("The list does not contain all of the requested values.");
        }
    }
    //PRE: accepts scanner input and MyArrayList
    //POST: prompts user to enter an index and deletes its value from the list
    //      reprompts if invalid integer or index out of bounds
    public static void deleteValue(Scanner input, MyArrayList<String> list) {
        if (list.isEmpty()) {
            System.out.println("The list is empty; there is nothing to delete.");
            return;
        }
        int index = getExistingIndex(input, list, "Please enter the index to delete:");
        String removedValue = list.remove(index);
        System.out.println("Item: \"" + removedValue + "\" at index: \"" + index
                + "\" has been removed.");
    }
    //PRE: accepts scanner input and MyArrayList
    //POST: prompts user to enter a collection of values to remove from the list
    //      ignores invalid values and removes matching integers
    public static void deleteList(Scanner input, MyArrayList<String> list) {
        String line = getValue(input,
                "Please enter values to remove (separated by spaces or commas):").trim();
        ArrayList<String> valuesToRemove = new ArrayList<>();
        for (String value : line.split("[,\\s]+")) {
            if (value.isEmpty()) {
                continue;
            }
            Integer integerValue = parseInteger(value);
            if (integerValue == null) {
                System.out.println("Ignoring invalid value \"" + value + "\".");
            } else {
                valuesToRemove.add(integerValue.toString());
            }
        }

        ArrayList<String> removedValues = new ArrayList<>();
        for (Object item : list.toArray()) {
            if (valuesToRemove.contains(item)) {
                removedValues.add((String) item);
            }
        }

        if (removedValues.isEmpty()) {
            System.out.println("No matching values were found.");
        } else {
            list.removeAll(valuesToRemove);
            System.out.println("Removed values: " + String.join(", ", removedValues) + ".");
        }
    }
    //PRE: accepts scanner input and MyArrayList
    //POST: prompts user to enter a value to find in the list
    public static void findValue(Scanner input, MyArrayList<String> list) {
        String value = getValue(input, "Please enter a value to find:");
        int index = list.indexOf(value);
        if (index >= 0) {
            System.out.println("Found \"" + list.get(index) + "\" at index " + index + ".");
        } else {
            System.out.println("Value \"" + value + "\" was not found.");
        }
    }
    //PRE: accepts scanner input and MyArrayList
    //POST: prompts user to enter an index
    //      if valid, prompts user to enter a replacement value at that index
    public static void modifyValue(Scanner input, MyArrayList<String> list) {
        if (list.isEmpty()) {
            System.out.println("The list is empty; there is nothing to modify.");
            return;
        }
        int index = getExistingIndex(input, list, "Please enter the index to modify:");
        String replacement = readIntegerValue(input, "Please enter an integer replacement value:");
        String previousValue = list.set(index, replacement);
        System.out.println("Replaced \"" + previousValue + "\" at index " + index
                + " with \"" + replacement + "\".");
    }
    //PRE: accepts MyArrayList
    //POST: helper method for printing the list to a report file
    public static void printList(MyArrayList<String> list) {
        writeListToReport(list);
    }
    //PRE: accepts MyArrayList
    //POST: writes the list to report.txt
    public static void writeListToReport(MyArrayList<String> list) {
        try (PrintWriter writer = new PrintWriter(new File("report.txt"))) {
            for (int i = 0; i < list.size(); i++) {
                writer.print(list.get(i));
                if ((i + 1) % 10 == 0 && i + 1 < list.size()) {
                    writer.println();
                } else if (i + 1 < list.size()) {
                    writer.print(" ");
                }
            }
            if (!list.isEmpty()) {
                writer.println();
            }
            System.out.println("List written to report.txt.");
        } catch (FileNotFoundException e) {
            System.err.println("Could not write report.txt: " + e.getMessage());
        }
    }
    //PRE: none
    //POST: helper method for invalid menu selection
    public static void invalidMenuSelection() {
        System.out.println("Invalid menu selection. Please try again.");
    }
    //PRE: accepts scanner input and prompt message
    //POST: helper method for getting a single value from user input
    private static String getValue(Scanner input, String prompt) {
        System.out.println(prompt);
        return input.nextLine();
    }
    //PRE: accepts scanner input and prompt message
    //POST: returns integer values, reprompting until all values are valid
    private static ArrayList<String> getIntegerValues(Scanner input, String prompt) {
        while (true) {
            String line = getValue(input, prompt).trim();
            if (line.isEmpty()) {
                System.out.println("Invalid input. Enter at least one integer.");
                continue;
            }

            ArrayList<String> values = new ArrayList<>();
            boolean valid = true;
            for (String value : line.split("[,\\s]+")) {
                Integer integerValue = parseInteger(value);
                if (integerValue == null) {
                    System.out.println("Invalid value \"" + value
                            + "\". Enter integers separated by commas or spaces.");
                    valid = false;
                    break;
                }
                values.add(integerValue.toString());
            }
            if (valid) {
                return values;
            }
        }
    }
    //PRE: accepts scanner input and prompt message
    //POST: helper method for getting a single integer value from user input
    private static String readIntegerValue(Scanner input, String prompt) {
        while (true) {
            Integer value = parseInteger(getValue(input, prompt).trim());
            if (value != null) {
                return value.toString();
            }
            System.out.println("Invalid input. Please enter an integer value.");
        }
    }

    //PRE: accepts a string value
    //POST: helper method for parsing a string to an Integer
    //      returns null if string is invalid
    private static Integer parseInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    //PRE: accepts scanner input, MyArrayList, and prompt message
    //POST: helper method for getting a valid index from user input
    private static int getExistingIndex(Scanner input, MyArrayList<String> list,
                                        String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = input.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Invalid input. Please enter an integer index.");
                continue;
            }
            try {
                int index = Integer.parseInt(line);
                if (index >= 0 && index < list.size()) {
                    return index;
                }
                System.out.println("Index out of range. Enter an index from 0 to "
                        + (list.size() - 1) + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer index.");
            }
        }
    }
}
