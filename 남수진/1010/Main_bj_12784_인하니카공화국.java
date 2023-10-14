package a1010;

import java.util.*;
import java.io.*;

public class Main_bj_12784_인하니카공화국 {

	static int N, M;
	static List<int[]>[] g;
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for(int tc  = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			g = new List[N + 1];
			for(int i = 1; i <= N; i++) {
				g[i] = new ArrayList<>();
			}
			
			for(int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				int weight = Integer.parseInt(st.nextToken());
				
				g[from].add(new int[] {to, weight});
				g[to].add(new int[] {from, weight});
			}
			
			int ans = dfs(1, -1, Integer.MAX_VALUE);
			
			sb.append(ans == Integer.MAX_VALUE ? 0 : ans).append("\n");
		}
		System.out.println(sb.toString());
	}
	
	static int dfs(int now, int prev, int weight) {
		int sum = 0;
		for(int[] next : g[now]) {
			if(next[0] != prev) {
				sum += dfs(next[0], now, next[1]);
			}
		}
		if(sum == 0) {
			return weight;
		}
		return Math.min(sum, weight);
	}

}
