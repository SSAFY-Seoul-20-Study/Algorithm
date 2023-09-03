package a0829;

import java.util.*;
import java.io.*;

public class Main_bj_14890_경사로 {
	
	static int N, L;
	static int[][] map;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int cnt = 0;
		for(int i = 0; i < N; i++) {
			if(isX(i)) cnt++;
			if(isY(i)) cnt++;
		}
		
		System.out.println(cnt);
		
		br.close();
	}
	
	// 행 확인
	static boolean isX(int x) {
		
		boolean[] v = new boolean[N];
		for(int i = 0; i < N - 1; i++) {
			if(Math.abs(map[x][i] - map[x][i + 1]) > 1) return false;
			
			if(map[x][i] + 1 == map[x][i + 1]) {
				for(int j = i; j > i - L; j--) {
					if(j < 0 || v[j] || map[x][j] != map[x][i]) return false;
					v[j] = true;
				}
			}
			
			if(map[x][i] - 1 == map[x][i + 1]) {
				for(int j = i + 1; j <= i + L; j++) {
					if(j >= N || v[j] || map[x][j] != map[x][i + 1]) return false;
					v[j] = true;
				}
			}
		}
		return true;
	}
	
	//열 확인
	static boolean isY(int y) {
		
		boolean[] v = new boolean[N];
		for(int i = 0; i < N - 1; i++) {
			if(Math.abs(map[i][y] - map[i + 1][y]) > 1) return false; 
			
			if(map[i][y] + 1 == map[i + 1][y]) {
				for(int j = i; j > i - L; j--) {
					if(j < 0 || v[j] || map[j][y] != map[i][y]) return false;
					v[j] = true;
				}
			}
			
			if(map[i][y] - 1 == map[i + 1][y]) {
				for(int j = i + 1; j <= i + L; j++) {
					if(j >= N || v[j] || map[j][y] != map[i + 1][y]) return false;
					v[j] = true;
				}
			}
		}
		return true;
	}

}
