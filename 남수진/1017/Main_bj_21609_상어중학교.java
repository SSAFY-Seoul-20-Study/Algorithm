package a1017;

import java.util.*;
import java.io.*;

public class Main_bj_21609_상어중학교 {

	static int N, M;
	static int[][] map;
	static boolean[][] v;
	static final int[] dx = {-1 ,0, 1, 0};
	static final int[] dy = {0, -1, 0, 1};
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		
		
		for(int i  = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int ans = 0;
		
		while(true) {
			int max = 0, maxCnt = 0;
			int maxX = -1, maxY = -1;
			v = new boolean[N][N];
			
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(map[i][j] > 0 && !v[i][j]) {
						int[] tmp = findBlock(i, j, map[i][j]);
						if(tmp[0] <= 1) continue;
						if(tmp[0] > max) {
							max = tmp[0];
							maxCnt = tmp[1];
							maxX = i;
							maxY = j;
						} else if(tmp[0] == max) {
							if(tmp[1] > maxCnt) {
								maxCnt = tmp[1];
								maxX = i;
								maxY = j;
							} else if(tmp[1] == maxCnt) {
								if(i > maxX) {
									maxX = i;
									maxY = j;
								} else if(i == maxX) {
									if(j > maxY) {
										maxY = j;
									}
								}
							}
						}
					}
				}
			}
			if(max == 0) break;
		
			removeBlock(maxX, maxY, map[maxX][maxY]);
			ans += Math.pow(max, 2);
			gravity();
			
			rotate();
			gravity();
		}
		
		System.out.println(ans);
	}
	
	static int[] findBlock(int x, int y, int idx) {
		
		ArrayDeque<int[]> q = new ArrayDeque<>();
		
		int[] ret = new int[2];
		
		q.offer(new int[] {x,y});
		v[x][y] = true;
		ret[0] = 1;
		
		ArrayList<int[]> list = new ArrayList<>();
		
		while(!q.isEmpty()) {
			int cx = q.peek()[0];
			int cy = q.poll()[1];
			
			for(int i = 0; i < 4; i++) {
				int nx = cx + dx[i];
				int ny = cy + dy[i];
				
				if(nx < 0 || nx >= N || ny < 0 || ny >= N ) continue;
				if(v[nx][ny]) continue;
				if(map[nx][ny] != idx && map[nx][ny] != 0) continue;
				
				v[nx][ny] = true;
				q.offer(new int[] {nx, ny});
				ret[0]++;
				if(map[nx][ny] == 0) {
					ret[1]++;
					list.add(new int[] {nx, ny});
				}
				
			}
		}
		
		while(!list.isEmpty()) {
			int[] cur = list.remove(0);
			v[cur[0]][cur[1]] = false;
		}
		
		return ret;
		
	}
	
	static void removeBlock(int x, int y, int idx) {
		
		ArrayDeque<int[]> q = new ArrayDeque<>();
		
		q.offer(new int[] {x,y});
		map[x][y] = -2;
		
		while(!q.isEmpty()) {
			int cx = q.peek()[0];
			int cy = q.poll()[1];
			
			for(int i = 0; i < 4; i++) {
				int nx = cx + dx[i];
				int ny = cy + dy[i];
				
				if(nx < 0 || nx >= N || ny < 0 || ny >= N ) continue;
				
				if(map[nx][ny] == idx || map[nx][ny] == 0) {
					map[nx][ny] = -2;
					q.offer(new int[] {nx,ny});
				}
			}
		}
	}
	
	static void gravity() {
		
		ArrayDeque<Integer> q = new ArrayDeque<>();
		
		for(int j = 0; j < N; j++) {
			int point = N - 1;
			for(int i = N - 1; i >= 0; i--) {
				if(map[i][j] >= 0) {
					q.offer(map[i][j]);
					map[i][j] = -2;
				} else if(map[i][j] == -1) {
					while(!q.isEmpty()) {
						map[point--][j] = q.poll();
					}
					point = i - 1;
				}
			}

			while(!q.isEmpty()) {
				map[point--][j] = q.poll();
			}
		}
	}
	
	static void rotate() {
		
		int[][] copy = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				copy[i][j] = map[j][N - 1 - i];
			}
		}
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				map[i][j] = copy[i][j];
			}
		}
	}
}
