import java.util.Scanner;

public class Exia {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(line);
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

            if (input.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                System.out.println(line);
                continue;
            }

            if (input.startsWith("done ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                tasks[taskNumber - 1].markAsDone();

                System.out.println(line);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[taskNumber - 1]);
                System.out.println(line);
                continue;
            }

            tasks[taskCount] = new Task(input);
            taskCount++;

            System.out.println(line);
            System.out.println("added: " + input);
            System.out.println(line);
        }

        scanner.close();
    }
}