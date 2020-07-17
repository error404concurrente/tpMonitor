package tpMonitor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public final class Monitor {
	//TODO reemplazar Thread por lo que sea que usemos
	private RedDePetri rdp;
	private Cola colaEspera;
	private int size;
	

	private Semaphore entrada;
	private Semaphore mutex;

	
	public Monitor(int max, RedDePetri red) {
		rdp = red;
		size = max;
		entrada = new Semaphore(1,true);
		mutex = new Semaphore(1,true);
		
		colaEspera = new Cola(rdp,entrada);
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
		
		if ( rdp.verificarCompatibilidad(hilo.getTarea(),hilo) ) {
			rdp.disparar(hilo);
			Log.spit("ES COMPATIBLE");
			mutex.release();
			colaEspera.buscarEspera();
		} else {
			Log.spit("NO ES COMPATIBLE-PA la espera");
			mutex.release();
			entrada.release();
			//colaEspera.espera(); //En caso que pregunten antes de irse a dormir
			colaEspera.encolar(hilo);
			Log.spit("ME VOY A EJECUTAR " + Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
			mutex.acquire();
			execute(hilo);
			
		}
	}
	
}
