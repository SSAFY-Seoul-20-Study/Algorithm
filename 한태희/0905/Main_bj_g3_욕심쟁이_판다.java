import java.util.*;
import java.io.*;

/**
 * DP + DFS 를 이용한 아이디어.
 * 임의의 위치 k와, k의 상하좌우 위치가 있을 때,
 * k위치에서 판다가 생존 가능한 최대 시간은
 * ( (상하좌우 위치 중 k보다 값이 큰 위치의) 생존 가능한 최대 시간 중)  최댓값
 * 임을 알 수 있다.
 * 
 * 2차원 배열의 각 점에서 생존 가능한 최대 시간을 메모이제이션 한다면,
 * 위의 점화식을 빠르게 구할 수 있다.
 */
public class Main_bj_g3_욕심쟁이_판다 {
	static int N;
	static int[][] a, b;

	static int[] dr = { 0, 1, 0, -1 };
	static int[] dc = { 1, 0, -1, 0 };

	public static void main(String args[]) throws Exception {
		//N의 크기와 각 위치의 대나무의 개수 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		a = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				a[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		//모든 점을 순회하면서 판다가 생존 가능한 최댓값을 구한다.
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

	/**
	 * 만약 b 배열 (메모이제이션 배열)이 0이 아닌 값이라면,
	 * 그 값이 해당 위치에서의 판다의 최대 생존 시간임으로 바로 리턴한다.
	 * 아직 b 배열이 초기 상태라면, 탐색이 필요함으로
	 * 4방향을 탐색해서 현재 위치보다 대나무가 더 많은 위치의 
	 * 판다의 생존 시간 중 가장 값이 큰 위치를 찾는다.
	 */
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