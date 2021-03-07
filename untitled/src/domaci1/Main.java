package domaci1;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main extends Thread{
    public static int finished=0;
    public static int numberOfStudents;
    public static Object LOCK = "LOCK";
    private static String input;
    public static Thread profesor;
    public static Thread asistent;
    public static int ukupnaOcena=0;
    public static final ArrayList<Student> listacekanja = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        //Unosimo broj studenata
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite broj studenata: ");
        input = scanner.nextLine();
        numberOfStudents = Integer.parseInt(input);
        System.out.println(numberOfStudents + " studenata brani ispit.");
        scanner.close();


        profesor = new Thread(new Profesor());


        asistent = new Thread(new Asistent());

        Random r = new Random();

        System.out.println("odbrana pocinje!");
        ScheduledExecutorService executorServiceScheduled = Executors.newScheduledThreadPool(Main.numberOfStudents);
        for (int i = 0; i < Main.numberOfStudents; i++) {
            int arrivalTime = r.nextInt(5000);
            executorServiceScheduled.schedule(new Student(), arrivalTime, TimeUnit.MILLISECONDS);
        }
        Thread.currentThread().sleep(5000);
        asistent.start();
        profesor.start();
        executorServiceScheduled.shutdownNow();
        asistent.join();
        profesor.join();
        System.out.println("Odbrana se završila.");
        System.out.println(ukupnaOcena);
        System.out.println(finished);
        System.out.println("Prosek ocena na odbrani je: "+ ukupnaOcena/finished*1.0);

    }
}
