import java.util.*;
import java.io.*;

public class Main_bj_g3_1939_중량_제한 {
	static int N, M;
	static int[] a;// 최대 유량
	static List<int[]>[] g;// 그래프 연결 정보

	final static int INF = Integer.MAX_VALUE;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		a = new int[N + 1];
		g = new List[N + 1];
		for (int i = 1; i < N + 1; i++) {
			g[i] = new ArrayList<>();
		}

		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			g[a].add(new int[] { b, z });
			g[b].add(new int[] { a, z });
		}

		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		Queue<int[]> q = new ArrayDeque<>(); // {선택 노드, 현재 유량}
		q.offer(new int[] { start, INF });
		a[start] = INF;

		while (q.isEmpty() == false) {
			int[] info = q.poll();
			int now = info[0];
			int flow = info[1];

			for (int[] edge : g[now]) {
				int next = edge[0];
				int weight = edge[1];
				int bottleneck = Math.min(flow, weight);
				if (a[next] < bottleneck) {
					a[next] = bottleneck;
					q.offer(new int[] { next, bottleneck });
				}
			}
		}

		int ans = a[end];
		System.out.println(ans);

		br.close();
	}

}