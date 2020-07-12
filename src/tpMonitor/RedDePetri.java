package tpMonitor;

public class RedDePetri {

	private int [][] matrizIncidencia;
	private int [] tranSensibilizadas;
	private int [] marcaInicial;
	private int [] marcaActual;
	private int [] aux;
	
	
	public RedDePetri(int[][] inc, int[] trans, int[] act) {
		matrizIncidencia= inc;
		tranSensibilizadas = trans;
		marcaInicial = act;
		marcaActual = act;
	}	
	
	public void changeCurrent(int[] newCurrent) {
		marcaActual = newCurrent;
	}
	
	public void protocoloAlfa() {
		System.out.println("RDP - Usted ha activado el protocolo alfa");
	}
	
	public boolean verificarCompatibilidad(int[] tarea){
		//Ej: verificarCompatibilidad(hilo.getTarea())
		for(int i = 0; i < tranSensibilizadas.length; i++) {
			if(tranSensibilizadas[i]==1 && tarea[i]==1){
				System.out.println("RDP - Transicion detectada: T"+i);
				return true;
			}
		}
		return false;
	}
	
	public void calcularMarcaActual(int[] disparo){
		int aux;
		for (int i = 0; i < matrizIncidencia.length; i++) {
			aux = 0;
			for (int j = 0; j < matrizIncidencia[0].length; j++) {
				aux += matrizIncidencia[i][j]*disparo[j];
			}
			marcaActual[i]+=aux;
		}
	}
	
	public void calcularVectorSensible(){
		int[] s = new int[matrizIncidencia[0].length];
		
		for (int i = 0; i < matrizIncidencia[0].length; i++) { //Recorrer columnas
			for (int j = 0; j < matrizIncidencia.length; j++) { //Recorrer filas
				s[j] = marcaActual[j] + matrizIncidencia[j][i];	
			}
			
			tranSensibilizadas[i]=1;
			for (int x = 0; x < s.length; x++) {
				if(s[x]<0){
					tranSensibilizadas[i]=0;
					break;
				}
			}
		}
	}
	
	//Getters
	public int[] getMarcaActual() {
		return marcaActual;
	}
	public int[][] getMatrizIncidencia() {
		return matrizIncidencia;
	}
	public int[] getMarcaInicial() {
		return marcaInicial;
	}
	public void printSensible() {
		for (int i = 0; i < tranSensibilizadas.length; i++) {
			System.out.print(tranSensibilizadas[i]+", ");
		}
		System.out.println("");
	}
	
	public void printActual() {
		for (int i = 0; i < marcaActual.length; i++) {
			System.out.print(marcaActual[i]+", ");
		}
		System.out.println("");
	}
	
	public int[] getTranSensibilizadas() {
		return tranSensibilizadas;
	}
}
