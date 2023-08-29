import java.util.*;
import java.io.*;

public class Main_bj_g3_욕심쟁이_판다 {
	static int N;
	static int[][] a, b;

	static int[] dr = { 0, 1, 0, -1 };
	static int[] dc = { 1, 0, -1, 0 };

	static int max_depth;

	public static void main(String args[]) throws Exception {
		System.setIn(new FileInputStream("한태희/res/panda.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		a = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				a[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		List<int[]> SP = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int fanIn = 0;
				for (int k = 0; k < 4; k++) {
					int r = i + dr[k];
					int c = j + dc[k];
					if (isValid(r, c)) {
						if (a[r][c] < a[i][j]) {
							fanIn++;
						}
					}
				}
				if (fanIn == 0) {
					SP.add(new int[] { i, j });
				}
			}
		}

		max_depth = 0;
		b = new int[N][N];
		for (int[] sp : SP) {
			b[sp[0]][sp[1]] = 1;
			dfs(sp[0], sp[1], 1);
		}

		System.out.println(max_depth);

		br.close();
	}

	static void dfs(int r, int c, int depth) {
		int fanOut = 0;
		for (int k = 0; k < 4; k++) {
			int nr = r + dr[k];
			int nc = c + dc[k];
			if (isValid(nr, nc) == false)
				continue;
			if (a[nr][nc] > a[r][c] && depth + 1 > b[nr][nc]) {
				b[nr][nc] = depth + 1;
				fanOut++;
				dfs(nr, nc, depth + 1);
			}
		}
		if (fanOut == 0) {
			max_depth = Math.max(depth, max_depth);
		}
	}

	static boolean isValid(int r, int c) {
		if (0 <= r && r < N && 0 <= c && c < N)
			return true;
		else
			return false;
	}

}