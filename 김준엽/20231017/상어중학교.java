import java.io.*;
import java.util.*;

public class 상어중학교 {
    static int N, M;
    static int board[][], dx[] = { -1, 1, 0, 0 }, dy[] = { 0, 0, -1, 1 };
    static boolean visited[][], copied[][], ans[][];
    static int maxBlock = 0, maxRainbow = 0, pos_x = -1, pos_y = -1;
    static int score;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (true) {
            visited = new boolean[N][N];
            maxBlock = 0;
            maxRainbow = 0;
            pos_x = -1;
            pos_y = -1;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] > 0 && !visited[i][j]) {
                        findBlock(i, j);
                    }
                }
            }
            if (maxBlock < 2)
                break;
            deleteBlock();
            gravity();
            rotate();
            gravity();
        }
        System.out.println(score);
    }

    static void print() {
        for (int i = 0; i < N; i++) {
            System.out.print("[");
            for (int j = 0; j < N; j++) {
                if (board[i][j] == -2)
                    System.out.print("  ");
                else if (board[i][j] == -1)
                    System.out.print("* ");
                else
                    System.out.print(board[i][j] + " ");
            }
            System.out.print("]");
            System.out.println();
        }
        for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(visited[i]));
        }
        System.out.println();
        System.out.println(maxBlock);
        System.out.println();
    }

    static void findBlock(int x, int y) {
        int rainbow = 0;
        int block = 1;
        int standard = board[x][y];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[] { x, y });
        copied = new boolean[N][N];
        visited[x][y] = true;
        copied[x][y] = true;
        while (!q.isEmpty()) {
            int cur[] = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if (0 > nx || nx >= N || 0 > ny || ny >= N)
                    continue;
                if (!visited[nx][ny]) {
                    if (board[nx][ny] == standard) {
                        block++;
                        visited[nx][ny] = true;
                        copied[nx][ny] = true;
                        q.add(new int[] { nx, ny });
                    } else if (board[nx][ny] == 0) {
                        block++;
                        rainbow++;
                        visited[nx][ny] = true;
                        copied[nx][ny] = true;
                        q.add(new int[] { nx, ny });
                    }
                }
            }
        }
        if (block == 1)
            visited[x][y] = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0)
                    visited[i][j] = false;
            }
        }

        if (block > maxBlock) {
            maxBlock = block;
            maxRainbow = rainbow;
            pos_x = x;
            pos_y = y;
            ans = copied;
        } else if (block == maxBlock) {
            if (rainbow > maxRainbow) {
                maxBlock = block;
                maxRainbow = rainbow;
                pos_x = x;
                pos_y = y;
                ans = copied;
            } else if (rainbow == maxRainbow) {
                if (x > pos_x) {
                    maxBlock = block;
                    maxRainbow = rainbow;
                    pos_x = x;
                    pos_y = y;
                    ans = copied;
                } else if (pos_x == x) {
                    if (y > pos_y) {
                        maxBlock = block;
                        maxRainbow = rainbow;
                        pos_x = x;
                        pos_y = y;
                        ans = copied;
                    }
                }
            }
        }
    }

    static void deleteBlock() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (ans[i][j]) {
                    board[i][j] = -2;
                    count++;
                }
            }
        }
        score += (int) Math.pow((double) count, (double) 2);
    }

    static void rotate() {
        int copy[][] = new int[N][N];
        int x = 0, y = 0;
        for (int j = N - 1; j >= 0; j--) {
            for (int i = 0; i < N; i++) {
                copy[x][y++] = board[i][j];
            }
            x++;
            y = 0;
        }
        board = copy;
    }

    static void gravity() {
        for (int i = N - 2; i >= 0; i--) {
            for (int j = 0; j < N; j++) {
                int cur = i;
                if (board[cur][j] == -1)
                    continue;
                while (cur != N - 1 && board[cur + 1][j] == -2) {
                    board[cur + 1][j] = board[cur][j];
                    board[cur][j] = -2;
                    cur++;
                }
            }
        }
    }
}
