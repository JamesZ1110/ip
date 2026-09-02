import java.util.Scanner;

public class Exia {
    public static void main(String[] args) {
        String line = "____________________________________________________________";

        System.out.println("Hello! I'm Exia");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            System.out.println(line);
            System.out.println("I don't understand yet, but I heard: " + input);
            System.out.println(line);
        }

        scanner.close();
    }
}