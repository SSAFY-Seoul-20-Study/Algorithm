import java.util.*;
import java.io.*;

public class Main_bj_g1_2933_미네랄 {
	static int N, M;
	static int[] dr = { 1, 0, -1, 0 };
	static int[] dc = { 0, 1, 0, -1 };
	static boolean arr[][];

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new boolean[N][M];
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				if(s.charAt(j) == 'x') {
					arr[i][j] = true;
				}
				if(s.charAt(j) == '.') {
					arr[i][j] = false;
				}
			}
		}

		int totalTurns = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine());

		boolean shotLeft = true; //true: 왼쪽에서 오른쪽으로, false: 오른쪽에서 왼쪽으로

		for(int turn=0;turn<totalTurns;turn++) {
			int level = Integer.parseInt(st.nextToken());
			shot(level, shotLeft);
			fall();
			break;
		}

		for(boolean[] t: arr) System.out.println(Arrays.toString(t));

		br.close();
	}

	static void shot(int level, boolean shotLeft){
		int col = shotLeft ? 0 : N-1;
		int dc = shotLeft ? 1 : -1;
		int row = N - level;

		while(0 <= col && col < N) {
			System.out.println(row + " " + col);
			if(arr[row][col]) {
				arr[row][col] = false;
				break;
			}
			col += dc;
		}
	}

	static void fall(){
		
	}

}