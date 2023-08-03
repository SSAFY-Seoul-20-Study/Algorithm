package selfPractice.backjoon;

import java.util.*;
import java.io.*;

public class Main_backjoon_17406_배열_돌리기_4 {
	static int N, M, K;
	static int[][] origin;
	static int[][] arr;
	static int[][] operations;
	
	static int [] dr = {0, 1, 0, -1};
	static int [] dc = {1, 0, -1, 0};
	
	static int[] a, b;
	static boolean[] v;
	
	static int total_min = 999999999;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		origin =new int [N][M];
		operations = new int [K][3];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				origin[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<3;j++) {
				operations[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		a=new int[K];
		for(int i=0;i<K;i++) a[i] = i;
		b=new int[K];
		v = new boolean[K]; //kPk 구하기
		
		perm(0);
		
		System.out.println(total_min);
	}
	
	static void perm(int cnt) {
		if(cnt==K) {
			deepcopyOrigin();
			for(int i=0;i<K;i++) {
				int idx = b[i];
				int [] op = operations[idx];
				spinArr(op);
			}
			int result = getMin();
			total_min = Math.min(total_min, result);
			return;
		}
		for(int i=0;i<K;i++) {
			if(v[i]) continue;
			v[i] = true;
			b[cnt] = a[i];
			perm(cnt+1);
			v[i] = false;
		}
	}
	
	static void spinArr(int[] op){
		int center_row = op[0] -1;
		int center_col = op[1] -1;
		
		int D = op[2];
		
		for(int step=1;step<=D;step++) {
			int row = center_row - step;
			int col = center_col - step;
			int before = arr[row][col];
			
			for(int di=0;di<4;di++) {
				for(int w=0;w<2*step;w++) {
					row = row + dr[di];
					col = col + dc[di];
					
					int temp = arr[row][col];
					arr[row][col] = before;
					before = temp;
				}
			}
		}
	}
	
	static int getMin() {
		int min = 999999999;
		for(int i=0;i<N;i++) {
			int sum = 0;
			for(int j=0;j<M;j++) sum += arr[i][j];
			min = Math.min(min, sum);
		}
		return min;
	}
	
	static void deepcopyOrigin(){
		arr = new int[N][M];
		for(int i=0;i<N;i++) 
			for(int j=0;j<M;j++) 
				arr[i][j] = origin[i][j];
			
		
	}
	

}
