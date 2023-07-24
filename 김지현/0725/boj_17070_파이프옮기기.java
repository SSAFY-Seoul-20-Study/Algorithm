package study_algo;

import java.util.*;
import java.io.*;

public class boj_17070 {
	static int N;
	static int[][] house;
	static int ans = 0;

	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("res/input_boj_17070.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());
		house = new int[N+1][N+1];

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				house[i][j] = Integer.parseInt(st.nextToken());
			}
		}
//		for (int[] a : house) {
//			System.out.println(Arrays.toString(a));
//		}
		dfs(1,2,0);
		
		sb.append(ans);
		
		System.out.println(sb.toString());
		br.close();
	}

	public static void dfs(int x, int y, int dir) {
		if (x == N && y == N) {
			ans++;
			return;
		}
		switch (dir) {
		case 0: // 파이프 : 가로 -> 오른쪽, 대각선
			if (y + 1 <= N && house[x][y + 1] == 0) {
				dfs(x, y + 1, 0);
			}
			break;
		case 1: // 파이프 : 세로 -> 아래, 대각선
			if (x + 1 <= N && house[x + 1][y] == 0) {
				dfs(x + 1, y, 1);
			}
			break;
		case 2: // 파이프 : 대각선 -> 오른쪽, 아래, 대각선
			if (y + 1 <= N && house[x][y + 1] == 0) { // 오른쪽
				dfs(x, y + 1, 0);
			}
			if (x + 1 <= N && house[x + 1][y] == 0) { // 아래
				dfs(x + 1, y, 1); 
			}
			break;
		}
		// 대각선은 모두 갈 수 있음. 여기서 한번에 처리
		if(y+1 <=N && x+1 <= N && house[x][y+1]==0 && house[x+1][y] ==0 && house[x+1][y+1] == 0) {
			dfs(x+1, y+1, 2);
		}
	}

}
