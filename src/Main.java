import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public static void performSemaphoreOperation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість потоків (n): ");
        int n = scanner.nextInt();
        System.out.print("Введіть інтервал часу t1: ");
        int t1 = scanner.nextInt();
        System.out.print("Введіть інтервал часу t2 (t2 > t1): ");
        int t2 = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            String threadName = "Thread-" + (i + 1);
            Thread thread = new Thread(new SemaphoreThread(threadName, t1, t2));
            thread.start();
        }
    }

    private static AtomicInteger globalVariable = new AtomicInteger(0);

    public static void performAtomicOperation() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Підпункти меню 'Atomic':");
            System.out.println("1 - Збільшити глобальну змінну");
            System.out.println("2 - Зменшити глобальну змінну");
            System.out.println("3 - Змінити глобальну змінну");
            System.out.println("0 - Повернутися до попереднього меню");

            System.out.print("Введіть номер підпункту: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> incrementGlobalVariable();
                case 2 -> decrementGlobalVariable();
                case 3 -> {
                    System.out.print("Введіть нове значення глобальної змінної: ");
                    int newValue = scanner.nextInt();
                    setGlobalVariable(newValue);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    public static void incrementGlobalVariable() {
        int oldValue = globalVariable.get();
        int newValue = oldValue + 1;
        boolean success = globalVariable.compareAndSet(oldValue, newValue);

        if (success) {
            System.out.println("Глобальна змінна збільшена. Поточне значення: " + newValue);
        } else {
            System.out.println("Не вдалося збільшити глобальну змінну. Поточне значення: " + globalVariable.get());
        }
    }

    public static void decrementGlobalVariable() {
        int oldValue = globalVariable.get();
        int newValue = oldValue - 1;
        boolean success = globalVariable.compareAndSet(oldValue, newValue);

        if (success) {
            System.out.println("Глобальна змінна зменшена. Поточне значення: " + newValue);
        } else {
            System.out.println("Не вдалося зменшити глобальну змінну. Поточне значення: " + globalVariable.get());
        }
    }

    public static void setGlobalVariable(int newValue) {
        globalVariable.set(newValue);
        System.out.println("Глобальна змінна змінена. Нове значення: " + newValue);
    }

    public static void performThreadOperation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Виберіть тип потоків (1 - Pull-Thread, 2 - Cash-Thread): ");
        int threadType = scanner.nextInt();

        System.out.print("Введіть кількість потоків в пулі/кеші (k): ");
        int k = scanner.nextInt();

        System.out.print("Введіть кількість потоків, що виконуються (n): ");
        int n = scanner.nextInt();

        ExecutorService executorService;

        if (threadType == 1) {
            executorService = Executors.newFixedThreadPool(k);
        } else if (threadType == 2) {
            executorService = Executors.newCachedThreadPool();
        } else {
            System.out.println("Невірний вибір типу потоків. Повернення до попереднього меню.");
            return;
        }

        for (int i = 0; i < n; i++) {
            executorService.execute(new ThreadRunnable());
        }

        executorService.shutdown();
    }

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
