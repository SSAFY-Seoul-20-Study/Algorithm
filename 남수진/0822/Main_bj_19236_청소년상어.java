package a0822.study;

import java.util.*;
import java.io.*;

// 물고기 클래스
class Fish{
	int x, y, num, dir;
	
	Fish(int x, int y, int num, int dir) {
		this.x = x;
		this.y = y;
		this.num = num;
		this.dir = dir;
	}
}

public class Main_bj_19236_청소년상어 {

	// 방향 정보
	static final int[] dx = {0, -1, -1, 0, 1, 1, 1, 0, -1};
	static final int[] dy = {0, 0, -1, -1, -1, 0, 1, 1, 1};
	static int max = 0; // 물고기 최댓값
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Fish[] order = new Fish[17]; // 순서에 따른 정보 저장
		int[][] map = new int[4][4]; // 물고기 지도

		for(int i = 0; i < 4; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 4; j++) {
				
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				map[i][j] = a;
				order[a] = new Fish(i, j, a, b);
			}
		}
		
		// 상어 객체 생성
		Fish shark = new Fish(0, 0, map[0][0], order[map[0][0]].dir);
		
		order[map[0][0]].dir = -1;
		map[0][0] = 0;
		shark(map, order, shark, shark.num);
		
		System.out.println(max);
	}

	static void shark(int[][] map, Fish[] order, Fish shark, int sum) {
		if(max < sum) { // 값이 크면 업데이트
			max = sum;
		}
		
		// 물고기 이동
		moveFish(map, order, shark);
		
		// 상어 이동
		for(int i = 1; i <= 3; i++) { // 최대 3번 이동 가능
			// 상어 위치 이동
			int nx = shark.x + dx[shark.dir] * i;
			int ny = shark.y + dy[shark.dir] * i;
			
			if(nx < 0 || nx >= 4 || ny < 0 || ny >= 4 || map[nx][ny] == 0) continue; 
			
			// 범위 안에 있을 경우
			// 값 복사
			int[][] copyMap = copyMap(map);
			Fish[] copyOrder = copyOrder(order);
			
			int x = copyOrder[copyMap[nx][ny]].x;
			int y = copyOrder[copyMap[nx][ny]].y;
			int num = copyOrder[copyMap[nx][ny]].num;
			int dir = copyOrder[copyMap[nx][ny]].dir;
			
			copyOrder[copyMap[nx][ny]].dir = -1;
			copyMap[nx][ny] = 0;
			
			shark(copyMap, copyOrder, new Fish(x, y, num, dir), sum + num);
		}
	}
	
	// 물고기 이동
	static void moveFish(int[][] map, Fish[] order, Fish shark) {
		for(int i = 1; i <= 16; i++) {
			if(order[i].dir == -1) continue; // 이미 먹힌 물고기면 제외
			
			// 물고기의 x, y, dir
			int x = order[i].x;
			int y = order[i].y;
			int dir = order[i].dir;
			
			find: while(true) {
				// 물고기 이동
				int nx = x + dx[dir];
				int ny = y + dy[dir];
					
				// 상어가 있거나 범위를 벗어나는 경우
				if((nx == shark.x && ny == shark.y) || (nx < 0 || nx >= 4 || ny < 0 || ny >= 4) ) {
					// 방향 변경
					dir++;
					if(dir == 9) dir = 1;
				}
				else if(map[nx][ny] != 0) { // 이동할 방향에 물고기가 있는 경우
					int tmp = map[nx][ny]; // 이동 방향 물고기의 number

					// 현재 순서에 x, y, dir 업데이트
					order[i].x = nx;
					order[i].y = ny;
					order[i].dir = dir;
								
					// 이동방향 num에 x, y좌표 업데이트
					order[tmp].x = x;
					order[tmp].y = y;

					// map 값 업데이트
					map[nx][ny] = map[x][y];
					map[x][y] = tmp;
						
					break find; // 종료
				} else { // 아무것도 없는 경우
					map[nx][ny] = i;
					// 값 업데이트
					order[i].x = nx;
					order[i].y = ny;
					order[i].dir = dir;
					
					map[x][y] = 0; // 기존 자리 초기화

					break find;
				}
			}
			
		}
	}
	static int[][] copyMap(int[][] map) {
		int[][] copy = new int[4][4];
		for(int i = 0; i < 4; i++ ) {
			for(int j = 0; j < 4; j++) {
				copy[i][j] = map[i][j];
			}
		}
		return copy;
	}
	
	static Fish[] copyOrder(Fish[] order) {
		Fish[] copy = new Fish[17];
		for(int i = 1; i < 17; i++ ) {
			Fish copyF = order[i];
			copy[i] = new Fish(copyF.x, copyF.y, copyF.num, copyF.dir);
		}
		return copy;
	}

}
