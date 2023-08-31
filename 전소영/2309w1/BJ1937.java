import java.io.*;
import java.util.*;

// 욕심쟁이 판다

public class BJ1937 {
    
    static int n;
    static int[][] board;
    static int[][] dp;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        board = new int[n][n];
        dp = new int[n][n];
        
        StringTokenizer st;
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                dp[i][j] = dfs(i, j);			// (i, j)를 시작으로 이동할 수 있는 최대 depth를 반환함
            }
        }
        
        int ans = 1;
        for(int i = 0; i < n; i++) {
        	for(int j = 0; j < n; j++) {
        		ans = Integer.max(ans, dp[i][j]);
        	}
        }
        System.out.println(ans);
    }
    
    private static int dfs(int x, int y) {
    	
    	if(dp[x][y] != 0) return dp[x][y];		// 이미 방문했던 위치라면 이미 저장된 값 반환
    	
    	dp[x][y] = 1;							// (x, y)를 시작으로 하기에 일단 1 부여
        
        for(int i = 0; i < 4; i++) {			// 갈 수 있는 네 방향을 탐색
            int xx = x + dx[i];
            int yy = y + dy[i];
            
            if(xx < 0 || xx >= n || yy < 0 || yy >= n || board[xx][yy] <= board[x][y]) continue;
            // 갈 수 있다면, 현재 저장된 값과 ((xx, yy)를 시작으로 이동할 수 있는 최대 depth) + 1 값을 비교하여 업데이트
            dp[x][y] = Integer.max(dp[x][y], dfs(xx, yy) + 1);
        }
        
        // 더 이상 갈 수 있는 방향이 없다면, 해당 값을 반환하며 종료
        return dp[x][y];
    }

}
