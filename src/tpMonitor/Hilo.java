package tpMonitor;

import java.util.ArrayList;

public class Hilo {
	ArrayList<int[]> tareas;
	
	public void Hilo(){
		System.out.println("Se ha creado un hilo");
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
