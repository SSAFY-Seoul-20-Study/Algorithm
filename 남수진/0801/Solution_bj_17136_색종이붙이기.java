package a0801;

import java.util.*;
import java.io.*;

public class Solution_bj_17136_색종이붙이기 {

	static int[][] board;
	static int answer = Integer.MAX_VALUE;
	static int[] paper = {0, 5, 5, 5, 5, 5};
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		board = new int[10][10];

		for(int i = 0; i<10; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j<10; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());

			}
		}
		recursion(0, 0, 0);
		
		if(answer == Integer.MAX_VALUE) System.out.println(-1);
		else System.out.println(answer);
	}
	public static void recursion(int x, int y, int cnt) {

		if(x == 9 && y == 10) { // 경로 탐색 종료
			answer = Math.min(answer, cnt); 
			return;
		}
		if(cnt >= answer) return; // 값이 더 커지면 종료
		
		if(y == 10) { // y값이 다 차면, x 1 증가
			recursion(x + 1, 0, cnt);
			return;
		}
		
		// 종이가 안 붙은 1인 경
		if(board[x][y] == 1) {
			for(int i = 5; i >= 1; i--) { // 1x1 ~5x5
				if(isAttach(x, y, i) && paper[i] > 0) {
					attach(x, y, i, 0); // 색종이를 붙임
                    paper[i]--;
                    recursion(x, y + 1, cnt + 1);
                    
                    attach(x, y, i, 1); // 색종이를 다시 뗌.
                    paper[i]++;
					
				}
			}
			
		} else {
			recursion(x, y + 1, cnt);
		}
	}
	
	// 종이를 붙일 수 있는 
	public static boolean isAttach(int x, int y, int size) {
		for(int i = x; i< x + size; i++) {
			for(int j = y; j< y + size; j++) {
				if(i >= 10 || j >= 10) return false;
				if(board[i][j] != 1) return false;
			}
		}
		return true;
	}
	
	// 색종이를 붙이거나 제거 붙이면 0 떼면 1
	public static void attach(int x, int y, int size, int state) {
		
		for(int i = x; i< x + size; i++) {
			for(int j = y; j< y + size; j++) {
				board[i][j] = state;
			}
		}
	}
	
}
