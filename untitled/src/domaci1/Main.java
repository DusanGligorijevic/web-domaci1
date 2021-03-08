package domaci1;

import javafx.util.converter.LongStringConverter;

import java.io.Console;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Thread{
    public static int finished=0;
    public static int numberOfStudents;
    public static Object LOCK = "LOCK";
    private static String input;
    public static Thread profesor;
    public static Thread asistent;
    public static int ukupnaOcena=0;
    static long   pocetakOdbrane;
    public static final ArrayList<Student> listacekanja = new ArrayList<>(); //predstavlja listu studenata

    public static void main(String[] args) throws InterruptedException {

        //Unosimo broj studenata
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite broj studenata: ");
        input = scanner.nextLine();
        numberOfStudents = Integer.parseInt(input);
        System.out.println(numberOfStudents + " studenata brani ispit.");
        scanner.close();

        /***
         *Kreiramo 2 pool-a, jedan za profesorsku i asistentsku nit, a jedan za studente
         * Startujemo ih i počinjemo sa odbranom
         */
        ExecutorService executorServiceFixed = Executors.newFixedThreadPool(2);
        profesor = new Thread(new Profesor());
        asistent = new Thread(new Asistent());

        System.out.println("Asistent je spreman.");
        System.out.println("Profesor je spreman.");
        System.out.println("odbrana pocinje!");
        Random r = new Random();
        pocetakOdbrane =System.currentTimeMillis();
        ScheduledExecutorService executorServiceScheduled = Executors.newScheduledThreadPool(Main.numberOfStudents);
        for (int i = 0; i < Main.numberOfStudents; i++) {
            int arrivalTime = r.nextInt(1000) + 1;
            executorServiceScheduled.schedule(new Student(), arrivalTime, TimeUnit.MILLISECONDS);
        }
        /**
         * Main klasa predstavlja tajmer
         * nakon 5 sekundi gasimo oba pool-a,
         * zatim prikazujemo rezultate odbrane
         */

        Thread.currentThread().sleep(1000);
        executorServiceFixed.execute(asistent);
        executorServiceFixed.execute(profesor);
        Thread.currentThread().sleep(4000);

        executorServiceFixed.shutdownNow();
        executorServiceFixed.shutdown();
        executorServiceScheduled.shutdownNow();
        executorServiceScheduled.shutdown();

        System.out.println("Odbrana se završila.");
        System.out.println(ukupnaOcena);
        System.out.println(finished);
        System.out.println("Prosek ocena na odbrani je: " + ukupnaOcena * 1.0 / finished);

        for(Student s : listacekanja){
            if(s.braniKod && s.branio){
                long homeworkTime= s.finishedTime-s.startTime;
                long time = s.startTime-pocetakOdbrane;
                System.out.println("Thread:<"+s.name+">"+
                        ", Arrival:<"+time+">"+
                        ", Prof:<"+profesor.getName()+">"+
                        ", TTC:<"+homeworkTime+">:<"+pocetakOdbrane+">"+
                        ", Score:<"+ s.ocena+">");
            }else if (!s.braniKod && s.branio){
                long time = s.startTime-pocetakOdbrane;
                long homeworkTime= s.finishedTime-s.startTime;
                System.out.println("Thread:<"+s.name+">"+
                        ", Arrival:<"+ time +">"+
                        ", Prof:<"+asistent.getName()+">"+
                        ", TTC:<"+homeworkTime+">:< "+ pocetakOdbrane +">"+
                        ", Score:<"+ s.ocena+">");

            }
        }

        for(Student s : listacekanja){
            if(!s.branio){
                System.out.println(s.getName()+" nije stigao da odbrani.");
            }
        }
        System.exit(0);
    }
}
