import java.util.*;
import java.io.*;

/*
SSAFY 알고리즘 시간에 풀어봤던 다이나믹프로그래밍 응용과 동일한 문제. 다만, 전에는 O(n^3) 알고리즘으로 풀었었는데, 이번 문제는 N이 너무 커서 그렇게 풀면 시간 초과가 발생한다.
이 문제를 O(n^2)으로 줄이는 아이디어는 각 DP 탐색 단계마다 이전 단계를 참조할 때, 가장 큰 값과 두번째로 큰 값만 알면 된다는 것이다.
가장 큰 값과 동일한 열의 다음 행은 두번째로 큰 값을 더하고, 나머지 경우는 가장 큰 값을 더하면 된다.
 */

public class Main_bj_g3_26093_고양이_목에_리본_달기 {

	final static int INF = Integer.MAX_VALUE;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		int[][] arr = new int[N][K];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < K; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int x = 1; x < N; x++) {
			int first = 0;
			int f_idx = -1;
			int second = 0;
			for (int k = 0; k < K; k++) {
				int n = arr[x - 1][k];
				if (n > first) {
					second = first;
					first = n;
					f_idx = k;
				} else if (n > second) {
					second = n;
				}
			}

			for (int k = 0; k < K; k++) {
				if (k != f_idx) {
					arr[x][k] += first;
				} else {
					arr[x][k] += second;
				}
			}

		}

		int max = 0;
		for (int i = 0; i < K; i++) {
			max = Math.max(max, arr[N - 1][i]);
		}

		System.out.println(max);

		br.close();

	}

}