package study;

import java.io.*;
import java.util.*;

public class 배열돌리기4 {
	static int [][] board;
	static int [][] copyBoard;
	static int [][] rotate;
	static boolean [] check;
	static int [] index;
	static int N,M,K;
	static int answer = Integer.MAX_VALUE;
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_17406.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(st.nextToken()); 
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		board = new int[N][M];
		copyBoard = new int[N][M];
		rotate = new int[K][3];
		check = new boolean[K];
		index = new int[K];
		for(int i = 0 ; i<N ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j ++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				copyBoard[i][j] = board[i][j];
			}
		}		
		for(int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine());
			rotate[i][0] = Integer.parseInt(st.nextToken());
			rotate[i][1] = Integer.parseInt(st.nextToken());
			rotate[i][2] = Integer.parseInt(st.nextToken());
//			int x1 = r-s-1, y1 = c-s-1, x2 = r+s-1, y2 = c+s-1;
//			while(x1 != x2 && y1 != y2) {
//				System.out.println(x1 + ", " + x2 + ", " + y1 + ", " +y2);
//				rotation(x1++,y1++,x2--,y2--);
//			}
		}
		permutation(0);
//		for(int i = 0; i<N; i++) System.out.println(Arrays.toString(board[i]));
//		rotation(0,0,0,0);
//		System.out.println();
//		for(int i = 0; i<N; i++) System.out.println(Arrays.toString(board[i]));
		System.out.println(answer);
	}
	static void permutation(int depth) {
		if(depth == K) {
			for(int i=0; i<K; i++) {
				int r = rotate[index[i]][0];
				int c = rotate[index[i]][1];
				int s = rotate[index[i]][2];
				int x1 = r-s-1, y1 = c-s-1, x2 = r+s-1, y2 = c+s-1;
				while(x1 != x2 && y1 != y2) {
					rotation(x1++,y1++,x2--,y2--);
				}
			}
			//한 케이스에 대하여 회전 끝
			findMin();
			init();
//			System.out.println(answer);
			return;
		}
		for(int i=0; i<K; i++) {
			if(check[i]) continue;
			check[i] = true;
			index[depth] = i;
			permutation(depth+1);
			check[i] = false;
		}
		
	}
	static void rotation(int x1, int y1,int x2, int y2) {
		int tmp = board[x1][y1];
		//left
		for(int i=x1+1; i<=x2; i++) board[i-1][y1] =board[i][y1];
		//down
		for(int i=y1+1; i<=y2; i++) board[x2][i-1] = board[x2][i];
		//right
		for(int i=x2-1; i>=x1; i--) board[i+1][y2] = board[i][y2];
		//top
		for(int i = y2-1; i>y1; i--) board[x1][i+1] = board[x1][i];
		board[x1][y1+1] = tmp;
	}
	static void findMin() {
		for(int i=0; i<N; i++) {
			int tmp = 0;
			for(int j=0; j<M; j++) {
				tmp += board[i][j];
			}
			if(tmp < answer) answer = tmp;
		}
	}
	static void init() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				board[i][j] = copyBoard[i][j];
			}
		}
	}
}
