package tpMonitor;

import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public final class Politicas {

	private static TreeMap<Integer,Integer> contador;
	
	public Politicas(int cantidad) {
		contador = new TreeMap<Integer,Integer>(); 
		inicializar(cantidad);
	}
	
	public static Boolean decidirYo(Hilo hilito) {
		
		if (contador.get(hilito.getID()) <= contador.get(hilito.getIDR())) {
			Log.spit("Voy: "+contador.get(hilito.getID())+" y el otro va: "+ contador.get(hilito.getIDR()));
			return true;
		} 
		return false;
	}
	
	public static Boolean estaRival(Hilo hilito, Queue<Hilo> espera) {
		
		for (Hilo hilito2 : espera) {
			if (hilito.getIDR() == hilito2.getID()) {
				Log.spit("Voy: "+contador.get(hilito.getID())+" y el otro va: "+ contador.get(hilito.getIDR()));
				return true;
				} 
			}
		return false;
	}
	
	public static void aumentar(Hilo hilito) {
		//Log.spit("Voy: "+contador.get(hilito.getID())+" y el otro va: "+ contador.get(hilito.getIDR()));
		contador.replace(hilito.getID(), contador.get(hilito.getID()),
				contador.get(hilito.getID()) + 1);
		Log.spit("Ahora voy: "+contador.get(hilito.getID()));
	}
	
	private void inicializar(int cantidad) {
		for( int i = 0; i < cantidad; i++ ) {
			contador.put( i, 0 );
		}
	}
}
