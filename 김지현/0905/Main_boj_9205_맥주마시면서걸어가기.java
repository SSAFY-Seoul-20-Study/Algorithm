package local_algo;

import java.util.*;
import java.io.*;

// 	14844KB	160ms
public class Main_boj_9205_맥주마시면서걸어가기 {
	
	static int N;
	static int[] start=new int[2]; // 상근이네 집.
	static int[][] p; // 편의점 배열.
	static int[] end = new int[2]; // 락 페스티벌.
	static boolean[] v; // 편의점 배열(p)의 방문배열.

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		// 입력받기 
		int T = Integer.parseInt(br.readLine());
		for(int tc=0; tc<T; tc++) {
			N = Integer.parseInt(br.readLine());
			p = new int[N][2];
			v = new boolean[N];
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<2; i++) {
				start[i] = Integer.parseInt(st.nextToken());
			}
			// 편의점 입력.
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<2; j++) {
					p[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			// 락 페스티벌 입력.
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<2; i++) {
				end[i] = Integer.parseInt(st.nextToken());
			}
			
			if(bfs()) sb.append("happy").append("\n");
			else sb.append("sad").append("\n");
		}
		System.out.println(sb);
		br.close();
	
	}
	private static boolean bfs() {
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(start);
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			if(isInDist(cur, end)) {
				return true;
			}
			for(int i=0; i<N; i++) {
				if(!v[i] && isInDist(cur, p[i])) {
					q.offer(p[i]);
					v[i] = true;
				}
			}
		}
		return false;
	}

	private static boolean isInDist(int[] now, int[] next) {
		int dist = Math.abs(now[0]-next[0])+Math.abs(now[1]-next[1]);
		if(dist <= 1000) return true;
		return false;
	}

}
