package a0811.study;

import java.util.*;
import java.io.*;

public class Main_bj_17471_게리맨더링 {

	static int N;
	static int[] p;
	static int[][] graph;
	static int[] AB;
	static int v, vTrue;
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine()); // 구역의 개수
		
		p = new int[N + 1]; // 인구 수 저장
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) p[i] = Integer.parseInt(st.nextToken()); // 인구 수 입력
		
		
		graph = new int[N + 1][N + 1]; // 인접한 곳 저장
		for(int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			graph[i][0] = Integer.parseInt(st.nextToken()); // 첫번째에 size 저장
			
			for(int j = 1; j <= graph[i][0]; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken()); // 두번째부터 인접한 구역저장
			}
		}
		
		AB = new int[N + 1]; // 선거구 나누기
		vTrue = (1 << (N + 1)) - 2;
		separate(1);
		
		if(min == Integer.MAX_VALUE) min = -1;
		
		System.out.println(min);
		
		br.close();
	}
	
	// 선거구 분리 - 부분집합
	static void separate(int cnt) {
		if(cnt == N + 1) {
			int sumA = 0, sumB = 0;
			v = 0;
			// 1인 구역 sum 
			for(int i = 1; i <= N; i++) {
				if(AB[i] == 1) {
					sumA = bfs(i, 1);
					break;
				}
			}
			
			// 2인 구역 sum
			for(int i = 1; i <= N; i++) {
				if(AB[i] == 2) {
					sumB = bfs(i, 2);
					break;
				}
			}
			
			if(sumA == 0 || sumB == 0) return;
			
			if((v & vTrue) != vTrue) return;
			min = Math.min(min, Math.abs(sumA - sumB));
			
			return;
		}
		
		
		AB[cnt] = 1;
		separate(cnt + 1);
		AB[cnt] = 2;
		separate(cnt + 1);	
	}
	
	
	static int bfs(int place, int state) {
		
		ArrayDeque<Integer> q = new ArrayDeque<>();
		
		q.offer(place);
		v = (1<< place) | v;
		int sum = p[place];
		
		while(!q.isEmpty()) {
			int current = q.poll();
			
			for(int i = 1; i <= graph[current][0]; i++) {
				int find = graph[current][i];
				int bit = 1 << find;
				
				if(AB[find] == state && (bit & v) != bit) {
					q.offer(find);
					sum += p[find];
					
					v = bit | v;
				}
			}
		}
		return sum;
	}

}
