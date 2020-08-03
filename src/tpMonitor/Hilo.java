package tpMonitor;

import java.util.ArrayList;


public class Hilo implements Runnable{
	
	private boolean politico;
	private int IDR;
	private int ID;
	Monitor monitor;
	int[] secundario;
	int[] tarea;
	
	public Hilo(Monitor monitor, int[] tarea, int[] secundario, boolean politico, int ID, int IDR){
		this.monitor    = monitor;
		this.tarea      = tarea;
		this.secundario = secundario;
		this.politico   = politico;
		this.ID  = ID;
		this.IDR = IDR;
	}
	
	public void run(){		
		while(!Politicas.terminado()) {
			try {
				monitor.enter(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int[] aux  = tarea;
            tarea      = secundario;
            secundario = aux;
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
	
	public boolean getPolitico() {
		return politico;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getIDR() {
		return IDR;
	}

}
