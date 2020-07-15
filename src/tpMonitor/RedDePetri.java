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
	
	public boolean disparar(Hilo hilo) {
		Log.spit("Hilo entrante: "+Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
		if(verificarCompatibilidad(hilo.getTarea())) {
			Log.spit("Compatibilidad Confirmada");
//			Log.spit("Hilo entrante: "+Thread.currentThread().getName()+"  Disparo: "+hilo.strTarea());
			Log.spit("-------------- Resultados --------------");
			Log.spit("Estado de RdP Antes:   "+strMarcaActual()+"  ----  T. Sensibles Antes:   "+strTranSensible());
			calcularMarcaActual(hilo.getTarea());
			calcularVectorSensible();
			Log.spit("Estado de RdP Despues: "+strMarcaActual()+"  ----  T. Sensibles Despues: "+strTranSensible());
			Log.spit("-------------- Fin Resultados --------------");
			return true;
		}else {
			Log.spit("Compatibilidad Denegada");
			return false;
		}
	}
	
	public boolean verificarCompatibilidad(int[] tarea){
		//Ej: verificarCompatibilidad(hilo.getTarea())
		for(int i = 0; i < tranSensibilizadas.length; i++) {
			if(tranSensibilizadas[i]==1 && tarea[i]==1){
				Log.spit("RDP - Transicion detectada: T"+i);
				return true;
			}
		}
		return false;
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
