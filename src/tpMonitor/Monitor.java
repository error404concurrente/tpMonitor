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
//			Log.spit("Hilo entrante: "+Thread.currentThread().getName());
			entrada.acquire();
			
			mutex.acquire();
		} catch (InterruptedException e) {
			Log.spit("ERROR DE ENTRADA DE MONITOR AIUDA");
			e.printStackTrace();
		}
		execute(hilo);
//		Log.spit("TERMINE ME VOY " + Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
	}

	private void execute(Hilo hilo) throws InterruptedException {
		if ( rdp.verificarCompatibilidad(hilo.getTarea(),hilo) ) {
			if(rdp.checkTemporal(hilo)) { //Es compatible y esta dentro de la ventana temporal
				rdp.disparar(hilo);
				mutex.release();
				colaEspera.buscarEspera();
			}else { //Es compatible pero no esta en la ventana temporal
//				Log.spit("Me voy alv "+Thread.currentThread().getName());
				mutex.release();
				entrada.release();
				Thread.sleep(rdp.mimirTime(hilo));
				enter(hilo);
			}
		} else { //No es compatible
//			Log.spit("A mimir");
			mutex.release();
			entrada.release();
			colaEspera.encolar(hilo);
//			Log.spit("ME VOY A EJECUTAR " + Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
			mutex.acquire();
			execute(hilo);

		}
	}
}