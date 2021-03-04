package domaci1;

import java.util.concurrent.CyclicBarrier;

public class Profesor implements Runnable{

    CyclicBarrier cyclicBarrier;

    public Profesor(){
    }
    @Override
    public void run() {
        System.out.println("Profesor je spreman.");
    }
    public void ispitajStudenta(Student s){

    }
}
