import java.io.*;
import java.util.*;

// 배열 돌리기 4

public class BJ17406 {

    private static class Info {
        int r;
        int c;
        int s;

        Info(int r, int c, int s) {
            this.r = r;
            this.c = c;
            this.s = s;
        }
    }
    static int n, m, k;
    static int ans = Integer.MAX_VALUE;
    static int[][] board;
    static Info[] rotaInfos;        // 입력 받은 회전 연산 담기
    static boolean[] visited;       // 해당 인덱스의 회전 연산을 선택했는지의 여부
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        board = new int[n + 1][m + 1];
        rotaInfos = new Info[k];
        visited = new boolean[k];

        for(int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            rotaInfos[i] = new Info(r, c, s);
        }

        backTracking(0);
        System.out.println(ans);
    }

    private static void backTracking(int depth) {

        if(depth == k) {
            // 모든 회전 연산이 끝난 경우, 배열의 값 계산하기
            int value = calculateValueOfBoard();
            ans = Integer.min(ans, value);
            return;
        }

        for(int i = 0; i < k; i++) {

            if(visited[i]) continue;

            visited[i] = true;
            // tempBoard에 board 복사하기
            int[][] tempBoard = new int[n + 1][m + 1];
            for(int r = 1; r <= n; r++) {
                for(int c = 1; c <= m; c++) {
                    tempBoard[r][c] = board[r][c];
                }
            }

            rotateBoard(i);
            backTracking(depth + 1);

            visited[i] = false;
            // board에 tempBoard 복사하기
            for(int r = 1; r <= n; r++) {
                for(int c = 1; c <= m; c++) {
                    board[r][c] = tempBoard[r][c];
                }
            }
        }
    }

    private static void rotateBoard(int idx) {        // idx번째 회전 연산을 사용해서 회전하기

        int r = rotaInfos[idx].r;
        int c = rotaInfos[idx].c;
        int s= rotaInfos[idx].s;

        // 하나의 회전 연산에 대하여 s번 사이클을 돌려야 함
        for(int i = 0; i < s; i++) {
            cycle(--r, --c, i * 2 + 3);
        }
    }

    private static void cycle(int x, int y, int size) {     // 좌상단 좌표 (x, y)를 기준으로 한 변의 길이가 size인 사이클

        int temp = board[x][y];

        for(int dir = 0; dir < 4; dir++) {
            for(int num = 0; num < size - 1; num++) {
                int xx = x + dx[dir];
                int yy = y + dy[dir];
                board[x][y] = board[xx][yy];
                x = xx;
                y = yy;
            }
        }

        board[x][y + 1] = temp;
    }

    private static int calculateValueOfBoard() {
        int min = Integer.MAX_VALUE;

        for(int i = 1; i <= n; i++) {
            int total = 0;
            for(int j = 1; j <= m; j++) {
                total += board[i][j];
            }
            min = Integer.min(min, total);
        }

        return min;
    }
}
