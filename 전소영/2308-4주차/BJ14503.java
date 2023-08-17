import java.io.*;
import java.util.*;

// 로봇 청소기

public class BJ14503 {

	static int n, m;
	static int x, y, d;
	static int ans;
	static int[][] board;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board = new int[n][m];
		
		st = new StringTokenizer(br.readLine());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < m; j++) {
				if(st.nextToken().equals("1")) board[i][j] = 1;
			}
		}
		
		solution();
		System.out.println(ans);
	}
	
	private static void solution() {
		
		while(true) {
			
			// 1번
			if(board[x][y] == 0) {
				board[x][y] = 2;
				ans++;
			}
			
			// 주변 4칸 탐색
			boolean flag = false;
			for(int i = 0; i < 4; i++) {
				int xx = x + dx[i];
				int yy = y + dy[i];
				
				if(board[xx][yy] == 0) {
					flag = true;
					break;
				}
			}
			
			// 2번
			if(!flag) {
				int xx = x;
				int yy = y;
				if(d % 2 == 0) {
					xx += dx[2 - d];
					yy += dy[2 - d];
				}
				else {
					xx += dx[4 - d];
					yy += dy[4 - d];
				}
				
				if(board[xx][yy] != 1) {
					x = xx;
					y = yy;
				}
				else return;
			}
			// 3번
			else {
				d = (d + 3) % 4;
				
				int xx = x + dx[d];
				int yy = y + dy[d];
				
				if(board[xx][yy] == 0) {
					x = xx;
					y = yy;
				}
			}
		}
	}
}
