package a0123;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main_bj_2573_빙산 {

    static int N, M;
    static int[][] map;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static boolean[][] v;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0;
        while(true) {
            int[][] copy = new int[N][M];

            int zero = 0;
            for(int i = 0; i< N; i++) {
                for(int j = 0; j < M; j++) {
                    if(map[i][j] == 0) zero++;
                    copy[i][j] = map[i][j];
                }
            }
            if(zero == N * M) {
                ans = 0;
                break;
            }

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    if (copy[i][j] == 0) continue;
                    int cnt = melt(i, j, copy);
                    map[i][j] = (map[i][j] >= cnt ? map[i][j] - cnt : 0);
                }
            }

            v = new boolean[N][M];

            int count = 0;
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    if(v[i][j] || map[i][j] == 0) continue;
                    separate(i,j);
                    count++;
                }
            }
            ans++;

            if(count >= 2) break;

        }

        System.out.println(ans);
    }

    static int melt(int x, int y, int[][] copy) {
        int cnt = 0;
        for(int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(isValid(nx, ny) && copy[nx][ny] == 0) cnt++;
        }
        return cnt;
    }
    static void separate(int x, int y) {
        ArrayDeque<int[]> q = new ArrayDeque<>();

        q.offer(new int[] {x, y});
        v[x][y] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0];
            int cy = cur[1];

            for(int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if(isValid(nx, ny) && !v[nx][ny] &&map[nx][ny] != 0 ){
                    q.offer(new int[] {nx, ny});
                    v[nx][ny] = true;
                }

            }

        }
    }

    static boolean isValid(int x, int y){
        if(x < 0 || x >= N || y < 0 || y >= M) return false;
        else return true;
    }
}
