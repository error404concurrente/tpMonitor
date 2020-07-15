package tpMonitor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public final class Monitor {
	//TODO reemplazar Thread por lo que sea que usemos
	private RedDePetri rdp;
//	private Queue<Hilo> entrada;
	private Queue<Hilo> espera;
	
	private int size;
	
//	private Semaphore espera;
	private Semaphore entrada;
	private Semaphore mutex;
	
	public Monitor(int max, RedDePetri red) {
		rdp = red;
//		entrada = new ConcurrentLinkedDeque<Hilo>();
		espera = new ConcurrentLinkedDeque<Hilo>();
		size = max;
//		espera = new Semaphore(size);
		entrada = new Semaphore(1,true);
		mutex = new Semaphore(1,true);	
	}
	
	public void enter(Hilo hilo) throws InterruptedException{
		// Obtencion de los semaforos
		try {
			entrada.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			Log.spit("ERROR DE ENTRADA DE MONITOR AIUDA");
			e.printStackTrace();
		}
		execute(hilo);
		Log.spit("TERMINE ME VOY " + Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
	}
	
	private void execute(Hilo hilo) throws InterruptedException {
		if (rdp.disparar(hilo)) {
			Log.spit("ES COMPATIBLE");
			mutex.release();
			espera();
		} else {
			Log.spit("NO ES COMPATIBLE-PA la espera");
			mutex.release();
			entrada.release();
			//espera(); //En caso que pregunten antes de irse a dormir
			encolar(espera, hilo);
			Log.spit("ME VOY A EJECUTAR " + Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
			mutex.acquire();
			execute(hilo);
			
		}
	}
	
	private void espera() throws InterruptedException {
		boolean encontrado = false;
		for(Hilo hilito: espera) {
			if( rdp.verificarCompatibilidad(hilito.getTarea())) {
				//ver politica
				Log.spit("HAY UN HILO PARA DESPERTAR");
				espera.remove(hilito);
				synchronized(hilito) {
				hilito.notify();
				}
				encontrado = true;
				break;
			}
		}
		if(!encontrado) {
		Log.spit("NO HAY HILITOS PARA DESPERTAR");
		entrada.release();
		}
	}
	
//	private void despertarEspera(Hilo hilo) throws InterruptedException {
//		execute(hilo);
//	}
	
	private void encolar(Queue <Hilo> cola, Hilo hilo) throws InterruptedException {
		Log.spit("ME VOY A MIMIR" + Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
		cola.add(hilo);
		synchronized(hilo){
		hilo.wait();
		}
	}
	
//	private void desEncolar(Queue <Hilo> cola){
//		cola.poll().notify();
//	}
}
