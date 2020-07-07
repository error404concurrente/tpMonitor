package tpMonitor;

public class RedDePetri {

	private int [][] matrizIncidencia;
	private int [] tranSensibilizadas;
	private int [] marcaInicial;
	private int[] marcaActual;
	
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
		System.out.println("Usted ha activado el protocolo alfa");
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
	public int[] getTranSensibilizadas() {
		return tranSensibilizadas;
	}
}
