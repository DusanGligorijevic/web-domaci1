package domaci1;

import java.util.Random;

public class Student implements Runnable{
    private static int redniBroj=0;
    private boolean branio;
    private int ocena;
    private Random r = new Random();
    @Override
    public void run() {
        System.out.println("Student "+ ++redniBroj + " je došao.");
        boolean braniKod = r.nextBoolean(); // true-prof, false-asistent
        if(!braniKod){
            System.out.println("Student "+ redniBroj + " brani kod asistenta.");


            /*
            try {
                Thread.sleep(r.nextInt(500)+500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
*/
        }else{
            System.out.println("Student "+ redniBroj + " brani kod profesora.");
        }



    }
}
