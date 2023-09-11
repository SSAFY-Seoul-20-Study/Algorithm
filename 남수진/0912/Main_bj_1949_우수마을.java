package a0912;

import java.util.*;
import java.io.*;

public class Main_bj_1949_우수마을 {

	static int N;
	static List<Integer>[] g;
	static int[] p;
	static int[][] dp;
	static boolean[] v;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		g = new List[N + 1];
		p = new int[N + 1];
		dp = new int[N + 1][2];
		v = new boolean[N + 1];
		
		for(int i = 0; i <= N; i++) g[i] = new ArrayList<>();
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			p[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			
			g[from].add(to);
			g[to].add(from);
		}
	
		dfs(1);
		System.out.println(Math.max(dp[1][0], dp[1][1]));
		
		br.close();
	}
	
	static void dfs(int node) {
		v[node] = true;
		dp[node][0] = 0;
		dp[node][1] = p[node];
		
		for(int next : g[node]) {
			if(v[next]) continue;
			dfs(next);
			dp[node][0] += Math.max(dp[next][0], dp[next][1]);
			dp[node][1] += dp[next][0];
		}
		
	}
	

}
