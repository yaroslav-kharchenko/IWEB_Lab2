import java.util.Scanner;

public class Main {

    public static void performMutexOperation() {
        System.out.println("Ви обрали операцію 'М'ютекс'.");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість потоків (n): ");
        int n = scanner.nextInt();
        System.out.print("Введіть інтервал часу t1: ");
        int t1 = scanner.nextInt();
        System.out.print("Введіть інтервал часу t2 (t2 > t1): ");
        int t2 = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            String threadName = "Thread-" + (i + 1);
            Thread thread = new Thread(new MutexThread(threadName, t1, t2));
            thread.start();
        }
    }

    public static void performSemaphoreOperation() {}

    public static void performAtomicOperation() {}

    public static void performThreadOperation() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню:");
            System.out.println("1 - 'М'ютекс'");
            System.out.println("2 - 'Семафор'");
            System.out.println("3 - 'Atomic'");
            System.out.println("4 - 'Pull-Thread'/'Cash-Thread'");
            System.out.println("0 - Вихід");

            System.out.print("Введіть номер операції: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> performMutexOperation();
                case 2 -> performSemaphoreOperation();
                case 3 -> performAtomicOperation();
                case 4 -> performThreadOperation();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }
}
