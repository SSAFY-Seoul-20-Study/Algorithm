package a0912;

import java.util.*;
import java.io.*;

public class Main_boj_1949_우수마을 {

	static int N;
	static int[] p;
	static List<Integer>[] tree;
	static int[][] dp;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("res/input_boj_1949.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		p = new int[N+1];
		tree = new List[N+1];
		dp = new int[N+1][2];
		
		st = new StringTokenizer(br.readLine());
		for(int i=1; i<=N; i++) {
			p[i] = Integer.parseInt(st.nextToken());
			tree[i] = new ArrayList<>();
		}
//		System.out.println(Arrays.toString(p));
		
		for(int i=0; i<N-1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			tree[a].add(b);
			tree[b].add(a);
		}
		
		dfs(1, 0);
		System.out.println(Math.max(dp[1][0], dp[1][1]));
		br.close();
		// 1 ~ N번 마을(트리)
		// N-1개 길
		// 양방향
		
		// 우수마을 선정
			// 마을 주민 수 총 합을 최대로
			// 만일 두 마을 인접 -> 하나만 우수 마을
			// 우수마을X -> 우수마을이랑 인접해야함

	}
	
	private static void dfs(int now, int parent) {
		for(int child : tree[now]) {
			if(child == parent) continue;
			dfs(child, now);
			dp[now][0] += Math.max(dp[child][0], dp[child][1]);
			dp[now][1] += dp[child][0];
		}
		dp[now][1] += p[now];
	}
}
