package tpMonitor;

import java.util.ArrayList;

public class Hilo implements Runnable{
	int[] tarea;
	Monitor monitor;
	
	public Hilo(Monitor monitor, int[] tarea){
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
	
	
	public String strTarea() {
		String v = "";
		for (int i = 0; i < tarea.length; i++) {
			v = v+tarea[i]+", ";
		}
		return v;
	}
	
	public int[] getTarea() {
		return tarea;
	}
	
}
