package tpMonitor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public class Monitor {
	//TODO reemplazar Thread por lo que sea que usemos
	private RedDePetri rdp;
	private Queue<Hilo> entrada;
	private Queue<Hilo> espera;
	
	private int size;
	
	private Semaphore lleno;
	private Semaphore vacio;
	private Semaphore mutex;
	
	public Monitor(int max, RedDePetri red) {
		rdp = red;
		
		//entrada = new ConcurrentLinkedDeque<Hilo>();
		espera = new ConcurrentLinkedDeque<Hilo>();
		
		size = max;
		
		lleno = new Semaphore(0);
		vacio = new Semaphore(size);
		mutex = new Semaphore(1);
	}
	
	public boolean enter(Hilo hilo) throws InterruptedException {
		//TODO discutir si lo dejamos boolean
		//TODO Controlar que lo que entre no esta ya adentro
		
		//Obtencion de los semaforos
		try {
			vacio.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			System.out.println("ERROR DE ENTRADA DE MONITOR AIUDA");
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName());
		
		if( rdp.verificarCompatibilidad(hilo.getTarea()) ) {
			System.out.println("ES COMPATIBLE");
			rdp.printActual();
			rdp.calcularMarcaActual(hilo.getTarea());
			rdp.printActual();
			System.out.println(" -------------- ");
			rdp.printSensible();
			rdp.calcularVectorSensible();
			rdp.printSensible();
		}else {
			System.out.println("NO ES COMPATIBLE-PA JUERA");
		}
		vacio.release();
		mutex.release();
		
		//hilo.wait(); //TODO Discutir si lo hacemos antes o despues de meterlo a la cola
		
		//Liberar semaforos
		return true;
	}
}
