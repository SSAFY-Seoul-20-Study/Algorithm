package a0925;

import java.io.*;
import java.util.*;

public class Main_boj_1939_중량제한2 {
	
	static int N, start, end, result;
	static List<int[]>[] map;
	static boolean[] v;
	
	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("res/input_boj_1939.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		// map 초기화
		map = new List[N+1];
		for(int i=1; i<=N; i++) {
			map[i] = new ArrayList<>();
		}
		
		int c_max = Integer.MIN_VALUE; // c 최대값 저장
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			map[a].add(new int[] {b, c});
			map[b].add(new int[] {a, c});
			
			c_max = Math.max(c_max, c);
		}
		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		
		// 이분탐색
		int left = 0;
		int right = c_max;
		while(left <= right) {
			int mid = (left + right) / 2;
			v = new boolean[N+1];
			boolean isGo = bfs(mid);
			if(isGo) {
				result = Math.max(mid, result);
				left = mid+1;
			} else {
				right = mid-1;
			}

		}	
		System.out.println(result);	
		br.close();
	}
	
	private static boolean bfs(int mid) {
		Queue<Integer> queue = new ArrayDeque<>();
		v[start] = true;
		queue.offer(start);
		while(!queue.isEmpty()) {
			int cur = queue.poll();
			if(cur == end) return true;
			for(int[] m : map[cur]) {
				int to = m[0];
				int cost = m[1];
				if(!v[to] && cost >= mid) {
					queue.offer(to);
					v[to] = true;
				}
			}
		}
		return false;
	}
}
