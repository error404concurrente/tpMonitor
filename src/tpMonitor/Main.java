package tpMonitor;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] incidence = {{-1,-1,0,0,1,1},{1,0,0,-1,0,0},{0,1,-1,0,0,0},{0,0,0,1,-1,0},{0,0,1,0,0,-1}};
		int[][] marking = {{0,0,0,0},{0,0,0,0}};
		int[] transitions = {0,0,0,0};
 		
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print(incidence[i][j]+" ");
			}
			System.out.println("");
		}
	}
}
