public class Aguarda {
    static volatile int contador;
    public static void main(String[] args) {
        Runnable r =() ->{
            Thread hiloActual = Thread.currentThread();
            System.out.printf("%s ha entrado en Ejecutable y esta esperando%n",hiloActual.getName());
            synchronized(Aguarda.class){
                contador++;
                try {
                    Thread.sleep(2000);
                    while(contador < 3){
                        Aguarda.class.wait();
                    }
                } catch (InterruptedException ie) {

                }
            }
            System.out.printf("%s ha despertado y esta terminado%n", hiloActual.getName());
        };
        Thread HiloA = new Thread(r, "Hilo A");
        Thread HiloB = new Thread(r, "Hilo B");
        Thread HiloC = new Thread(r, "Hilo C");
        HiloA.start();
        HiloB.start();
        HiloC.start();
        r = new Runnable(){
            @Override
            public void run() {
                try {
                    while(contador < 3){
                        Thread.sleep(100);
                    }
                    synchronized(Aguarda.class){
                        Aguarda.class.notifyAll();
                    }
                } catch (Exception e) {
                    
                }
            }
        };
        Thread h = new Thread(r);
        h.start();
    }
}
