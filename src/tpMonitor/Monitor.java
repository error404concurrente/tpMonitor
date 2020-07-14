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
	
	public void enter(Hilo hilo) throws InterruptedException{
		//TODO discutir si lo dejamos boolean
		//TODO Controlar que lo que entre no esta ya adentro

		// Obtencion de los semaforos
		try {
			vacio.acquire();
			mutex.acquire();
			// hilo.wait();
		} catch (InterruptedException e) {
			Log.spit("ERROR DE ENTRADA DE MONITOR AIUDA");
			e.printStackTrace();
		}

		// Log.spit("Hilo entrante: "+Thread.currentThread().getName()+" Disparo:
		// "+hilo.strTarea());

		if (rdp.disparar(hilo)) {
			Log.spit("ENTREEEEEEEEEEEEEEE");
		} else {
			Log.spit("NO ES COMPATIBLE-PA JUERA");
		}


		//Liberar semaforos
		vacio.release();
		mutex.release();
		vacio.release();
		Thread.sleep(2000);
		// hilo.notifyAll();
	}
}
