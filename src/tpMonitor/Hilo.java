package tpMonitor;

import java.util.ArrayList;


public class Hilo implements Runnable{
	int[] tarea;
	int[] secundario;
	Monitor monitor;
	private boolean politico;
	private int ID;
	private int IDR;
	
	public Hilo(Monitor monitor, int[] tarea, int[] secundario, boolean politico, int ID, int IDR){
		this.monitor = monitor;
		this.tarea = tarea;
		this.secundario = secundario;
		this.politico = politico;
		this.ID = ID;
		this.IDR = IDR;
	}
	
	public void run(){		
		while(!Politicas.terminado()) {
			try {
				monitor.enter(this);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int[] aux = tarea;
            tarea = secundario;
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
