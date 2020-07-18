package tpMonitor;

import java.util.ArrayList;
import java.util.Arrays;

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
		int [][] mT =getTranspuesta(inc);
		farkasAlgorithm(concatMatrix(mT,getidentityMatrix(mT.length)),mT.length,mT[0].length);
	}	
	
	public void changeCurrent(int[] newCurrent) {
		marcaActual = newCurrent;
	}
	
	public boolean disparar(Hilo hilo) {
		Log.spit("RdP: Analizando compatibilidad de "+Thread.currentThread().getName()+"  Disparo: {"+hilo.strTarea()+"}");
		if(verificarCompatibilidad(hilo.getTarea())) {
			Log.spit("RdP: Compatibilidad Confirmada a "+Thread.currentThread().getName());
//			Log.spit("Hilo entrante: "+Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
			Log.spit("-------------- Resultados de Disparo --------------");
			Log.spit("Estado de RdP Antes:   "+strMarcaActual()+"  ----  T. Sensibles Antes:   "+strTranSensible());
			calcularMarcaActual(hilo.getTarea());
			calcularVectorSensible();
			Log.spit("Estado de RdP Despues: "+strMarcaActual()+"  ----  T. Sensibles Despues: "+strTranSensible());
			Log.spit("------------------ Fin Resultados -----------------");
			return true;
		}else {
			Log.spit("RdP: Compatibilidad Denegada a "+Thread.currentThread().getName());
			return false;
		}
	}
	
	public boolean verificarCompatibilidad(int[] tarea){
		//Ej: verificarCompatibilidad(hilo.getTarea())
		boolean compatible = false;
		for(int i = 0; i < tranSensibilizadas.length; i++) {
			if(tranSensibilizadas[i]==1 && tarea[i]==1){
				Log.spit("RDP: Transicion compatible detectada: T"+i);
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
	
	private void calcularVectorSensible(){
		int[] s = new int[matrizIncidencia[0].length];  //vector auxiliar S
		
		for (int i = 0; i < matrizIncidencia[0].length; i++) { //Recorrer columnas
			for (int j = 0; j < matrizIncidencia.length; j++) { //Recorrer filas
				s[j] = marcaActual[j] + matrizIncidencia[j][i];	//Calculo de S
			}
			/**Crear nuevo vector de transiciones sensibilizadas
			 * En la posicion i va a ser 1 si no hay valores negativos en S,
			 * de otra forma será 0 
			 */
			tranSensibilizadas[i]=1;
			for (int x = 0; x < s.length; x++) {
				if(s[x]<0){
					tranSensibilizadas[i]=0;
					break;
				}
			}
		}
	}
	private void farkasAlgorithm(int [][]concat,int rowInc, int colInc) {
		int pivot = 0, pivotNum = 0, factor = 0;
		boolean notFind = true;
		ArrayList<Integer> regPivot = new ArrayList<Integer>();
		
		
		for(int j=0; j<colInc; j++) {
            for(int i=0; i<concat.length; i++) {
            	if(concat[i][j]!=0 && notFind) {
            	  pivot = i;
            	  pivotNum = concat[i][j];
            	  notFind=false;
            	  regPivot.add(pivot);
            	  System.out.println("\npivot in row = "+pivot+" col="+j+" value = "+pivotNum);
            	}
            	
            	if(concat[i][j]!=0 && notFind==false && i != pivot ) {
            		factor = (concat[i][j] == pivotNum)? -1: 1;
            		System.out.println("\nfactor = "+factor);
            		System.out.println("row ="+i+"  col= "+j+" valor = "+concat[i][j]+"  en for k: ");
            		for(int k=0;k<concat[0].length;k++) {
            			concat[i][k]=concat[i][k]+factor*(concat[pivot][k]);
            		}	
            	}
            }
            for(int k=0;k<concat[0].length;k++) {
    			concat[pivot][k]=0;
    		}
    		printMatrix(concat);
            notFind = true;
		}
		
		//int M = matrizIncidencia.length;
		int row=0;
		for(int pivote :regPivot) {
			System.out.println(pivote);
		}
		int[][] pinvariants = new int[rowInc-regPivot.size()][rowInc];
		for(int i=0;i<concat.length;i++) {
			
			for(int j=colInc;j<concat[0].length;j++) {
				if(!regPivot.contains(i)) {
					System.out.println("i="+row+" j="+(j-colInc));
					pinvariants[row][(j-colInc)] = concat[i][j];
				}
			}
			if(!regPivot.contains(i)) {row++;}
		}
		printMatrix(pinvariants);
		
	}
	public void  printMatrix(int[][] concat) {
		System.out.println("\n\n--------------------------------------------");
		System.out.println("Mi");
		for(int i=0; i<concat.length ; i++) {
			System.out.println();
			for(int j=0; j<concat[0].length ; j++) {
				System.out.print(concat[i][j]+"   ");
			}
		}
		System.out.println("\n--------------------------------------------\n");
	}
	public int[][] getidentityMatrix(int N) {
		int [][] identity = new int[N][N];
		for(int i=0; i<N ; i++) {
			for(int j=0; j<N ; j++) {
				identity[i][j] = (i==j)? 1 : 0;
			}
		}
		System.out.println("Identidad: ");
		for(int i=0; i<N ; i++) {
			System.out.println();
			for(int j=0; j<N ; j++) {
				System.out.print(identity[i][j]+" ");
			}
		}
		return identity;
	}
	public int[][] concatMatrix(int[][] inc, int[][] id) {
		
	    int N = inc.length;
		int M = inc[0].length + id[0].length;
		int row=0; int col=0;
		int[][]concat = new int[N][M];
		for(int j=0; j<inc[0].length;j++) {
			for(int i=0; i<N; i++) {
				concat[i][j]=inc[i][j];
			}
		}
		for(int j=inc[0].length; j<M ; j++) {
			for(int i=0; i<N ;i++) {
				concat[i][j]=id[row][col];
				row++;
			}
			row=0;
			col++;
		}
		System.out.println("\n\nC|I: ");
		for(int i=0; i<concat.length ; i++) {
			System.out.println();
			for(int j=0; j<concat[0].length ; j++) {
				System.out.print(concat[i][j]+"   ");
			}
		}
		//System.out.println(Arrays.deepToString(concat));
		return concat;

	}
	
	public int[][] getTranspuesta(int[][] matrix) {
		int[][] mT = new int[matrix[0].length][matrix.length];
		for (int i=0; i < matrix.length; i++) {
			  for (int j=0; j < matrix[i].length; j++) {
			    mT[j][i] = matrix[i][j];
			  }
			}
		printMatrix(matrix);
		printMatrix(mT);
		return mT;
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
