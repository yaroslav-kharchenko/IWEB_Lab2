import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiplication {
    public static void main(String[] args) {
        // Зчитуємо розмірність квадратної матриці з консолі
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть розмірність квадратної матриці: ");
        int size = scanner.nextInt();

        // Зчитуємо кількість ведених потоків
        System.out.print("Введіть кількість ведених потоків: ");
        int N = scanner.nextInt();

        // Створюємо матриці
        int[][] matrixA = generateRandomMatrix(size);
        int[][] matrixB = generateRandomMatrix(size);
        int[][] resultMatrix = new int[size][size];

        // Створюємо пул потоків
        ExecutorService executor = Executors.newFixedThreadPool(N);

        // Розбиваємо роботу на частини для ведених потоків
        int rowsPerThread = size / N;
        int remainingRows = size % N;
        int startIndex = 0;

        long startTime = System.currentTimeMillis();

        // Запускаємо ведені потоки
        for (int i = 0; i < N; i++) {
            int endIndex = startIndex + rowsPerThread;
            if (i == N - 1) {
                endIndex += remainingRows;
            }
            Runnable worker = new Worker(matrixA, matrixB, resultMatrix, startIndex, endIndex);
            executor.execute(worker);
            System.out.println("Створено потік " + (i + 1) + ": обробка рядків " + startIndex + " - " + endIndex);
            startIndex = endIndex;
        }

        // Завершуємо роботу пулу потоків
        executor.shutdown();

        try {
            // Чекаємо завершення всіх потоків
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Виводимо результат
        System.out.println("Matrix A:");
        printMatrix(matrixA);
        System.out.println("Matrix B:");
        printMatrix(matrixB);
        System.out.println("Result Matrix:");
        printMatrix(resultMatrix);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Час множення матриць " + totalTime + " мс");
    }

    // Генеруємо випадкову матрицю
    private static int[][] generateRandomMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = (int) (Math.random() * 10);
            }
        }
        return matrix;
    }

    // Виводимо матрицю на екран
    private static void printMatrix(int[][] matrix) {
        int columns = matrix[0].length;
        for (int[] ints : matrix) {
            for (int j = 0; j < columns; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

class Worker implements Runnable {
    private final int[][] matrixA;
    private final int[][] matrixB;
    private final int[][] resultMatrix;
    private final int startIndex;
    private final int endIndex;

    public Worker(int[][] matrixA, int[][] matrixB, int[][] resultMatrix, int startIndex, int endIndex) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        int size = matrixA.length;
        for (int i = startIndex; i < endIndex; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }
}
