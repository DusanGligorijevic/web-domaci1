package domaci1;

import java.util.ArrayList;
import java.util.Random;

public class Student extends Thread implements Runnable{
    private static int redniBroj=0;
    public String name = "Student";
    protected  boolean branio=false;
    public static int ocena;
    private Random r = new Random();
    public  boolean braniKod;



    public Student (String name, boolean branio, int ocena, boolean braniKod){
        this.name=name;
        this.branio = branio;
        this.ocena= ocena;
        this.braniKod=braniKod;
    }
    public Student(){

    }
    @Override
    public void run() {
        braniKod = r.nextBoolean(); // true-prof, false-asistent
        Student student = new Student(name + " " + ++redniBroj, false, 0, braniKod);
        System.out.println(name +" " + redniBroj + " je došao.");
        if(!braniKod){
            System.out.println(name +" " + redniBroj + " brani kod asistenta.");
        }else{
            System.out.println(name +" " + redniBroj + " brani kod profesora.");
        }
        Main.listacekanja.add(student);
    }
}
