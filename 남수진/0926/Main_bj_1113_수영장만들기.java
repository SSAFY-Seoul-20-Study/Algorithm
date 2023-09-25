package a0926;

import java.util.*;
import java.io.*;

public class Main_bj_1113_수영장만들기 {

	static int N, M;
	static final int[] dx = {1, 0, -1, 0};
	static final int[] dy = {0, 1, 0, -1};
	static int[][] map;
	static boolean[][] v;
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		
		int max = 0;
		for(int i = 0; i < N; i++) {
			String str = br.readLine();
			for(int j = 0; j < M; j++) {
				map[i][j] = (int) str.charAt(j) - '0';
				max = Math.max(max, map[i][j]);
			}
		}
		
		int ans = 0;
		for(int h = 1; h <= max; h++) {
			v = new boolean[N][M];
			for(int i = 0; i < N ; i++) {
				for(int j = 0; j < M; j++) {
					if(map[i][j] < h && !v[i][j]) {
						ans += bfs(i, j, h);
					}
				}
			}

		}
		System.out.println(ans);
				
	}
	
	static int bfs(int x, int y, int height) {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		
		int cnt = 1;
		q.offer(new int[] {x, y});
		v[x][y] = true;
		boolean flag = true;
		
		while(!q.isEmpty()) {
			int cx = q.peek()[0];
			int cy = q.poll()[1];
			
			for(int i = 0; i < 4; i++) {
				int nx = cx + dx[i];
				int ny = cy + dy[i];
				
				if(nx < 0 || nx >= N || ny < 0 || ny >= M ) {
					flag = false;
					continue;
				}
				
				if(map[nx][ny] < height && !v[nx][ny]) {
					q.offer(new int[] {nx, ny});
					v[nx][ny] = true;
					cnt++;
				}
			}
		}

		if(flag) return cnt;
		else return 0;
	}
	

}
