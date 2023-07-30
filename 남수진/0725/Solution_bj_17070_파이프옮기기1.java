import java.io.*;
import java.util.*;

public class Solution_bj_17070_파이프옮기기1{
	static int[][] map;
	static int cnt = 0, N = 0;
 
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		for(int i = 0; i <N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		dfs(0, 0, 1);
		
		System.out.println(cnt);
		
		br.close();
		return;
	}
	// state 현재 상태 - 가로 0, 세로 1, 대각선 2
	public static void dfs(int state, int x, int y) {
		if(x == N - 1 && y == N - 1) {
			cnt++;
			return;
		} 
		// 가로이동
		if(state == 0 || state == 2) {
			if(y + 1 < N && map[x][y + 1] == 0) {
				dfs(0, x, y + 1);
			}
		}
		// 세로 이동 
		if(state == 1 || state == 2) {
			if(x + 1 < N&& map[x + 1][y] == 0) {
				dfs(1, x + 1, y);
			}
		}
		// 대각선 이동 
		if(x + 1 < N && y+ 1 < N) {
			if( map[x + 1][y] == 0&& map[x][y + 1] == 0 && map[x+1][y+1] == 0) 
				dfs(2, x + 1, y + 1);
		}
	}
}
