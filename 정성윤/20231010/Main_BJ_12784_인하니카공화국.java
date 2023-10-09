package main;

import java.io.*;
import java.util.*;

//문제 조건에서 MST라고 주어졌기에 굉장히 쉬운 문제
//MST가 아니었다면....난이도가 몇배는 뛰었을거라 생각한다 끔찍

public class Main_BJ_12784_인하니카공화국 {
	public static int T, N, M;
	public static ArrayList<int[]>[] al;
	public static boolean[] visited;
	public static int[] dp;
	
	
	//사실 전형적인 dfs문제. 리프 노드일때와 아닐때만 구분해서 dp 값을 넣어주면 되는 문제이다.
	public static boolean dfs(int nowisland,int dynamite) {
		//섬이 두개밖에 없을경우, 1번 섬에 대한 예외처리를 해주지 않으면 다음 섬이 있음에도 그대로 종료된다.
		//루트 섬 (1번)에 대한 엣지케이스를 꼭 잊지 말자
		if(al[nowisland].size()==1 && nowisland!=1) {
			dp[nowisland] = dynamite;
			return true;
		}
		int tmp = 0;
		for(int i=0;i<al[nowisland].size();i++) {
			int[] nextinfo = al[nowisland].get(i);
			int nextisland = nextinfo[0];
			int cost = nextinfo[1];
			if(visited[nextisland]==true) continue;
			visited[nextisland] = true;
			if(dfs(nextisland,cost)) {
				tmp+=cost;
			}
			else {
				tmp+=dp[nextisland];
			}
		}
		if(dynamite > tmp) {
			dp[nowisland] = tmp;
		}
		else {
			dp[nowisland] = dynamite;
		}
		return false;
	}
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=0;t<T;t++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			al = new ArrayList[N+1];
			visited = new boolean[N+1];
			dp = new int[N+1];
			
			for(int i=1;i<=N;i++) {
				al[i] = new ArrayList<>();
			}
			for(int i=0;i<M;i++) {
				st = new StringTokenizer(br.readLine());
				int is1 = Integer.parseInt(st.nextToken());
				int is2 = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken());
				al[is1].add(new int[] {is2,cost});
				al[is2].add(new int[] {is1,cost});
			}
			
			visited[1]  = true;
			dp[1] = Integer.MAX_VALUE;
			dfs(1,dp[1]);
			System.out.println(dp[1]);
		}
		
	}
}
