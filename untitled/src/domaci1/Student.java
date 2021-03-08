package domaci1;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Student extends Thread implements Runnable{
    public String name;
    protected boolean branio = false;
    public int ocena;
    private Random r = new Random();
    public boolean braniKod; // ukoliko je true brani kod profesora, ukoliko je false brani kod asistenta
    long startTime;
    long finishedTime;

    public Student(String name, boolean branio, int ocena, boolean braniKod, long startTime) {
        this.name = name;
        this.branio = branio;
        this.ocena = ocena;
        this.braniKod = braniKod;
        this.startTime=startTime;
    }
    public Student() {
    }
    @Override
    public void run() {
        braniKod = r.nextBoolean(); // true-prof, false-asistent
        name=Thread.currentThread().getName();
        Student student = new Student(name, false, 0, braniKod,System.currentTimeMillis());
        System.out.println(name + " " + " je došao.");
        Main.listacekanja.add(student);

        //odredjujemo da li student brani kod profesora ili asistenta
        if (!braniKod) {
            System.out.println(name + " brani kod asistenta.");
        } else {
            try {
                System.out.println(name + " brani kod profesora.");
                System.out.println(name +  " ceka.");
                /**
                 * Ovde se nalazi barijera zbog toga sto  kod u klasi profesor nije u run metodi.
                 * Takodje, iz istog razloga se ovde nalazi i deo gde student brani rad svojim tempom (Thread.sleep)
                 */
                Profesor.cyclicBarrier.await();
                int vremeOdbrane = r.nextInt(501)+500;
                Main.profesor.sleep(vremeOdbrane);
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("InterruptedException: Student koji je cekao kod profesora takodje nije zavrsio.");
            }
        }
    }
}
