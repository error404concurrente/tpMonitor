package tpMonitor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public class Monitor {
	//TODO reemplazar Thread por lo que sea que usemos
	private Queue<Thread> entrada;
	private Queue<Thread> espera;
	
	private int size;
	
	private Semaphore lleno;
	private Semaphore vacio;
	private Semaphore mutex;
	
	public Monitor(int max) {
		entrada = new ConcurrentLinkedDeque<Thread>();
		espera = new ConcurrentLinkedDeque<Thread>();
		
		size = max;
		
		lleno = new Semaphore(0);
		vacio = new Semaphore(size);
		mutex = new Semaphore(1);
	}
	
	public boolean enter(Thread hilo) throws InterruptedException {
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
		//Añadir a cola de entrada
		entrada.add(hilo);
		hilo.wait(); //TODO Discutir si lo hacemos antes o despues de meterlo a la cola
		
		//Liberar semaforos
		vacio.release();
		mutex.release();
		return true;
	}
}
