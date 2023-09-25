package a0926;

import java.util.*;
import java.io.*;

public class Main_bj_1939_중량제한 {
	
	static int N, M, first, end;
	static List<int []>[] g;
	static boolean[] v;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		g = new List[N + 1];
		v = new boolean[N + 1];
		
		for(int i = 0; i< N + 1; i++) g[i] = new ArrayList<>();
		
		int left = Integer.MAX_VALUE;
		int right = 0;
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			
			g[A].add(new int[] {B, C});
			g[B].add(new int[] {A, C});
			left = Math.min(left, C);
			right = Math.max(right, C);
		}

		st = new StringTokenizer(br.readLine());
		first = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		
		int result = 0;
		while(left <= right) {
			int mid = (left + right) / 2; 
			
			for(int i = 0; i < N + 1; i++) v[i] = false;
			
			if(bfs(mid)) {
				left = mid + 1;
				result = mid;
			} else {
				right = mid - 1;
			}
		}
		System.out.println(result);
	}

	static boolean bfs(int mid) {
		
		ArrayDeque<Integer> q = new ArrayDeque<>();

		q.offer(first);
		v[first] = true;
		
		while(!q.isEmpty()) {
			int now = q.poll();

			if(now == end) {
				return true;
			}
			
			for(int[] i : g[now]) {
				if(v[i[0]] || i[1] < mid) continue;
				v[i[0]] = true;
				q.offer(i[0]);
			}
		}
		return false;
	}
}