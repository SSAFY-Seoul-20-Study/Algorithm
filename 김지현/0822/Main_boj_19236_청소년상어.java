package study_algo;

import java.util.*;
import java.io.*;

// 14248KB	128ms
public class Main_boj_19236_청소년상어 {
	
	private static class Fish{
		int x;
		int y;
		int dir;
		
		public Fish(int x, int y, int dir) {
			super();
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}
	
	static int ans;
	static int[][] board = new int[4][4]; // 상어 : 0, 빈칸 : 99
	static Fish[] fishes = new Fish[17]; // 물고기 16 + 상어 1
	
	static final int[] dx = {0,-1,-1, 0, 1, 1, 1, 0,-1};  // 1번~8번 순서대로, 0:상어
	static final int[] dy = {0, 0,-1,-1,-1, 0, 1, 1, 1};  // 1번~8번 순서대로

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_boj_19236.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		for(int i=0; i<4; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<4; j++) {
				int id = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());
				board[i][j] = id;
				fishes[id] = new Fish(i, j, dir);
			}
		}

		// (0, 0) 에 상어 넣기
		fishes[0] = new Fish(0, 0, fishes[board[0][0]].dir);
		fishes[board[0][0]] = new Fish(-1, -1, -1);
		ans = board[0][0];
		board[0][0] = 0;
		
		backTracking(ans);
		System.out.println(ans);
		
		br.close();
	}
	private static void backTracking(int total) {
		
		moveFish();
		
		// 배열 복사
		int[][] tempBoard = copyArr(board);
		
		Fish[] tempFishes = new Fish[17];
		for(int i=0; i<tempFishes.length; i++) {
			tempFishes[i] = fishes[i];
		}

		int x = fishes[0].x;
		int y = fishes[0].y;
		int dir = fishes[0].dir;
		boolean isSharkOutOfBounds = false;
		
		while(true) { // 상어가 공간을 벗어날 때 까지 반복
			x += dx[dir];
			y += dy[dir];
			
			if(!isRange(x, y)) {
				isSharkOutOfBounds = true;
				break;
			}
			if(board[x][y] != 99) { // 빈칸이 아니라면(물고기가 있다면),
				board[fishes[0].x][fishes[0].y] = 99;
				fishes[0] = new Fish(x, y, fishes[board[x][y]].dir);
				fishes[board[x][y]] = new Fish(-1, -1, -1); // 물고기 먹기
				int eaten = board[x][y];
				board[x][y] = 0;
				
				backTracking(total + eaten);
				
				// 배열 되돌리기
				board = copyArr(tempBoard);
				for(int i=0; i< tempFishes.length; i++) fishes[i] = tempFishes[i];
			}
		}
		
		// 상어가 공간 벗어남. ans 값 업데이트
		if(isSharkOutOfBounds) ans = Integer.max(ans, total);
		
	}

	// 물고기 번호 작은 순서대로 이동함.
	private static void moveFish() {
		// 번호가 작은 물고기부터 순서대로 이동.
		for(int idx=1; idx<=16; idx++) {
			Fish fish = fishes[idx];
			
			if(fishes[idx].x == -1) continue; // 먹은 물고기라면 continue
			
			for(int i=0; i<= 8; i++) {
				int dir = (fish.dir + i) % 8;
				if(dir == 0) dir = 8;
				
				int nx = fish.x + dx[dir];
				int ny = fish.y + dy[dir];
				
				if(!isRange(nx, ny) || board[nx][ny] == 0) continue; // 이동못하면 방향 계속 바꾸기
				
				if(board[nx][ny] <= 16) { // 물고기가 있는 칸이면
					// swap
					int targetIdx = board[nx][ny]; 
					board[nx][ny] = idx;
					board[fish.x][fish.y]= targetIdx;
					
					fishes[idx] = new Fish(nx, ny, dir);
					fishes[targetIdx] = new Fish(fish.x, fish.y, fishes[targetIdx].dir);
				} else { // 빈칸이면
					board[nx][ny] = idx;
					board[fish.x][fish.y] = 99;
					fishes[idx] = new Fish(nx, ny, dir);
				}
				break;
			}
		}

	}
	
	// 배열 복사하는 메서드
	private static int[][] copyArr(int[][] arr){
		int[][] temp = new int[4][4];
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				temp[i][j] = arr[i][j];
			}
		}
		return temp;
	}
	// 배열 범위 내에 있는 지 확인하는 메서드
	private static boolean isRange(int x, int y) {
		if(0 <= x && x < 4 && 0 <= y && y < 4) return true;
		return false;
	}
}
