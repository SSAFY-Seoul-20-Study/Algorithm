
import java.io.*;
import java.util.*;

public class 마법사상어와블리자드 {
    static int N, M;
    static int snail[][], board[][], dx[] = { 0, 1, 0, -1 }, dy[] = { -1, 0, 1, 0 };
    static int mdx[] = { -1, 1, 0, 0 }, mdy[] = { 0, 0, -1, 1 };
    static ArrayList<Integer> marbles;
    static int popCount, ans;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        snail = new int[N][N];
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        board[N / 2][N / 2] = -1;
        makeSnail();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int dir = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());
            blizzard(dir - 1, count);
            pushReady();
            while (true) {
                popCount = 0;
                pop();
                if (popCount == 0)
                    break;
            }
            changeReady();
            change();
        }
        System.out.println(ans);
    }

    static void print() {
        for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
        System.out.println();
    }

    static void makeSnail() {
        int x = N / 2;
        int y = N / 2;
        int flag = 1;
        int count = 1;
        int move = 0;
        x: while (x != 0 || y != 0) {
            for (int i = 0; i < 2; i++) {
                move %= 4;
                for (int j = 0; j < flag; j++) {
                    x += dx[move];
                    y += dy[move];
                    if (x < 0 || x >= N || y < 0 || y >= N)
                        break x;
                    snail[x][y] = count++;
                }
                move++;
            }
            flag++;
        }
    }

    static void blizzard(int dir, int count) {
        int x = N / 2;
        int y = N / 2;
        while (count != 0) {
            x += mdx[dir];
            y += mdy[dir];
            board[x][y] = 0;
            count--;
        }
    }

    // 미는 거를 구슬 change하는 것처럼 0만 뺴고 담기
    static void pushReady() {
        marbles = new ArrayList<>();
        int x = N / 2;
        int y = N / 2;
        int flag = 1;
        int move = 0;
        x: while (x != 0 || y != 0) {
            for (int i = 0; i < 2; i++) {
                move %= 4;
                for (int j = 0; j < flag; j++) {
                    x += dx[move];
                    y += dy[move];
                    if (x < 0 || x >= N || y < 0 || y >= N)
                        break x;
                    if (board[x][y] != 0)
                        marbles.add(board[x][y]);
                }
                move++;
            }
            flag++;
        }
    }

    //
    static void pop() {
        marbles.add(0);
        ArrayList<Integer> copied = new ArrayList<>();
        int n = marbles.size();
        int num = marbles.get(0);
        boolean visited[] = new boolean[n];
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (num == marbles.get(i)) {
                count++;
            } else if (num != marbles.get(i)) {
                if (count >= 4) {
                    for (int j = i - 1; j > i - 1 - count; j--)
                        visited[j] = true;
                    popCount++;
                    ans += count * num;
                }
                count = 1;
                num = marbles.get(i);
            }
        }
        for (int i = 0; i < n - 1; i++) {
            if (!visited[i])
                copied.add(marbles.get(i));
        }
        marbles = copied;
    }

    static void changeReady() {
        marbles.add(0);
        ArrayList<Integer> copied = new ArrayList<>();
        int n = marbles.size();
        int num = marbles.get(0);
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (num == marbles.get(i)) {
                count++;
            } else if (num != marbles.get(i)) {
                copied.add(count);
                copied.add(num);
                count = 1;
                num = marbles.get(i);
            }
        }
        marbles = copied;
    }

    static void change() {
        board = new int[N][N];
        int x = N / 2;
        int y = N / 2;
        int flag = 1;
        int move = 0;
        int index = 0;
        int indexM = marbles.size();
        x: while (x != 0 || y != 0) {
            for (int i = 0; i < 2; i++) {
                move %= 4;
                for (int j = 0; j < flag; j++) {
                    x += dx[move];
                    y += dy[move];
                    if (x < 0 || x >= N || y < 0 || y >= N || index == indexM)
                        break x;
                    board[x][y] = marbles.get(index++);
                }
                move++;
            }
            flag++;
        }
    }
}
