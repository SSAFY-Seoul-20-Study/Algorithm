package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class boj_3197_백조의호수 {

    static int[] bird1 = null;
    static int[] bird2 = null;

    static int r, c;
    static char[][] board;
    static boolean[][] v;
    static ArrayDeque<int[]> meltQ = new ArrayDeque<>();
    static ArrayDeque<int[]> q = new ArrayDeque<>();

    static int[][] move = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        board = new char[r][c];
        v = new boolean[r][c];

        // n^2
        for (int i = 0; i < r; i++) {
            String line = br.readLine();
            for (int j = 0; j < c; j++) {
                board[i][j] = line.charAt(j);
                if (board[i][j] == 'L') {
                    if (bird1 == null) {
                        bird1 = new int[]{i, j};
                    } else {
                        bird2 = new int[]{i, j};
                    }
                    board[i][j] = '.';
                }
                if (board[i][j] == '.') {
                    meltQ.add(new int[]{i,j});
                }
            }
        }

        q.add(new int[]{bird1[0], bird1[1]});
        v[bird1[0]][bird1[1]] = true;

//        System.out.println(Arrays.deepToString(board));

        int day = 0;
        while (true) {
            // 만날수있는지확인
            if (isConnected()) {
                break;
            }

            // 녹이기
            melt();
            day++;
        }
        System.out.println(day);


    }

    static boolean isConnected() {
        ArrayDeque<int[]> nextQ = new ArrayDeque<>();

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            // 가다가 처음만난 x이기때문에 다음 턴에는 무조건 녹는다.

            for (int[] dxy : move
            ) {
                int nx = cur[0] + dxy[0];
                int ny = cur[1] + dxy[1];

                if (nx<0 || r<=nx || ny<0 || c<=ny || v[nx][ny]) continue;
                v[nx][ny] = true;

                if (nx == bird2[0] && ny == bird2[1]) {
                    return true;
                } else if (board[nx][ny]=='X') {
                    nextQ.add(new int[]{nx,ny});
                } else {
                    q.add(new int[]{nx,ny});
                }
            }
        }
        q = nextQ;
        return false;
    }

    static void melt() {
        int size = meltQ.size();
        for (int it = 0; it < size; it++) {
            int[] cur = meltQ.poll();
            for (int[] dxy:move
            ) {
                int nx = cur[0] + dxy[0];
                int ny = cur[1] + dxy[1];
                if (0 <= nx && nx < r && 0 <= ny && ny < c && board[nx][ny] == 'X') {
                    board[nx][ny] = '.';
                    meltQ.add(new int[]{nx,ny});
                }
            }
        }
    }
}
