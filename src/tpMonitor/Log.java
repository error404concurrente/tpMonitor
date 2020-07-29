package tpMonitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Log {
	private static final SimpleDateFormat sdfTitulo = new SimpleDateFormat("[dd:mm:yyyy]-[HH:mm:ss]: ");
	public static PrintStream archivo;
	public static ArrayList<Integer> disparos = new ArrayList<Integer>();
	
	public static void createLog() throws FileNotFoundException {
		Date resultdate = new Date(System.currentTimeMillis());
		System.out.println("Log: Creando archivo log...");
		archivo = new PrintStream(new File("log"+sdfTitulo.format(resultdate)+".txt"));
		System.setOut(archivo);
	}
	
	public static String ts() {
		//Funcion global para imprimir timestamps en consola, y por lo tanto en el log
		SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss:SSS]: ");
		Date resultdate = new Date(System.currentTimeMillis());
		return sdf.format(resultdate);
	}
	
	public static void spit(String str) {
		System.out.println(ts()+str);
	}
	
	public static void addDisparo(int[] disparo) {
		for (int i = 0; i < disparo.length; i++) {
			disparos.add(disparo[i]);
		}
	}
	
	public static void createLogDisparos() throws FileNotFoundException {
		archivo = new PrintStream(new File("log"+sdfTitulo.format("disparos.txt")));
		System.setOut(archivo);
		for (int i = 0; i < disparos.size(); i++) {
			if(i%17==0) {
				System.out.print(disparos.get(i)+"");
			}else {
				System.out.print(disparos.get(i)+" ");
			}
		}		
	}
}
