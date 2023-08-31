import java.util.*;
import java.io.*;

public class Main_bj_g5_맥주_마시면서_걸어가기 {
	static int[] dr = { 0, 1, 0, -1 };
	static int[] dc = { 1, 0, -1, 0 };

	static int N;

	final static int INF = Integer.MAX_VALUE;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			int[] startC = new int[3];
			int[] endC = new int[3];
			int[][] convC_arr = new int[N][3];

			StringTokenizer st = new StringTokenizer(br.readLine());
			startC[0] = Integer.parseInt(st.nextToken());
			startC[1] = Integer.parseInt(st.nextToken());
			startC[2] = 0;

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				convC_arr[i][0] = Integer.parseInt(st.nextToken());
				convC_arr[i][1] = Integer.parseInt(st.nextToken());
				convC_arr[i][2] = INF;
			}

			st = new StringTokenizer(br.readLine());
			endC[0] = Integer.parseInt(st.nextToken());
			endC[1] = Integer.parseInt(st.nextToken());

			Queue<int[]> q = new ArrayDeque<>();
			q.offer(startC);
			
			while()

		}

		br.close();
	}

	static boolean isValid(int r, int c) {
		if (0 <= r && r < N && 0 <= c && c < N)
			return true;
		else
			return false;
	}

}