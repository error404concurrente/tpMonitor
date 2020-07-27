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
		
		int[][] inc = { { 0,  1,  0, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
						{ 0,  0,  1,  0, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
						{ 1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
						{ 0,  0,  0,  0,  0,  0,  0,  0, -1,  0, -1,  0,  0,  0,  7,  0,  0},
						{ 0,  0,  0,  0,  0,  0,  0,  0,  0, -1,  0, -1,  0,  0,  0,  7,  0},
						{ 0, -1,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
						{ 0,  0, -1,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
						{ 0,  0,  0,  0,  0,  0,  1,  0, -1, -1,  0,  0,  0,  0,  0,  0,  1},
						{ 0,  0,  0,  0,  0,  1,  0,  1,  0,  0, -1, -1,  0,  0,  0,  0,  0},
						{ 0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  1,  0,  0,  0, -7,  0,  0},
						{ 0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  1,  0,  0,  0, -7,  0},
						{-1,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
						{ 0,  0,  0, -1,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0,  0,  0},
						{ 0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0},
						{ 0,  0,  0,  0,  1, -1,  0,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0},
						{ 0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0, -1},
						{ 0,  0,  0, -1, -1,  1,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  1},
						{ 0,  0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0},
						{ 0,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  1,  0,  0,  0} };
		
		
		int[] marking = { 0,  0,  0,  8,  8,  4,  4,  0,  0,  0,  0,  1,  1,  1,  0,  0,  1,  0,  0};
		Log.spit(marking.length+"");
		int[] tSensibility = { 1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		
		final int cantHilos = 99;
		//Tiempo en ms
		long[] alfa = {50,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		long[] beta = {0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff,  0xfffffff};

		//Creacion de RpP
		RedDePetri rdp = new RedDePetri(inc, tSensibility, marking, alfa, beta);


		//Creacion de Monitor
		Monitor monitor = new Monitor(cantHilos, rdp);



		/**	Creacion de Hilos y transiciones
		 * Se entienden como tareas a las transiciones que el hilo quiere disparar
		 */
		int[][] transiciones = {{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //Arrival Rate
								{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //Asignar P1
								{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //Asignar P2
								{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //Empezar P1
								{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //Empezar P2
								{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};

		final int N_TRANS= transiciones.length;
//		politica.inicializar(N_TAREAS);
		//Creacion de Politica
		Politicas politica = new Politicas(N_TRANS);
		
		ArrayList <Thread> hilito = new ArrayList<Thread>();
		
		for(int i=0; i<N_TRANS;i++) {
				hilito.add(new Thread(new Hilo(monitor, transiciones[i], false, i, i),"hilito "+i));
				hilito.get(i).start();
		}
	}
}
