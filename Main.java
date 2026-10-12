//Avery Phelps
//CS303 - Program 3
//10.11.2026


import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        System.out.println("Welcome to Program 3: Creating MyList");
        Scanner input = new Scanner(System.in);
        String fileName = Functions.getFileName(input);
        MyArrayList<String> list = Functions.loadData(fileName);

        String choice = Functions.getMenuItem(input);
        while (!choice.equals("Q")) {
            switch (choice) {
                case "A":
                    Functions.addValue(input, list);
                    break;
                case "B":
                    Functions.addValues(input, list);
                    break;
                case "C":
                    Functions.checkValues(input, list);
                    break;
                case "D":
                    Functions.deleteValue(input, list);
                    break;
                case "E":
                    Functions.deleteList(input, list);
                    break;
                case "F":
                    Functions.findValue(input, list);
                    break;
                case "M":
                    Functions.modifyValue(input, list);
                    break;
                case "P":
                    Functions.printList(list);
                    break;
                default:
                    Functions.invalidMenuSelection();
            }
            choice = Functions.getMenuItem(input);
        }
        input.close();
    }  

}
//Sources:
//Copilot LLM used for error checking and questions about syntax
//also helped with formatting and code redundancy