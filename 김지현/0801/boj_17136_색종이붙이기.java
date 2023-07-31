import java.util.*;
import java.io.*;

// 23428KB	264ms
public class boj_17136_색종이붙이기 {
    static int[][] paper = new int[10][10];
    static int[] remain = {0, 5, 5, 5, 5, 5}; // 남은 종이 수
    static int res = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("res/input_boj_17136.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 10; j++) {
                paper[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0, 0);

        if (res == Integer.MAX_VALUE) res = -1;

        System.out.println(res);
        br.close();

    }

    // dfs + 백트래킹
    public static void dfs(int x, int y, int cnt) {
        // 종료 조건 : 맨 끝 도착
        if (x >= 9 && y > 9) {
            res = Math.min(res, cnt);
            return;
        }

        // 기존에 구해놓은 res보다, cnt가 커지면, 종료
        if (res <= cnt) return;

        // 마지막 열 넘으면, 다음 줄로 가기.
        if (y > 9) {
            dfs(x + 1, 0, cnt);
            return;
        }

        if (paper[x][y] == 1) {
            for (int i = 5; i >= 1; i--) { // 색종이 큰 것부터 붙이기
                if (remain[i] > 0 && isAttach(x, y, i)) {
                    attach(x, y, i, 0); // 붙임
                    remain[i]--; // 색종이 붙였으니, 남은 개수 하나 빼기
                    dfs(x, y + 1, cnt + 1);
                    attach(x, y, i, 1); // 다시 뗌
                    remain[i]++; // 색종이 떼어낸 거, 남은 개수 더해주기
                }
            }
        } else { // paper[x][y] == 0 인 경우 오른쪽으로 이동
            dfs(x, y + 1, cnt);
        }
    }

    // 색종이 붙이고 떼는 것 한 번에 구현(0:붙이기, 1:떼기)
    public static void attach(int x, int y, int size, int state) {
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                paper[i][j] = state;
            }
        }
    }

    // 색종이 크기만큼 모두 1인지 확인
    public static boolean isAttach(int x, int y, int size) {
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (i < 0 || i >= 10 || j < 0 || j >= 10) return false;
                if (paper[i][j] != 1) return false;
            }
        }
        return true;
    }
}