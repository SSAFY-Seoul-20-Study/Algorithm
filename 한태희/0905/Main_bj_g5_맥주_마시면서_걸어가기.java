import java.util.*;
import java.io.*;

/**
 * 유니온 파인드 문제.
 * 맥주를 20병을 들고 50칸을 걸을때마다 1병씩 소모한다는 것은,
 * 1000칸은 추가적인 보급 없이 걸어갈 수 있다는 것이다.
 * 
 * 따라서 두 편의점 사이의 거리가 1000 이하이라면, 두 편의점 사이는 이동 가능한 것이고
 * 1000을 초과한다면 이동 불가능하다.
 * 이걸 그래프 속 노드 두개의 연결이 되어있다, 연결이 없다로 생각할 수 있다.
 * 
 * 시작점부터 유니온 파인드를 하면서 집과 축제장이 같은 그래프 안에
 * 연결돼있는지 찾으면 되는 문제이다.
 */
public class Main_bj_g5_맥주_마시면서_걸어가기 {
	static int[] dr = { 0, 1, 0, -1 };
	static int[] dc = { 1, 0, -1, 0 };

	static int N;

	final static int INF = Integer.MAX_VALUE;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		testcase: for (int tc = 1; tc <= T; tc++) {

			// 정보 입력
			// 정수 3개가 들어있는 cord 배열을 사용한다.
			// 0:row 1:col 2:집에서의 거리 를 의미한다.
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

			// BFS를 이용해서 그래프를 탐색한다.
			Queue<int[]> q = new ArrayDeque<>();
			q.offer(startC);

			while (q.isEmpty() == false) {
				int[] cord = q.poll();

				if (getDist(cord, endC) <= 1000) {
					sb.append("happy\n");
					continue testcase;
				}

				for (int[] convC : convC_arr) {
					// 만약 그래프가 연결되어 있지 않거나,
					// 다음 좌표의 집과의 거리가 현재 좌표의 집과의 거리보다 작다면
					// 탐색할 필요가 없다.
					if (getDist(cord, convC) <= 1000 && convC[2] > cord[2]) {
						convC[2] = cord[2] + 1;
						q.offer(convC);
					}
				}
			}

			sb.append("sad\n");
		}

		System.out.print(sb);

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