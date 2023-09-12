package main;

import java.io.*;
import java.util.*;

//재밌는 문제였습니다!
//DP의 가장 기본적인 문제인 개미병사 문제의 응용으로, 1차원 배열이 아닌 tree 구조에서 어떻게 n-1, n-2의 값들을 가져오냐 가 핵심인 문제였습니다.
//DFS와 DP의 응용으로, DFS에서 끝값부터 return을 사용해 dp를 갱신시켜 나가는 아이디어를 그대로 사용 가능 합니다.

public class Main_BJ_1949_우수마을 {

	public static int N;
	public static int[] population;
	public static ArrayList[] city;
	public static int[] dpfield;
	public static boolean[] visited;
	
	//DFS 구현 자체는 간단합니다. 더이상 리프 노드가 없다면 그 도시의 인구를 반환하되, n-2값을 저장해야하므로 1차원 배열로 return 시킵니다.
	public static int[] dfs(int now) {
		if(city[now].size()==0) {
			dpfield[now] = population[now];
			return new int[] {population[now],  0};
		}
		int tmp1 = 0;
		int tmp2 = 0;
		int[] tmpset;
		//1번 노드가 가장 꼭대기임을 기준으로, n-1, n-2 번 층의 dp 값을 더해서 tmp1, tmp2에 저장합니다.
		for(int i =0; i<city[now].size();i++) {
			int next = (int) city[now].get(i);
			if(visited[next]==true) continue;
			visited[next] = true;
			tmpset =  dfs(next);
			tmp1 += tmpset[0];
			tmp2 += tmpset[1];
		}
		//만약 현재 도시 + n-2 까지의 최댓값이 n-1까지의 최대값보다 크다면, 그 값을 현재 층 dp에 저장합니다.
		if((population[now]+tmp2) > tmp1) {
			dpfield[now] = population[now]+tmp2;
		}
		else {
			dpfield[now] = tmp1;
		}
		return new int[] {dpfield[now],tmp1};
	}
	
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine().trim());
		city = new ArrayList[N+1];
		population = new int[N+1];
		dpfield = new int[N+1];
		visited = new boolean[N+1];
		
		st = new StringTokenizer(br.readLine().trim());
		for(int i=0;i<N;i++) {
			population[i+1] = Integer.parseInt(st.nextToken());
			city[i+1] = new ArrayList<Integer>();
		}
		//노드가 depth 순서대로 주어지지 않으므로, 양방향 그래프 처럼 처리해줍니다.
		for(int i=0;i<N-1;i++) {
			st = new StringTokenizer(br.readLine().trim());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			city[a].add(b);
			city[b].add(a);
		}
		
		visited[1] = true;
		System.out.println(dfs(1)[0]);
	}
}
