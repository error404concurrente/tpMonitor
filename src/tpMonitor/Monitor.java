package tpMonitor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public final class Monitor {
	//TODO reemplazar Thread por lo que sea que usemos
	private RedDePetri rdp;
	private Queue<Hilo> entrada;
	private Queue<Hilo> espera;
	
	private int size;
	
//	private Semaphore espera;
//	private Semaphore entrada;
	private Semaphore mutex;
	
	public Monitor(int max, RedDePetri red) {
		rdp = red;
		entrada = new ConcurrentLinkedDeque<Hilo>();
		espera = new ConcurrentLinkedDeque<Hilo>();
		size = max;
//		espera = new Semaphore(size);
//		entrada = new Semaphore(size);
		mutex = new Semaphore(1,true);	
	}
	
	public void enter(Hilo hilo) throws InterruptedException{
		// Obtencion de los semaforos
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			Log.spit("ERROR DE ENTRADA DE MONITOR AIUDA");
			e.printStackTrace();
		}
		execute(hilo);
	}
	
	private void execute(Hilo hilo) throws InterruptedException {
		if (rdp.disparar(hilo)) {
			despertarEspera(hilo);
		} else {
			Log.spit("NO ES COMPATIBLE-PA la espera");
			encolar(espera, hilo);
			mutex.release();
		}
	}
	
	private void despertarEspera(Hilo hilo) throws InterruptedException {
		execute(hilo);
	}
	
	private void encolar(Queue <Hilo> cola, Hilo hilo) throws InterruptedException {
		cola.add(hilo);
		hilo.wait();
	}
	
	private void desEncolar(Queue <Hilo> cola){
		cola.poll().notify();
	}
}
