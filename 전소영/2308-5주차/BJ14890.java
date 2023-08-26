import java.io.*;
import java.util.*;

// 경사로

public class BJ14890 {

    static int n, l;
    static int ans;
    static int[][] board;
    static int[] dx = {0, 1};
    static int[] dy = {1, 0};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        board = new int[n][n];

        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        solution();
        System.out.println(ans);
    }

    private static void solution() {

        for(int i = 0; i < n; i++) {
            if(isValid(i, 0, 0)) ans++;     // 가로 방향으로 길을 만드는 경우
            if(isValid(0, i, 1)) ans++;     // 세로 방향으로 길을 만드는 경우
        }
    }

    private static boolean isValid(int x, int y, int d) {     // (x, y)에서 d 방향으로 길을 만들 수 있는지의 여부

        boolean[] isSlope = new boolean[n];                   // 경사로를 놓았는지의 여부
        int height = board[x][y];

        while(true) {
            x += dx[d];
            y += dy[d];

            if(x >= n || y >= n) return true;
            if(board[x][y] == height) continue;
            if(Math.abs(board[x][y] - height) > 1) return false;        // 높이 차이가 1 초과인 경우 불가능
            
            if(board[x][y] < height) {                                  // 내려가는 경사인 경우
                // l개의 칸의 높이가 모두 같은지 확인
                int xx = x;
                int yy = y;
                for(int i = 0; i < l; i++) {
                    if(xx >= n || yy >= n || board[xx][yy] != board[x][y]) return false;                // 경사로를 놓다가 범위를 벗어나거나 높이가 같지 않은 경우 불가능
                    if(d == 0) isSlope[yy] = true;
                    else isSlope[xx] = true;
                    xx += dx[d];
                    yy += dy[d];
                }

                x = xx - dx[d];
                y = yy - dy[d];
                height = board[x][y];
            }
            else {                                                      // 올라가는 경사인 경우
                // l개의 칸의 높이가 모두 같은지 확인
                int xx = x;
                int yy = y;
                for(int i = 0; i < l; i++) {
                    xx -= dx[d];
                    yy -= dy[d];
                    if(xx < 0 || yy < 0 || (d == 0 ? isSlope[yy] : isSlope[xx])) return false;                // 경사로를 놓다가 범위를 벗어나거나 이미 경사로를 놓은 경우 불가능
                    if(d == 0) isSlope[yy] = true;
                    else isSlope[xx] = true;
                }

                height = board[x][y];
            }
        }
    }
}