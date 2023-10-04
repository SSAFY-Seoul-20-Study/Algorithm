import java.util.*;
import java.io.*;

/**
 * -이분 탐색 문제
 * 
 * 1. 문제를 결정 문제로 변환:
 * - 무게 K가 있을 때, 중량 제한이 K 미만인 다리는 지나갈 수 없다.
 * - 따라서 무게가 K인 짐을 수송 가능하다는 것은 중량 제한이 K 이상인 다리만을 이용하여 시작점에서 끝점으로 이동 가능하다는
 * 것이다.(같은 그래프에 있다는 것이다.)
 * - K보다 작은 무게의 짐을 수송가능한 다리는 무게 K를 수송하는 다리를 포함하므로, 정수 K가 수송 가능하다면, 1<=P<K 인 임의의
 * 무게 P도 수송 가능하다.
 * 
 * 2. 결정 문제를 통해 이분 탐색 성립:
 * - 임의의 무게 K가 주어졌을 때,
 * - 무게가 K인 짐을 수송 가능하면, 답은 K 이상 1,000,000,000 이하 사이에 있다.
 * - 무게가 K인 짐이 수송 불가능하면, 답은 1이상 K미만 사이에 있다.
 * 이 식을 이분 탐색을 통해서 찾는다.
 */
public class Main_bj_g3_1939_중량_제한 {
	static int N, M;
	static int start, end;
	static ArrayList<int[]>[] list;// 그래프 연결 정보
	static boolean[] v;

	final static int INF = Integer.MAX_VALUE;

	static int ans;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		list = new ArrayList[N + 1];

		int left = INF;
		int right = 0;

		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			left = Math.min(left, z);
			right = Math.max(right, z);

			if (list[a] == null)
				list[a] = new ArrayList<>();
			list[a].add(new int[] { b, z });
			if (list[b] == null)
				list[b] = new ArrayList<>();
			list[b].add(new int[] { a, z });
		}

		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		ans = 0;

		// 이분탐색을 재귀식이 아니라 반복문으로 짰더니 통과했습니다. 재귀식은 생각보다 깊이 못들어가니 항상 깊이를 생각해보고 가능할지
		// 미리따져봅시다..
		while (left <= right) {
			int mid = (left + right) / 2;
			v = new boolean[N + 1];
			if (check(mid)) {
				left = mid + 1;
				ans = mid;
			} else {
				right = mid - 1;
			}
		}

		System.out.println(ans);

		br.close();
	}

	/**
	 * 크기가 k 이상인 다리들만 사용하여 start에서 end로 건너갈 수 있는지 확인한다.
	 * =유니온 파인드와 동치
	 * bfs로 품
	 */
	static boolean check(int k) {
		v = new boolean[N + 1];
		v[start] = true;
		Queue<Integer> q = new ArrayDeque<>();
		q.offer(start);

		while (q.isEmpty() == false) {
			int now = q.poll();
			ArrayList<int[]> edges = list[now];

			if (edges == null)
				continue;

			for (int info[] : edges) {
				int next = info[0];
				int weight = info[1];

				if (v[next] == false && weight >= k) {
					if (next == end) {
						return true;
					}
					q.offer(next);
					v[next] = true;
				}
			}
		}

		return false;
	}

}