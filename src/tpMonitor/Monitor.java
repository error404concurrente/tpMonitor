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
			//vacio.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			System.out.println("ERROR DE ENTRADA DE MONITOR AIUDA");
			e.printStackTrace();
		}
		
		//System.out.println("Hilo entrante: "+Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
		
		if( rdp.verificarCompatibilidad(hilo.getTarea()) ) {
			System.out.println("Compatibilidad Confirmada");
			System.out.println("Hilo entrante: "+Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
			System.out.println("-------------- Resultados --------------");
			System.out.println("Estado de RdP Antes:   "+rdp.strMarcaActual()+"  ----  T. Sensibles Antes:   "+rdp.strTranSensible());
			rdp.calcularMarcaActual(hilo.getTarea());
			rdp.calcularVectorSensible();
			System.out.println("Estado de RdP Despues: "+rdp.strMarcaActual()+"  ----  T. Sensibles Despues: "+rdp.strTranSensible());
			System.out.println("-------------- Fin Resultados --------------");
			System.out.println("Hilo exit-ante: "+Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
		}else {
			System.out.println("NO ES COMPATIBLE-PA JUERA");
		}

		//Liberar semaforos
		//vacio.release();
		mutex.release();
		
		return true;
	}
}
