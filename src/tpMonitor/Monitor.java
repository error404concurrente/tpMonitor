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
	
	private Politicas politica;
	
	public Monitor(int max, RedDePetri red, Politicas politica) {
		rdp = red;
//		entrada = new ConcurrentLinkedDeque<Hilo>();
		espera = new ConcurrentLinkedDeque<Hilo>();
		size = max;
//		espera = new Semaphore(size);
		entrada = new Semaphore(1,true);
		mutex = new Semaphore(1,true);
		
		this.politica = politica;
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
				if( !hilito.getPolitico() ) {
					Log.spit("NO HAY POLITICA, DESPIERTO AL hilito" + hilito.getID());
					desEncolar(hilito);
					encontrado = true;
					break;
					}
				else {
					Log.spit("HAY POLITICA, DECIDO POR hilito" + hilito.getID());
					//politica.decidir( hilito, espera );
					if (politica.decidirYo(hilito)) {
						Log.spit("LE TOCA AL hilito" + hilito.getID());
						desEncolar(hilito);
						encontrado = true;
						break;
					}
					else if(politica.decidirRival(hilito, espera)){
						for(Hilo hilito2: espera) {
							if (hilito.getIDR() == hilito2.getID()) {
								Log.spit("LE TOCA AL hilito" + hilito.getID());
								desEncolar(hilito2);
								encontrado = true;
								break;
							}
						}
						break;
						}
					else {
						Log.spit("LE TOCA AL OTRO HILO PERO NO ESTA");
					}
				}
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
		Log.spit("ME VOY A MIMIR " + Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
		cola.add(hilo);
		synchronized(hilo){
		hilo.wait();
		}
	}
	
	private void desEncolar(Hilo hilito){
		Log.spit("HAY UN HILO PARA DESPERTAR");
		espera.remove(hilito);
		synchronized(hilito) {
			hilito.notify();
		}
	}
}
