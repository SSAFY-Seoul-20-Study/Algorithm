import java.util.*;
import java.io.*;

public class Main_bj_g3_욕심쟁이_판다 {
	static int N;
	static int[][] a, b;

	static int[] dr = { 0, 1, 0, -1 };
	static int[] dc = { 1, 0, -1, 0 };

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		a = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				a[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int max_depth = 0;
		b = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				max_depth = Integer.max(max_depth, dfs(i, j));
			}
		}

		System.out.print(max_depth);

		br.close();
	}

	static int dfs(int r, int c) {

		if (b[r][c] == 0) {
			int local_max = 0;
			for (int k = 0; k < 4; k++) {
				int nr = r + dr[k];
				int nc = c + dc[k];
				if (isValid(nr, nc) && a[r][c] < a[nr][nc]) {
					local_max = Integer.max(local_max, dfs(nr, nc));
				}
			}
			b[r][c] = 1 + local_max;
		}

		return b[r][c];
	}

	static boolean isValid(int r, int c) {
		if (0 <= r && r < N && 0 <= c && c < N)
			return true;
		else
			return false;
	}

}