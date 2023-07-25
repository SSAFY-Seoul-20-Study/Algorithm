import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

// 파이프 옮기기 1

public class BJ17070_2 {

    static int n;
    static boolean[][] board;
    static int[][][] dp;       // 가로: dp[0][x][y], 세로: dp[1][x][y], 대각선: dp[2][x][y]

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        board = new boolean[n + 1][n + 1];
        dp = new int[3][n + 1][n + 1];
        dp[0][1][2] = 1;

        StringTokenizer st;
        for(int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= n; j++) {
                if(st.nextToken().equals("1")) board[i][j] = true;
            }
        }

        solution();
        long ans = dp[0][n][n] + dp[1][n][n] + dp[2][n][n];
        System.out.println(ans);
    }

    private static void solution() {

        for(int i = 1; i <= n; i++) {
            for(int j = 2; j <= n; j++) {
                
                if(board[i][j]) continue;

                // 가로 방향
                dp[0][i][j] += dp[0][i][j - 1] + dp[2][i][j - 1];
                
                // 세로 방향
                dp[1][i][j] += dp[1][i - 1][j] + dp[2][i - 1][j];
                
                // 대각선 방향
                if(board[i - 1][j] || board[i][j - 1]) continue;
                dp[2][i][j] += dp[0][i - 1][j - 1] + dp[1][i - 1][j - 1] + dp[2][i - 1][j - 1];
            }
        }

    }
}
