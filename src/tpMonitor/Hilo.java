package tpMonitor;

import java.util.ArrayList;

abstract class Hilo implements Runnable{
	ArrayList<int[]> tareas;
	Monitor monitor;
	
	public Hilo(Monitor moni){
		System.out.println("Se ha creado un hilo");
		monitor = moni;
	}
	
	public void run(){
		
	}
	
	public void addTarea(int[] tarea){
		/**Añade tareas al hilo, Ej:
		 *  tarea = {0,0,0,1}
		 *  hilo.add(tarea);
		 *  hilo.tareas seria {...,{0,0,0,1}}
		 */
		tareas.add(tarea);
	}	
}
