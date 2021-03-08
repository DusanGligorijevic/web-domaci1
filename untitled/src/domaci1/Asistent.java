package domaci1;

import java.util.Random;

public class Asistent implements Runnable{
    boolean flag=true;
    Random r = new Random();
    @Override
    public void run() {
        /**
         * Sve dok postoji student u listi cekanja koji brani rad kod asistenta,
         * cim zavrsi ispitivanje jednog studenta, asistent prelazi na ispitivanje drugog.
         */
        while(flag){
            flag=false;
            for(Student s: Main.listacekanja){
                if(!s.braniKod && !s.branio){
                    synchronized (Main.LOCK){
                        try {
                            Thread.currentThread().sleep(r.nextInt(500)+500);
                        } catch (InterruptedException e) {
                            System.out.println("InterruptedException: Studenti koji su cekali kod asistenta nisu stigli " +
                                    "da odbrane.");
                        }
                        s.ocena = r.nextInt(10)+1;
                        Main.finished++;
                        Main.ukupnaOcena+=s.ocena;
                        System.out.println(s.name + " je zavrsio i dobio ocenu " + s.ocena+ " kod asistenta.");
                        s.finishedTime=System.currentTimeMillis();
                        s.branio=true;
                        flag=true;
                    }
                }

            }
        }
    }
}
