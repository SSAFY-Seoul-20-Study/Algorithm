package bj0803;

import java.util.*;
import java.io.*;

public class Main_bj_17406_배열돌리기4  {

	// N, M: 배열 크기 K: 회전 횟수 sum: 행 최솟값 min: 회전 최솟값
	static int N, M, K, sum = Integer.MAX_VALUE, min = Integer.MAX_VALUE;
	// map: 원본 map, temp: copy, rotate: 회전 연산
	static int[][] map, temp, rotate;
	static boolean[] v; // 회전 연산 수행 여부
	static int[] order; // 회전 연산 수행 순서
	
	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		// 초기화
		map = new int[N][M];
		v = new boolean[K];
		order = new int[K];
		rotate = new int[K][3];
		temp = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			rotate[i][0] = Integer.parseInt(st.nextToken());
			rotate[i][1] = Integer.parseInt(st.nextToken());
			rotate[i][2] = Integer.parseInt(st.nextToken());
		}
		
		calc(0);
		
		System.out.println(min);
		
	}
	
	// 회전 연산 순서 정하기 - perm
	static void calc(int cnt) {
		if(cnt == K) { // 순서가 정해진 후 연산
			sum = Integer.MAX_VALUE;
			for(int j = 0; j < N; j++) temp[j] = Arrays.copyOf(map[j], M);
			
			for(int i = 0; i < K; i++) {

				int index = order[i];
				
				int r = rotate[index][0];
				int c = rotate[index][1];
				int s = rotate[index][2];
				
				int tx = r - s - 1;
				int ty = c - s - 1;
				int bx = r + s - 1;
				int by = c + s - 1;
				
				rotation(tx, ty, bx, by); // 회전 연산 수행
			
			}
			calcSum(); // 회전 후 값 행의 합 중 최솟값 계산
			min = Math.min(min, sum);
			return;
		}
		
		for(int i = 0; i < K; i++) {
			if(v[i]) continue;
			v[i] = true;
			order[cnt] = i;
			calc(cnt + 1);
			v[i] = false;
		}
		
	}
	
	// 회전 후 값 행의 합 중 최솟값 계산
	static void calcSum() {
		
		for(int i = 0; i < N; i++) {
			int s = 0;
			for(int j = 0; j < M; j++) {
				s += temp[i][j];
			}
			
			sum = Math.min(sum, s);
		}
	}
	
	// 회전 연산
	static void rotation(int tx, int ty, int bx, int by) {
		if(tx >= bx && ty >= by) return;
		
		int copy = temp[tx][by]; // 처음 기준 값 저장 -> 손실 방지
		// 좌 -> 우
		for(int i = by; i > ty; i--) {
			temp[tx][i] = temp[tx][i - 1];
		}
		// 아래 -> 위
		for(int i = tx; i < bx; i++) {
			temp[i][ty] = temp[i + 1][ty]; 
		}


		//우 -> 좌
		for(int i = ty; i < by; i++) {
			temp[bx][i] = temp[bx][i + 1]; 
		}


		// 위 -> 아래
		for(int i = bx; i > tx; i--) {
			temp[i][by] = temp[i - 1][by];
		}

		temp[tx + 1][by] = copy;
		
		rotation(tx + 1, ty + 1, bx - 1, by - 1); // 내부 회전 반복
	}
	

}
