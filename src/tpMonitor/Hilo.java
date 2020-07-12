package tpMonitor;

import java.util.ArrayList;

public class Hilo implements Runnable{
	int[] tarea;
	Monitor monitor;
	
	public Hilo(Monitor monitor, int[] tarea){
		System.out.println("Se ha creado un hilo");
		this.monitor = monitor;
		this.tarea = tarea;
	}
	
	public void run(){
		try {
			monitor.enter(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int[] getTarea() {
		return tarea;
	}
	
}
