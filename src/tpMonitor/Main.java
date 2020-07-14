package tpMonitor;


import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		//Log.createLog();//Obligatorio para log
		
		int[][] inc = {{-1,-1,0,0,1,1},{1,0,0,-1,0,0},{0,1,-1,0,0,0},{0,0,0,1,0,-1},{0,0,1,0,-1,0}};
		int[] marking = {1,0,0,0,0};
		int[] transitions = {1,1,0,0,0,0};
		final int cantHilos = 99;
 				
		//Creación de RpP
		RedDePetri rdp = new RedDePetri(inc, transitions, marking);
		
		//Creación de Monitor
		Monitor monitor = new Monitor(cantHilos, rdp);
		

		//CReacion hilo
       
		final int N_TAREAS = 6;
		int [][] tareas = {{1,0,0,0,0,0},{0,1,0,0,0,0},{0,0,1,0,0,0},{0,0,0,1,0,0},{0,0,0,0,1,0},{0,0,0,0,0,1}};
		ArrayList <Thread> hilito = new ArrayList<Thread>();
		for(int i=0; i<N_TAREAS;i++) {
			
			hilito.add(new Thread(new Hilo(monitor, tareas[i]),"hilito "+i));
			hilito.get(i).start();
		}	
	}
}
