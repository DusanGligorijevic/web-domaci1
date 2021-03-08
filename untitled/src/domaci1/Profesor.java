package domaci1;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class Profesor implements Runnable{

   static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        // ovaj Runnable predstavlja zadatak koji ce barijera obaviti kada pukne
        @Override
        public void run() {
            // deo kada student brani rad svojim tempom ( thread.sleep) se nalzi u klasi asistent
            System.out.println("Barijera je pukla, studenti su krenuli sa odbranom.");
                for(Student s: Main.listacekanja) {
                    if (s.braniKod && !s.branio) {
                        synchronized (Main.LOCK) {
                            Random r = new Random();
                            s.ocena = r.nextInt(10) + 1;
                            Main.finished++;
                            Main.ukupnaOcena += s.ocena;
                            System.out.println(s.name + " je zavrsio i dobio ocenu " + s.ocena + " kod profe.");
                            s.finishedTime=System.currentTimeMillis();
                            s.branio = true;
                            break;
                        }
                    }

                }
        }
    });
    @Override
    public void run() {
    }
}
