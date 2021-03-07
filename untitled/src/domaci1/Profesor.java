package domaci1;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Profesor implements Runnable{

   static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        // ovaj Runnable predstavlja zadatak koji ce barijera obaviti kada pukne
        @Override
        public void run() {
            System.out.println("Barijera je pukla, studenti su krenuli sa odbranom.");
                for(Student s: Main.listacekanja) {
                    if (s.braniKod && !s.branio) {
                        synchronized (Main.LOCK) {
                            Random r = new Random();
                            // ne dozvoljava zato sto nije u run metodi
                         //   try {
                          //     Thread.currentThread().sleep(r.nextInt(500) + 500);
                          //  } catch (InterruptedException e) {
                           //     e.printStackTrace();
                          //  }
                            s.ocena = r.nextInt(5) + 1;
                            Main.finished++;
                            Main.ukupnaOcena += s.ocena;
                            System.out.println(s.name + " je zavrsio i dobio ocenu " + s.ocena + " kod profe.");
                            s.branio = true;
                            break;
                        }

                    }

                }

        }
    });


    @Override
    public void run() {
       // System.out.println("Profesor je spreman.");
    }

}
