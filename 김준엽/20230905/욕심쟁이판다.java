import java.io.*;
import java.util.*;

public class Main {
    static int N,ans = Integer.MIN_VALUE;
    static int board[][], dx[] ={-1,1,0,0}, dy[] = {0,0,-1,1};
    static int dp[][];
    static boolean visited[][];
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans = Math.max(ans, dfs(i,j));
            }
        }

        System.out.println(ans);
    }
    static int dfs(int x, int y){
        if(dp[x][y] != 0){
            return dp[x][y];
        }
        if(dp[x][y] == 0) dp[x][y] = 1;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(0<=nx && nx<N && 0<=ny && ny<N && board[nx][ny] > board[x][y]){
                dp[x][y] = Math.max(dp[x][y], dfs(nx,ny) + 1);
            }
        }
        return dp[x][y];
    }
}