package tpMonitor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public final class Monitor {
	private RedDePetri rdp;
<<<<<<< HEAD
	private Queue<Hilo> espera;
	
	private int size;
	
=======
	private Cola colaEspera;
	private int size;
	

>>>>>>> 17541e27aa5a0455b0b9875729b83f2e52515390
	private Semaphore entrada;
	private Semaphore mutex;

	
	public Monitor(int max, RedDePetri red) {
		rdp = red;
<<<<<<< HEAD
		espera = new ConcurrentLinkedDeque<Hilo>();
=======
>>>>>>> 17541e27aa5a0455b0b9875729b83f2e52515390
		size = max;
		entrada = new Semaphore(1,true);
		mutex = new Semaphore(1,true);
		
		colaEspera = new Cola(rdp,entrada);
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
<<<<<<< HEAD
		if (rdp.disparar(hilo)) {
			//Se disparo correctamente la transicion
			Log.spit("Monitor: Confirmacion de Disparo recibida - Se procede a revisar los hilos en la cola de espera");
=======
		
		if ( rdp.verificarCompatibilidad(hilo.getTarea(),hilo) ) {
			rdp.disparar(hilo);
			Log.spit("ES COMPATIBLE");
>>>>>>> 17541e27aa5a0455b0b9875729b83f2e52515390
			mutex.release();
			colaEspera.buscarEspera();
		} else {
			//No se disparo la transicion
			Log.spit("Monitor: Denegacion de Disparo confirmada - Se procede a mandar "+Thread.currentThread().getName()+" a la cola de espera");
			
			mutex.release();
			entrada.release();
<<<<<<< HEAD
			// espera(); //En caso que pregunten antes de irse a dormir
			encolar(espera, hilo);
			Log.spit("Monitor: Sale el hilo " + Thread.currentThread().getName()+" de Cola de Espera y esta listo para disparar");
=======
			//colaEspera.espera(); //En caso que pregunten antes de irse a dormir
			colaEspera.encolar(hilo);
			Log.spit("ME VOY A EJECUTAR " + Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
>>>>>>> 17541e27aa5a0455b0b9875729b83f2e52515390
			mutex.acquire();
			execute(hilo);

		}
	}
<<<<<<< HEAD

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
	
	private void encolar(Queue<Hilo> cola, Hilo hilo) throws InterruptedException {
		Log.spit("Monitor: Entra el hilo " + Thread.currentThread().getName() + " a la Cola de Espera");
		cola.add(hilo);
		synchronized (hilo) {
			hilo.wait();
		}
	}

=======
	
>>>>>>> 17541e27aa5a0455b0b9875729b83f2e52515390
}
