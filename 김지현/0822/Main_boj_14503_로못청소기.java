package study_algo;

import java.util.*;
import java.io.*;

//14688KB	136ms
public class Main_boj_14503_로못청소기 {
	
	static int N, M;
	static int[][] map;
	static int r, c, dir;
	static int[] dx = {-1, 0, 1, 0}; // 북동남서
	static int[] dy = {0, 1, 0 ,-1};
	static int res;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_boj_14503.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 입력받기
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		
		st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		dir = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
//		for(int[] mm:map)System.out.println(Arrays.toString(mm));
		
		cleaning();
		System.out.println(res);
		br.close();
		
	}
	private static void cleaning() {
		while(true) {
			if(map[r][c] == 0) {
				map[r][c] = 2;
				res++;
			}			
			boolean flag = false; // 주변 4칸 중에 빈칸이 있는 지 확인.
			for(int d=0; d<4; d++) {
				if(map[r+dx[d]][c+dy[d]] == 0) flag = true;
			}
			if(flag) { // 빈 칸이 있는 경우
				if(dir == 0) dir = 3;
				else dir -= 1;
				
				if(map[r+dx[dir]][c+dy[dir]] == 0) {
					r += dx[dir];
					c += dy[dir];
				}
			}
			else { // 빈 칸이 없는 경우
				if(dir == 1) { // 현재 동쪽인 경우 
					if(map[r+dx[3]][c+dy[3]] != 1) {
						r += dx[3];
						c += dy[3];
					} else { // 뒤가 벽이면, 종료.
						break;
					}
				} else { // 현재 나머지 방향인 경우
					if(map[r+dx[Math.abs(dir-2)]][c+dy[Math.abs(dir-2)]] != 1) {
						r += dx[Math.abs(dir-2)];
						c += dy[Math.abs(dir-2)];
					} else{ // 뒤가 벽이면, 종료.
						break;
					}
				}
			}
		}
	}
}
