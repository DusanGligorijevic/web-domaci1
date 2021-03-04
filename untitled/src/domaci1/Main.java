package domaci1;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main extends Thread{
    public static int sum=0;
    public static int finished=0;
    public static int numberOfStudents;
    public static Object LOCK = "LOCK";
    private static String input;

    public static void main(String[] args) throws InterruptedException {

        //Unosimo broj studenata
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite broj studenata: ");
        input = scanner.nextLine();
        numberOfStudents = Integer.parseInt(input);
        System.out.println(numberOfStudents + " studenata brani ispit.");
        scanner.close();



        Thread profesor = new Thread(new Profesor());
        profesor.start();

        Thread asistent = new Thread(new Asistent());
        asistent.start();
        Random r = new Random();

        System.out.println("odbrana pocinje!");
        ScheduledExecutorService executorServiceScheduled = Executors.newScheduledThreadPool(Main.numberOfStudents);
        for (int i = 0; i < Main.numberOfStudents; i++) {
            int arrivalTime = r.nextInt(1000);
            executorServiceScheduled.schedule(new Student(), arrivalTime, TimeUnit.MILLISECONDS);
        }
        Thread.currentThread().sleep(5000);
        executorServiceScheduled.shutdownNow();
         System.out.println("Odbrana se završila.");

    }
}
