package a0905;

import java.io.*;
import java.util.*;

public class Main_boj_16437_양구출작전 {

	static int N;
	static long[] island; // 마리 (늑대 : 음수로 넣기)
	static List<Integer>[] adj_list; // 인접리스트
	static int result;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_boj_16437.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		island = new long[N + 1];
		adj_list = new List[N + 1];
		for (int i = 1; i < N + 1; i++) {
			adj_list[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());

			char animal = st.nextToken().charAt(0);
			long cnt = Integer.parseInt(st.nextToken());
			if (animal == 'W') island[i + 2] = -1 * cnt;
			else island[i + 2] = cnt;

			int parent = Integer.parseInt(st.nextToken());
			adj_list[parent].add(i+2);
		}

		dfs(1, 0);
		
		System.out.println(island[1]);
		br.close();
	}

	private static void dfs(int now, int parent) {
		for (int nx : adj_list[now]) {
			dfs(nx, now); // 자식 노드로 DFS 수행
		}
		
		// 모든 자식 노드를 탐색 후
		if(island[now] > 0) { // 양이 있다면
			island[parent] += island[now]; // 부모 노드로 양 보냄.
		}
	}
}

// https://jooona.tistory.com/192 참고
