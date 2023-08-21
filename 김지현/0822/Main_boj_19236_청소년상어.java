package study_algo;

import java.util.*;
import java.util.zip.InflaterInputStream;
import java.io.*;

public class Main_boj_19236_청소년상어 {
	
	static int[][] a = new int[4][4]; // 물고기 번호 배열(1<= 번호 <= 16. 모두 다른 번호)
	static int[][] b = new int[4][4]; // 방향 배열
	static int[] shark = new int[3]; // 인덱스, 방향
	static int res; // 상어가 먹은 물고기 번호 
	
	static boolean[] v = new boolean[17]; // 물고기 번호 방문배열(1~16번)
	
	static final int[] dx = {0,-1,-1, 0, 1, 1, 1, 0,-1};  // 1번~8번 순서대로.
	static final int[] dy = {0, 0,-1,-1,-1, 0, 1, 1, 1};  // 1번~8번 순서대로.

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_boj_19236.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		for(int i=0; i<4; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<4; j++) {
				a[i][j] = Integer.parseInt(st.nextToken());
				b[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 처음 초기화 (0,0)
		res = a[0][0]; // (0,0) 자리 물고기 번호 저장.
		shark[2] = b[0][0]; // 상어 방향 설정
		
		a[0][0] = -1; // 첫 상어 칸 -1으로 초기화
		v[res] = true;
		
		moveFish();
		int[][] copyA = copyArr(a);
		int[][] copyB = copyArr(b);
		
		moveShark(copyA, copyB, 0);
		
	}
	private static void moveShark(int[][] A, int[][] B, int score) {
		
		for(int[] aa:A)System.out.println(Arrays.toString(aa)); System.out.println();
		for(int[] bb:B)System.out.println(Arrays.toString(bb)); System.out.println();
		System.out.println(Arrays.toString(shark));
		
		score += A[shark[0]][shark[1]];
		A[shark[0]][shark[1]] = -1;
		v[A[shark[0]][shark[1]]] = true;
		
		
		res = Math.max(res, score);
		moveFish(); // 물고기 이동
		
		int nx = shark[0];
		int ny = shark[1];
		
		for(int cnt= 1; cnt<=3; cnt++) {
			nx += dx[shark[2]];
			ny += dy[shark[2]];
			
			if(!isRange(nx, ny)) break;
			if(A[nx][ny] > 0) {
				
//				score += A[nx][ny];
				A[shark[0]][shark[1]] = 0; // 기존 상어가 있던 곳은 빈 곳으로 만들기.
				shark[0] = nx; // 상어 위치 바꾸기
				shark[1] = ny;
				shark[2] = B[nx][ny]; // 방향 저장.
				moveShark(A, B, score);
				
			}
		}
	}
	
	private static void moveFish() {
		for(int i=1; i<=16; i++) {
			if(!v[i]) { // 번호가 작은 물고기부터 순서대로 이동.(상어가 먹은 물고기 번호 제외하고)
				findFish(i);
			}
		}
//		for(int[] aa:a)System.out.println(Arrays.toString(aa)); System.out.println();
//		for(int[] bb:b)System.out.println(Arrays.toString(bb)); System.out.println();
	}
	private static void findFish(int target) {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(a[i][j] == target) {
					int nx = i + dx[b[i][j]];
					int ny = j + dy[b[i][j]];
					while(!isRange(nx, ny) || a[nx][ny] == -1) {
						// 45도 반시계 회전
						b[i][j] = (b[i][j] % 8)+1;
						
						nx = i + dx[b[i][j]];
						ny = j + dy[b[i][j]];
					}
					if(a[nx][ny] > 0) swap(i,j, nx, ny);
					return;
				}
			}
		}
	}
	
	private static void swap(int i, int j, int nx, int ny) {
		int temp = a[i][j];
		a[i][j] = a[nx][ny];
		a[nx][ny] = temp;
		
		temp = b[i][j];
		b[i][j] = b[nx][ny];
		b[nx][ny] = temp;
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
