package a0829;

import java.util.*;
import java.io.*;

public class Main_bj_14891_톱니바퀴{

	static int[][] map;
	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		map = new int[4][8];
		
		for(int i = 0; i < 4; i++) {
			String str = br.readLine();
			for(int j = 0; j < 8; j++) {
				map[i][j] = (int)str.charAt(j) - '0';
			}
		}
		
		int K = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < K; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken()); // 회전 시킨 번호
			int dir = Integer.parseInt(st.nextToken()); // 방향
			// 1 시계방향 -1 반시계방향
			
			// 현재 톱니바퀴의 6번 2번
			int tmpLeft = map[N - 1][6];
			int tmpRight = map[N - 1][2];
			
			// 방향에 따른 회전
			if(dir == 1) Watch(N - 1);
			else reverseWatch(N-1);
			
			//맞닿은 극 -> 2번, 6번
			int nDir = dir; // 방향 
			int prev = tmpLeft; // 이전 값
			
			// 왼쪽 비교 
			for(int t = N - 1; t >= 1; t--) {
				if(prev != map[t - 1][2]) { // 6번 - 2번 비교 -> 다르면 회전
					prev = map[t - 1][6]; // prev update
					
					// 방향 변경
					if(nDir == 1) {
						nDir = -1;
						reverseWatch(t - 1);
					}
					else {
						nDir = 1;
						Watch(t - 1);
					}
					
				} else break; // 같으면 중지
			}
			
			// 오른쪽 비교 
			nDir = dir;
			prev = tmpRight;
			for(int t = N - 1; t < 3; t++) {
				if(prev != map[t + 1][6]) {
					prev = map[t + 1][2];
					if(nDir == 1) {
						nDir = -1;
						reverseWatch(t + 1);
						
					}
					else {
						nDir = 1;
						Watch(t + 1);
					}
				} else break;
			}
			
		}
		int sum = 0;
		
		for(int i = 0; i < 4; i++) {
			if(map[i][0] == 0) continue;
			sum += Math.pow(2, i); // 점수 계산
		}
		System.out.println(sum);
	}
	
	//반시계방향 회전
	static void reverseWatch(int num) {
		int tmp = map[num][0];
		for(int i = 0; i < 7; i++) {
			map[num][i] = map[num][i + 1];
		}
		map[num][7] = tmp;
	}

	//시계방향 회전
	static void Watch(int num) {
		int tmp = map[num][7];
		for(int i = 7; i >= 1; i--) {
			map[num][i] = map[num][i - 1];
		}
		map[num][0] = tmp;
	}
}
