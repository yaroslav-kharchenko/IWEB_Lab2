import java.util.Scanner;

public class Main {

    public static void performMutexOperation() {}

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
