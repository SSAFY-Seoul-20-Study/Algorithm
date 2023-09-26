package a0914;

import java.util.*;
import java.io.*;

public class Main_boj_1113_수영장만들기 {
	
	static int N, M, result;
	static int max=Integer.MIN_VALUE; // 최대 높이
	static int[][] pool;
	static final int[] dx = {-1, 0, 1, 0};
	static final int[] dy = {0, -1, 0, 1};
	static boolean[][] v;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 입력 받기
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		pool = new int[N][M];
		
		for(int i=0; i<N; i++) {
			String input = br.readLine();
			for(int j=0; j<M; j++) {
				int m = input.charAt(j) - '0';
				pool[i][j] = m;
				if(max < m) max = m; // 최고 높이 구하기
			}
		}
		
		for(int h=1; h<=max; h++) {
			v = new boolean[N][M];
			for(int i=0; i<N; i++) {
				for(int j=0; j<M; j++) {
					if(pool[i][j] < h && !v[i][j]) {
						result += bfs(i, j, h);
					}
				}
			}
		}
		System.out.println(result);
		br.close();
	}
	
	private static int bfs(int x, int y, int h) {
		Queue<int[]> q = new ArrayDeque<>();
		
		int cnt = 1;
		boolean flag = true;
		
		q.offer(new int[] {x, y});
		v[x][y] = true;
		
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			for(int d=0; d<4; d++) {
				int nx = cur[0] + dx[d];
				int ny = cur[1] + dy[d];
				
				if(!isRange(nx, ny)) {
					flag = false;
					continue;
				}
				if(pool[nx][ny] < h && !v[nx][ny]) {
					q.offer(new int[] {nx, ny});
					v[nx][ny] = true;
					cnt++;
				}
			}
		}
		if(flag) return cnt;
		return 0;
	}
	
	private static boolean isRange(int x, int y) {
		if(0 <= x && x < N && 0 <= y && y < M) return true;
		return false;
	}
}
