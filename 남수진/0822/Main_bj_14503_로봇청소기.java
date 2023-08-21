package a0822.study;

import java.util.*;
import java.io.*;

public class Main_bj_14503_로봇청소기 {
	static int N, M, cnt = 0, firstX, firstY, robotDir; // 방의 크기, 청소한 칸의 개수, 로봇 위치와 방
	static int[][] map; // 방 
	static final int[] dx = {-1, 0, 1, 0}; // 반시계방향 회전 기준
	static final int[] dy = {0, 1, 0, -1};

	public static void main(String[] args ) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		cnt = 0;
		map = new int[N][M];
		
		st = new StringTokenizer(br.readLine());
		
		// 로봇의 위치 입력
		firstX = Integer.parseInt(st.nextToken());
		firstY = Integer.parseInt(st.nextToken());	
		robotDir = Integer.parseInt(st.nextToken());
		
		// 방 입력
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j ++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		clean(firstX, firstY, robotDir);
		
		System.out.println(cnt);
		
		br.close();
	}
	
	// 청소 함수
	static void clean(int x, int y, int dir) {
		
		if(map[x][y] == 0) { // 청소가 되지 않았으면
			map[x][y] = 2; // 청소 후 
			cnt++; // 값 증가 
		}

		for(int i = 0; i < 4; i++) {
			// 0 -> 0 1 -> 3 2 -> 2 3 -> 1
			dir = (dir + 3) % 4;
			
			int nx = x + dx[dir];
			int ny = y + dy[dir];
			
			if(map[nx][ny] > 0) continue; // 방을 벗어나면 continue 
			
			if(map[nx][ny] == 0) { // 청소되지 않으면 해당 위치에서 다시 청소 실시
				clean(nx, ny, dir);
				return;
			}
		}
		// 방향을 모두 돌아도 청소할 곳이 없는 경우 
		if(map[x - dx[dir]][y - dy[dir]] == 1) { //벽이면 반환 -> 더이상 탐색 불가능
			return;
		} else {
			map[x - dx[dir]][ y - dy[dir]] = 3; // 뒤로 후진
			clean(x - dx[dir], y - dy[dir] , dir); // 후진 후 재탐색
		}
		
	}

}
