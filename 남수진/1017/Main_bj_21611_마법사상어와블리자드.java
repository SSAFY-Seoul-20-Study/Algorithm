package a1017;

import java.util.*;
import java.io.*;

public class Main_bj_21611_마법사상어와블리자드 {

	static int N, M, shark;
	static int[][] map;
	static final int[] dx = {0, -1, 1, 0, 0};
	static final int[] dy = {0, 0, 0, -1, 1};
	static final int next[] = { 0, 3, 4, 2, 1 };
	static int[][] xy;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		xy = new int[N * N][2];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		shark = N / 2;
		setXY();
		
		int ans = 0;
		for(int i  = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			
			blizzard(d, s);
			move();
			
			while(true) {
				int tmp = bomb();
				if(tmp == 0) break;
				else {
					ans += tmp;
					move();	
				}
			}
			change();
//			for(int k = 0; k < N; k++) {
//				System.out.println(Arrays.toString(map[k]));
//			}
		}
		System.out.println(ans);
		
	}
	

	static void blizzard(int d, int s) {
		
		int x = shark;
		int y = shark;		
		
		for(int i = 0; i < s; i++) {
			x += dx[d];
			y += dy[d];
			if(x < 0 || x >= N || y < 0 || y >= N) break;
			map[x][y] = 0;
		}
	}
	
	static void setXY() { 
		int x = shark, y = shark;
		int nx = 0, ny = 0;
		int curDir = 3;
		int d = 1;
		int num = 1;

		while (true) {
			for (int k = 0; k < 2; k++) {
				for (int i = 0; i < d; i++) {
					if (x == 0 && y == 0)
						return;
					nx = x + dx[curDir];
					ny = y + dy[curDir];
					xy[num][0] = nx;
					xy[num][1] = ny;
					num++;

					x = nx;
					y = ny;
				}
				curDir = next[curDir];
			}
			d++;

		}

	}

	static void move() {
		
		for (int i = 1; i < N * N; i++) {
			int x = xy[i][0];
			int y = xy[i][1];
			if (map[x][y] == 0) {
				int[] nxy = find(i);
				int nx = nxy[0];
				int ny = nxy[1];
				map[x][y] = map[nx][ny];
				map[nx][ny] = 0;
			}
		}
	}
	
	static int[] find(int num) {
		int nxy[] = new int[2];
		for (int i = num + 1; i < N * N; i++) {
			int cx = xy[i][0];
			int cy = xy[i][1];
			if (map[cx][cy] != 0) {
				nxy[0] = cx;
				nxy[1] = cy;
				break;
			}
		}
		return nxy;
	}

	static int bomb() {
		int x = shark, y = shark;
		int nx = 0, ny = 0;
		int curDir = 3;
		int d = 1;
		int cnt = 0;
		ArrayList<int []> list = new ArrayList<>();
		int ret = 0;
		
		while(true) {
			if(x == 0 && y == 0) break;
			
			for (int k = 0; k < 2; k++) {
				for (int i = 0; i < d; i++) {
					if (x == 0 && y == 0)
						return ret;
					nx = x + dx[curDir];
					ny = y + dy[curDir];
					
					if(map[x][y] != 0 && map[x][y] == map[nx][ny]) {
						if(cnt == 0) {
							cnt+= 2;
							list.add(new int[] {x, y});
							list.add(new int[] {nx, ny});
						} else {
							cnt++;
							list.add(new int[] {nx, ny});
						}
					}
					if(map[x][y] != map[nx][ny]) {
						if(cnt >= 4) {
							for(int[] cur : list) {
								int cx = cur[0], cy = cur[1];
								ret += map[cx][cy];
								map[cx][cy] = 0;
							}
						}
						cnt = 0;
						list.clear();
					}
					
					x = nx;
					y = ny;
				}
				curDir = next[curDir];
			}
			d++;
			
		}
		
		return ret;
	}
	
	static void change() {
		int copy[][] = new int[N][N];
		
		int cnt = 1;
		int num = 1;
		int cx = 0, cy = 0, nx = 0, ny = 0;
		for(int i = 1; i < N * N; i++) {
			cx = xy[i][0];
			cy = xy[i][1];
			
			if(map[cx][cy] == 0) break;
			if(i != N * N - 1) {
				nx = xy[i + 1][0];
				ny = xy[i + 1][1];
			}
			
			if(i != N * N - 1 && map[cx][cy] == map[nx][ny]) {
				cnt++;
			} else {
				if(num >= N * N) break;
				
				int x = xy[num][0];
				int y = xy[num][1];
				num++;
				
				if(num >= N * N) break;
				
				int x2 = xy[num][0];
				int y2 = xy[num][1];
				num++;
				copy[x][y] = cnt;
				copy[x2][y2] = map[cx][cy];
				cnt = 1;
			}
		}
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				map[i][j] = copy[i][j];
			}
		}
		
	}
}
