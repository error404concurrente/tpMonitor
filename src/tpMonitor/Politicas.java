package tpMonitor;

import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public final class Politicas {

	private TreeMap<Integer,Integer> contador;
	
	public Politicas() {
		contador = new TreeMap<Integer,Integer>(); 
	}
	
//	public void decidir(Hilo hilito, Queue<Hilo> espera) {
//		
//		if (contador.get(hilito.getID()) <= contador.get(hilito.getIDR())) {
//			// Me toca a mi, me despierto y termino busqueda del hilo a despertar
//			contador.replace(hilito.getID(), contador.get(hilito.getID()),
//					contador.get(hilito.getID()) + 1);
//		} 
//		else {
//			for (Hilo hilito2 : espera) {
//				if (hilito.getIDR() == hilito2.getID()) {
//					//Esta el otro, lo despierto
//					contador.replace(hilito2.getID(), contador.get(hilito2.getID()),
//							contador.get(hilito2.getID()) + 1);
//					//Despues de despertarlo, tengo que dormir y terminar la busqueda del 
//					//hilo a despertar
//					break;
//					} 
//				}
//			}
//				
//	}
	
	public Boolean decidirYo(Hilo hilito) {
		
		if (contador.get(hilito.getID()) <= contador.get(hilito.getIDR())) {
			// Me toca a mi, me despierto y termino busqueda del hilo a despertar
			Log.spit("Voy: "+contador.get(hilito.getID())+" y el otro va: "+ contador.get(hilito.getIDR()));
//			contador.replace(hilito.getID(), contador.get(hilito.getID()),
//					contador.get(hilito.getID()) + 1);
//			Log.spit("Ahora voy: "+contador.get(hilito.getID()));
			return true;
		} 
		return false;
	}
	
	public Boolean decidirRival(Hilo hilito, Queue<Hilo> espera) {
		
		for (Hilo hilito2 : espera) {
			if (hilito.getIDR() == hilito2.getID()) {
				//Esta el otro, lo despierto
				Log.spit("Voy: "+contador.get(hilito.getID())+" y el otro va: "+ contador.get(hilito.getIDR()));
//				contador.replace(hilito2.getID(), contador.get(hilito2.getID()),
//						contador.get(hilito2.getID()) + 1);
//				Log.spit("Ahora el otro va: "+contador.get(hilito2.getID()));
				//Despues de despertarlo, tengo que dormir y terminar la busqueda del 
				//hilo a despertar
				return true;
				} 
			}
		return false;
	}
	
	public void aumentar(Hilo hilito) {
		//Log.spit("Voy: "+contador.get(hilito.getID())+" y el otro va: "+ contador.get(hilito.getIDR()));
		contador.replace(hilito.getID(), contador.get(hilito.getID()),
				contador.get(hilito.getID()) + 1);
		Log.spit("Ahora voy: "+contador.get(hilito.getID()));
	}
	
//	public void setContador(int ID) {
//		contador.put(ID, 0);
//	}
	
	public void inicializar(int cantidad) {
		for( int i = 0; i < cantidad; i++ ) {
			contador.put( i, 0 );
		}
	}
}
