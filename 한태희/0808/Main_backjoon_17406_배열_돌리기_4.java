package selfPractice.backjoon;

import java.util.*;
import java.io.*;

public class Main_backjoon_17406_배열_돌리기_4 {
	static int N, M, K;
	static int[][] arr;
	static int[][] operations;
	
	static int [] dr = {0, 1, 0, -1};
	static int [] dc = {1, 0, -1, 0};

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		arr =new int [N][M];
		operations = new int [K][3];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<3;j++) {
				operations[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int[] op:operations) {
			int center_row = op[0] -1;
			int center_col = op[1] -1;
			
			int D = op[2];
			int row = center_row - 1;
			int col = center_col - 1;
			int before = arr[row][col];
			for(int d=0;d<D;d++) {
				for(int idx=0;idx<4;idx++) {
					for(int w=0;w<2*d;w++) {
						row = row + dr[idx];
						col = col + dc[idx];
						System.out.printf("%d, %d\n", row, col);
						int temp = arr[row][col];
						arr[row][col] = before;
						before = temp;
					}
				}
			}
			for(int []b:arr)System.out.println(Arrays.toString(b));
			System.out.println();
		}
		
//		for(int []b:arr)System.out.println(Arrays.toString(b));
	}
	

}
