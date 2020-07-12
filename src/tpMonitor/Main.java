package tpMonitor;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] inc = {{0,0,0,0,1,1},{1,0,0,0,0,0},{0,1,0,0,0,0},{0,0,0,1,0,0},{0,0,1,0,0,0}};
		int[] marking = {1,0,0,0,0};
		int[] transitions = {1,1,0,0,0,0};
		final int cantHilos = 99;
 		
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print(inc[i][j]+" ");
			}
			System.out.println("");
		}
		
		//Creación de RpP
		RedDePetri rdp = new RedDePetri(inc, transitions, marking);
		
		//Creación de Monitor
		Monitor monitor = new Monitor(cantHilos, rdp);
		
		//CReacion hilo
		int[] tarea = {1,0,0,0,0,0};
		int[] tarea2 = {0,1,0,0,0,0};
		Hilo hilo = new Hilo(monitor, tarea);
		Hilo hilo2 = new Hilo(monitor, tarea2);
		Thread hilito = new Thread(hilo, "hilito");
		Thread hilito2 = new Thread(hilo2, "hilito2");
		
		
		hilito.start();
		hilito2.start();
		
	}
}
