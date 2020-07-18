package tpMonitor;


import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Log.spit("=============================== Trabajo Final de Programacion Concurrente ===============================");
		Log.spit("* Name        : tpMonitor");
		Log.spit("* Author: Vega Jimena, Chagay Vera Adriel, Klincovitzky Sebastian, Severini Alejo, Wortley Agustina");
		Log.spit("* Version     : BETA");
		Log.spit("* Description : Software de manejo de Red de Petri haciendo uso de un Monitor");
		Log.spit("=========================================================================================================\n");
		
		//Log.createLog();//Obligatorio para log
		
		int[][] inc = {{-1,-1,0,0,1,1},{1,0,0,-1,0,0},{0,1,-1,0,0,0},{0,0,0,1,0,-1},{0,0,1,0,-1,0}};
		int[] marking = {1,0,0,0,0};
		int[] transitions = {1,1,0,0,0,0};
		final int cantHilos = 99;
 				
		//Creacion de RpP
		RedDePetri rdp = new RedDePetri(inc, transitions, marking);
		
//		//Creacion de Politica
//		Politicas politica = new Politicas();
		
		//Creacion de Monitor
		Monitor monitor = new Monitor(cantHilos, rdp/*, politica*/);
		
		
<<<<<<< HEAD
		/**	Creacion de Hilos y tareas
		 * Se entienden como tareas a las transiciones que el hilo quiere disparar
		 */
=======

		//Creacion hilo
       
>>>>>>> 17541e27aa5a0455b0b9875729b83f2e52515390
		final int N_TAREAS = 6;
//		politica.inicializar(N_TAREAS);
		//Creacion de Politica
		Politicas politica = new Politicas(N_TAREAS);
				
		int [][] tareas = {{1,0,0,0,0,0},{0,1,0,0,0,0},{0,0,1,0,0,0},{0,0,0,1,0,0},{0,0,0,0,1,0},{0,0,0,0,0,1}};
		ArrayList <Thread> hilito = new ArrayList<Thread>();
		
		hilito.add(new Thread(new Hilo(monitor, tareas[0], true, 0, 1),"hilito "+0));
		//politica.setContador(0);
		hilito.get(0).start();
		hilito.add(new Thread(new Hilo(monitor, tareas[1], true, 1, 0),"hilito "+1));
		//politica.setContador(1);
		hilito.get(1).start();
		
		for(int i=2; i<N_TAREAS;i++) {
			hilito.add(new Thread(new Hilo(monitor, tareas[i], false, i, i),"hilito "+i));
			hilito.get(i).start();
		}	
		
		
	}
}
