import java.util.*;
import java.io.*;

/**
 * 그룹만들기 + 구현 문제
 * 
 * 높이가 똑같은 구역을 그룹으로 묶으면, 두 그룹간의 고저차를 이용해서 물이 고여있는 양을 구할 수 있다.
 * 주변에 자신보다 낮은 구역이 있으면 물이 흘러내리기 때문에 물이 고이지 않고,
 * 주변에 자신보다 낮은 구역이 없으면, 주변 구역 중 가장 높이가 낮은 구역과의 차이만큼 물이 고인다.
 * 
 * 위의 규칙대로 물을 채우면, 물이 고인 곳은 그만큼 높이가 높아진다고 볼 수 있다.
 * 물이 고이면 주변 구역 중 가장 높이가 낮은 구역과 동일한 높이로 병합된다.
 * 병합 이후, 물이 고이는 과정을 다시 진행한다.
 * 이런 과정으로 모든 구역에 물이 고이지 않을 때까지 반복하면 된다.
 */
public class Main_bj_g1_1113_수영장_만들기 {
	static int N, M;
	static int[][] a, g;
	static int minH, groupSize;

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 문제의 조건에 따라서, 배열의 4방향 모서리에 1 굵기의 0 값 패딩을 준다.
		// 이 0은 가장 낮은 높이이며, 물을 무한히 흡수한다. (수위가 높아지지 않는다.)
		a = new int[N + 2][M + 2];

		// 높이를 나타내는 배열 a 값 입력
		for (int i = 1; i < N + 1; i++) {
			String str = br.readLine();
			for (int j = 1; j < M + 1; j++) {
				a[i][j] = str.charAt(j - 1) - '0';
			}
		}

		int res = 0; // 결과 값

		// 더이상 물이 고이지 않을 때까지 반복
		while (true) {
			int gcnt = 0;
			// 그룹을 나타내는 배열 g
			g = new int[N + 2][M + 2];
			// hash[gcnt] = water (w는 고인 물의 높이, 즉 hash는 각 그룹에 고인 물의 높이를 나타냄)
			int[] hash = new int[2501];

			// 설명에선 병합 어쩌고 했었지만, 실제 구현은 처음 병합해야한다는 것을 모르고 구현을 시작해서,
			// 그 코드에서 얼기설기 붙히다가 각 반복마다 그룹을 새로 만드는 것으로 구현
			// 배열의 최대 크기가 2500칸이고, 높이는 1~9사이의 자연수이므로 충분히 가능.
			for (int i = 1; i < N + 1; i++) {
				for (int j = 1; j < M + 1; j++) {
					if (g[i][j] == 0) {
						int groupH = a[i][j];
						minH = 10;
						groupSize = 0;
						makeGroup(i, j, ++gcnt);
						int w = (minH - groupH);
						if (w > 0) {
							hash[gcnt] = w;
						}
					}
				}
			}

			int localres = 0;

			// 각 칸을 순회하면서 res와 a[i][j]에 고인 물의 높이를 더해준다.
			for (int i = 1; i < N + 1; i++) {
				for (int j = 1; j < M + 1; j++) {
					int key = g[i][j];
					a[i][j] += hash[key];
					localres += hash[key];
				}
			}

			// 만약 이번 반복에 고인 물이 없으면 반복문을 탈출한다.
			if (localres == 0)
				break;

			res += localres;
		}

		System.out.println(res);

		br.close();
	}

	static void makeGroup(int r, int c, int gcnt) {
		g[r][c] = gcnt;
		groupSize++;
		for (int k = 0; k < 4; k++) {
			int nr = r + dr[k];
			int nc = c + dc[k];
			if (g[nr][nc] == 0 && a[r][c] == a[nr][nc]) {
				makeGroup(nr, nc, gcnt);
			} else if (g[nr][nc] != gcnt) {
				if (minH > a[nr][nc]) {
					minH = a[nr][nc];
				}
			}
		}
	}
}