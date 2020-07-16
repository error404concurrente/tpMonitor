package tpMonitor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public final class Monitor {
	private RedDePetri rdp;
	private Queue<Hilo> espera;
	
	private int size;
	
	private Semaphore entrada;
	private Semaphore mutex;
	
	public Monitor(int max, RedDePetri red) {
		rdp = red;
		espera = new ConcurrentLinkedDeque<Hilo>();
		size = max;
		entrada = new Semaphore(1,true);
		mutex = new Semaphore(1,true);	
	}
	
	public void enter(Hilo hilo) throws InterruptedException{
		// Obtencion de los semaforos
		Log.spit("Monitor: Entra el hilo " + Thread.currentThread().getName()+" a Cola de Entrada");
		entrada.acquire();
		Log.spit("Monitor: Sale el hilo " + Thread.currentThread().getName()+" de Cola de Entrada");
		mutex.acquire();
		Log.spit("Monitor: El hilo " + Thread.currentThread().getName()+" tiene exclusion mutua");
		execute(hilo);
		Log.spit("Monitor: El hilo "+Thread.currentThread().getName()+" ha finalizado y se retira del monitor ");
	}
	
	private void execute(Hilo hilo) throws InterruptedException {
		if (rdp.disparar(hilo)) {
			//Se disparo correctamente la transicion
			Log.spit("Monitor: Confirmacion de Disparo recibida - Se procede a revisar los hilos en la cola de espera");
			mutex.release();
			espera();
		} else {
			//No se disparo la transicion
			Log.spit("Monitor: Denegacion de Disparo confirmada - Se procede a mandar "+Thread.currentThread().getName()+" a la cola de espera");
			
			mutex.release();
			entrada.release();
			// espera(); //En caso que pregunten antes de irse a dormir
			encolar(espera, hilo);
			Log.spit("Monitor: Sale el hilo " + Thread.currentThread().getName()+" de Cola de Espera y esta listo para disparar");
			mutex.acquire();
			execute(hilo);

		}
	}

	private void espera() throws InterruptedException {
		Log.spit("************** Verificacion Cola de Espera **************");
		boolean encontrado = false;
		for (Hilo hilito : espera) {
			if (rdp.verificarCompatibilidad(hilito.getTarea())) {
				// ver politica
				Log.spit("Se ha encontrado un hilo que puede ejecutar su disparo");
				espera.remove(hilito);
				Log.spit("************ Fin Verificacion Cola de Espera ************");
				synchronized (hilito) {
					hilito.notify();
				}
				encontrado = true;
				break;
			}
		}
		if (!encontrado) {
			Log.spit("No hay hilos en cola de espera que puedan ejecutar un disparo actualmente");
			Log.spit("************ Fin Verificacion Cola de Espera ************");
			entrada.release();
		}
	}
	
//	private void despertarEspera(Hilo hilo) throws InterruptedException {
//		execute(hilo);
//	}
	
	private void encolar(Queue <Hilo> cola, Hilo hilo) throws InterruptedException {
		Log.spit("Monitor: Entra el hilo " + Thread.currentThread().getName()+" a la Cola de Espera");
		cola.add(hilo);
		synchronized(hilo){
		hilo.wait();
		}
	}
	
//	private void desEncolar(Queue <Hilo> cola){
//		cola.poll().notify();
//	}
}
