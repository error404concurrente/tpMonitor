package tpMonitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.security.Timestamp;
import java.text.SimpleDateFormat;

public class Main {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	public static void main(String[] args) throws FileNotFoundException {
		//Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		//Descomentar la siguiente linea para generar logs
		//System.setOut(new PrintStream(new File("log.txt")));
		
		int[][] inc = {{-1,-1,0,0,1,1},{1,0,0,-1,0,0},{0,1,-1,0,0,0},{0,0,0,1,0,-1},{0,0,1,0,-1,0}};
		int[] marking = {1,0,0,0,0};
		int[] transitions = {1,1,0,0,0,0};
		final int cantHilos = 99;
 				
		//Creación de RpP
		RedDePetri rdp = new RedDePetri(inc, transitions, marking);
		
		//Creación de Monitor
		Monitor monitor = new Monitor(cantHilos, rdp);
		
		//CReacion hilo
//		int[] tarea = {0,0,0,0,0,0};
//		for (int i = 0; i < 6; i++) {
//			tarea[i]=1;
//			Hilo hilo = new Hilo(monitor, tarea);
//			Thread hilito = new Thread(hilo, "hilito"+i);
//			hilito.start();
//			tarea[i]=0;
//		}
		
		int[] tarea0 = {1,0,0,0,0,0};
		int[] tarea1 = {0,1,0,0,0,0};
		int[] tarea2 = {0,0,1,0,0,0};
		int[] tarea3 = {0,0,0,1,0,0};
		int[] tarea4 = {0,0,0,0,1,0};
		int[] tarea5 = {0,0,0,0,0,1};
		Hilo hilo0 = new Hilo(monitor, tarea0);
		Hilo hilo1 = new Hilo(monitor, tarea1);
		Hilo hilo2 = new Hilo(monitor, tarea2);
		Hilo hilo3 = new Hilo(monitor, tarea3);
		Hilo hilo4 = new Hilo(monitor, tarea4);
		Hilo hilo5 = new Hilo(monitor, tarea5);
		Thread hilito0 = new Thread(hilo0, "hilito0");
		Thread hilito1 = new Thread(hilo1, "hilito1");
		Thread hilito2 = new Thread(hilo2, "hilito2");
		Thread hilito3 = new Thread(hilo3, "hilito3");
		Thread hilito4 = new Thread(hilo4, "hilito4");
		Thread hilito5 = new Thread(hilo5, "hilito5");
		
		
		hilito0.start();
		hilito1.start();
		hilito2.start();
		hilito3.start();
		hilito4.start();
		hilito5.start();
		
	}
}
