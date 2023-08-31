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
			endC[2] = INF;

			Queue<int[]> q = new ArrayDeque<>();
			q.offer(startC);

			while (q.isEmpty() == false) {
				int[] cord = q.poll();
				// System.out.println(Arrays.toString(cord));

				if (getDist(cord, endC) <= 1000) {
					System.out.println("happy");
					return;
				}

				for (int[] convC : convC_arr) {
					if (getDist(cord, convC) <= 1000 && convC[2] > cord[2]) {
						convC[2] = cord[2] + 1;
						q.offer(convC);
					}
				}
			}

			System.out.println("sad");
		}

		br.close();
	}

	static boolean isValid(int r, int c) {
		if (0 <= r && r < N && 0 <= c && c < N)
			return true;
		else
			return false;
	}

	static int getDist(int[] cord1, int[] cord2) {
		return Math.abs(cord1[0] - cord2[0]) + Math.abs(cord1[1] - cord2[1]);
	}

}