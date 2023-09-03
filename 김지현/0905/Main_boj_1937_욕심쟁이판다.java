package local_algo;

import java.io.*;
import java.util.*;

public class Main_boj_1937_욕심쟁이판다 {
	
	static int N, res = Integer.MIN_VALUE;
	static int[][] map;
	static int[][] dp;
	
	static final int[] dx = {-1, 0, 1, 0};
	static final int[] dy = {0, -1, 0, 1};

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_boj_1937.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 입력받기
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
	
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 모든 칸에서 시작해서 확인해야함.
		// 최대한 많은 칸을 이동
		dp = new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				res = Math.max(res, dfs(i,j));
			}
		}
		
		System.out.println(res);
		br.close();
	}
	
	private static int dfs(int x, int y) {
		if(dp[x][y] != 0) {
			return dp[x][y];
		}
		
		dp[x][y] = 1;
		
		for(int d=0; d<4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			if(isRange(nx, ny) && map[x][y] < map[nx][ny]) {
				dp[x][y] = Math.max(dp[x][y], dfs(nx,ny)+1);
			}
		}
		return dp[x][y];
	}
	
	private static boolean isRange(int x, int y) {
		if(0<=x && x<N && 0<=y && y<N) return true;
		return false;
	}

}
