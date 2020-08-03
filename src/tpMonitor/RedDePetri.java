package tpMonitor;

import java.util.ArrayList;
import java.util.Arrays;

public class RedDePetri {

	private int [][] matrizIncidencia;
	private int [] tranSensibilizadas;
	private long [] transTimestamp;
	private int [] marcaInicial;
	private int [] marcaActual;
	private long [] alfa;
	private long [] beta;
	private int[][] pinvariant = {{0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
								  {0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
								  {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
								  {1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
								  {0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,1,0},
								  {0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,0,0,0,1},
								  {0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
								  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1}};


	public RedDePetri(int[][] inc, int[] trans, int[] act, long [] alfa, long [] beta) {
		matrizIncidencia   = inc;
		tranSensibilizadas = trans;
		marcaInicial       = act;
		marcaActual        = act;
		this.alfa          = alfa;
		this.beta          = beta;
		transTimestamp     = new long[tranSensibilizadas.length];
		
		for (int i = 0; i < transTimestamp .length; i++) {
			transTimestamp[i]=System.currentTimeMillis();
		}
	}

	public void changeCurrent(int[] newCurrent) {
		marcaActual = newCurrent;
	}

	public void disparar(Hilo hilo) throws InterruptedException {
//		Log.spit("Hilo entrante: " + Thread.currentThread().getName() + "  Disparo: " + hilo.strTarea());
//		Log.spit("Compatibilidad Confirmada");
//		Log.spit("-------------- Resultados de Disparo --------------");
//		Log.spit("Estado de RdP Antes:   " + strMarcaActual() + "  ----  T. Sensibles Antes:   " + strTranSensible());
		calcularMarcaActual(hilo.getTarea());
		calcularVectorSensible();
		Log.spit("[Disparo Efectuado]: " + Thread.currentThread().getName() + "  [Estado Actual]: " + strMarcaActual()
				+ " [T.Sensibilizadas]: " + strTranSensible());
//		Log.spit("Estado de RdP Despues: " + strMarcaActual() + "  ----  T. Sensibles Despues: " + strTranSensible());
//		Log.spit("-------------- Fin Resultados --------------");
		Politicas.aumentar(hilo);
		Log.addDisparo(hilo.getTarea());
		showPinvariants(pinvariant, marcaActual);

	}

	public boolean verificarCompatibilidad(int[] tarea, Hilo hilo) throws InterruptedException {
		// Ej: verificarCompatibilidad(hilo.getTarea())
		for (int i = 0; i < tranSensibilizadas.length; i++) {
			if (tranSensibilizadas[i] == 1 && tarea[i] == 1) {
				if ((!hilo.getPolitico() || (hilo.getPolitico() && Politicas.decidirYo(hilo)))) {
					// Log.spit("RDP - Transicion detectada: T"+i);
					return true;
				}
			}
		}
//		Log.spit("Compatibilidad Denegada");
		return false;
	}
	
	public boolean verificarCompatibilidad(int[] tarea){
		//Ej: verificarCompatibilidad(hilo.getTarea())
		boolean compatible = false;
		for(int i = 0; i < tranSensibilizadas.length; i++) {
			if(tranSensibilizadas[i]==1 && tarea[i]==1){
//				Log.spit("RDP: Transicion compatible detectada: T"+i);
				compatible = true;
			}
		}
		return compatible;
	}

	private void calcularMarcaActual(int[] disparo){
		int aux;
		for (int i = 0; i < matrizIncidencia.length; i++) {
			aux = 0;
			for (int j = 0; j < matrizIncidencia[0].length; j++) {
				aux += matrizIncidencia[i][j]*disparo[j]; //Ecuacion de Estado
			}
			marcaActual[i]+=aux;
		}
	}

	private void calcularVectorSensible() {
		int[] s = new int[marcaActual.length]; // vector auxiliar S
		int[] aux = new int[tranSensibilizadas.length];
		for (int i = 0; i < aux.length; i++) {
			aux[i] = tranSensibilizadas[i];
		}

		for (int i = 0; i < matrizIncidencia[0].length; i++) { // Recorrer columnas
			for (int j = 0; j < matrizIncidencia.length; j++) { // Recorrer filas
				s[j] = marcaActual[j] + matrizIncidencia[j][i]; // Calculo de S
			}
			
			  //Crear nuevo vector de transiciones sensibilizadas En la posicion i va a ser 1
			  //si no hay valores negativos en S, de otra forma ser
			tranSensibilizadas[i] = 1;
//			transTimestamp[i]=System.currentTimeMillis();
			for (int x = 0; x < s.length; x++) {
				if (s[x] < 0) {
					tranSensibilizadas[i] = 0;
					break;
				}
			}
		}
		
		for (int i = 0; i < tranSensibilizadas.length; i++) {
			if(aux[i]==0 && tranSensibilizadas[i]==1) {
				transTimestamp[i]=System.currentTimeMillis();
			}else if(aux[i]==1 && tranSensibilizadas[i]==0){
				transTimestamp[i]=0;
			}
		}	
	}
		
	public boolean checkTemporal(Hilo hilo) throws InterruptedException {
		int[] aux = hilo.getTarea();
		for (int i = 0; i < aux.length; i++) {
			if (aux[i] == 1) {
				long tSensible = System.currentTimeMillis() - transTimestamp[i];
				long t = alfa[i] - tSensible;
				if (t > 0) {
//					Log.spit("MIMIENDO");
//					Log.spit("t:" + t + "   " + alfa[i] + "  " + System.currentTimeMillis() + "   " + transTimestamp[i]);
					return false;
				} else if (t <= 0 && beta[i] >= tSensible) {
//					Log.spit("NO MIMIENDO: t:" + t + "   " + alfa[i] + "  " + System.currentTimeMillis() + "   "+ transTimestamp[i]);
					return true;
				} else if (t <= 0 && beta[i] < tSensible) {
					Log.spit(beta[i] + " ");
//					Log.spit("Error de Beta: "+tSensible+">beta");
					return false;
				}
			}
		}
		return true;
	}

	public long mimirTime(Hilo hilo) {
		int[] aux = hilo.getTarea();
		for (int i = 0; i < aux.length; i++) {
			if (aux[i] == 1) {
				long tSensible = System.currentTimeMillis() - transTimestamp[i];
				long t = alfa[i] - tSensible;
				return t;
			}
		}
		return 1000;
	}
	
	public void showPinvariants(int[][] pinvariant, int[] m) {
		String s = "";
		boolean first = false;
		int k = 0;
		for (int i = 0; i < pinvariant.length; i++) {
			first = true;
			for (int j = 0; j < pinvariant[0].length; j++) {

				if (pinvariant[i][j] != 0 && first) {
					s += " P" + j + "(" + m[j] + ") ";
					k += m[j];
					first = false;
				} else if (pinvariant[i][j] != 0) {
					k += m[j];
					s += " +  P" + j + "(" + m[j] + ")";
				}
			}
			s += " = " + k;
			Log.spit(s);
			k = 0;
			s = "";
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
	
	public String strTranSensible() {
		String v = "";
		for (int i = 0; i < tranSensibilizadas.length; i++) {
			v = v+tranSensibilizadas[i]+", ";
		}
		return v;
	}

	public String strMarcaActual() {
		String v = "";
		for (int i = 0; i < marcaActual.length; i++) {
			v+=marcaActual[i]+", ";
		}
		return v;
	}

	public int[] getTranSensibilizadas() {
		return tranSensibilizadas;
	}
}
