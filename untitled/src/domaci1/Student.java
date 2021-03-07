package domaci1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Student extends Thread implements Runnable {
    private static int redniBroj = 0;
    public String name = "Student";
    protected boolean branio = false;
    public static int ocena;
    private Random r = new Random();
    public boolean braniKod;


    public Student(String name, boolean branio, int ocena, boolean braniKod) {
        this.name = name;
        this.branio = branio;
        this.ocena = ocena;
        this.braniKod = braniKod;

    }

    public Student() {

    }

    @Override
    public void run() {
        braniKod = r.nextBoolean(); // true-prof, false-asistent
            Student student = new Student(name + " " + ++redniBroj, false, 0, braniKod);
            System.out.println(name + " " + redniBroj + " je došao.");
            Main.listacekanja.add(student);
        if (!braniKod) {
            System.out.println(name + " " + redniBroj + " brani kod asistenta.");
        } else {
            try {
                System.out.println(name + " " + redniBroj + " brani kod profesora.");
                System.out.println(name + " "+ redniBroj + " ceka.");
                Profesor.cyclicBarrier.await();
                Main.profesor.sleep(1000);
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }


    }
}
