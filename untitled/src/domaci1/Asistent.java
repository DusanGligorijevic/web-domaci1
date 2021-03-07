package domaci1;

import java.util.Random;

public class Asistent implements Runnable{
    boolean flag=true;
    Random r = new Random();
    @Override
    public void run() {
           // System.out.println("Asistent je spreman.");
        while(flag){
            flag=false;
            for(Student s: Main.listacekanja){
                if(!s.braniKod && !s.branio){
                    synchronized (Main.LOCK){
                        try {
                            Thread.currentThread().sleep(r.nextInt(500)+500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        s.ocena = r.nextInt(5)+1;
                        Main.finished++;
                        Main.ukupnaOcena+=s.ocena;
                        System.out.println(s.name + " je zavrsio i dobio ocenu " + s.ocena+ " kod asistenta.");
                        s.branio=true;
                        flag=true;
                    }
                }

            }
        }
    }
}
