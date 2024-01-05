package a0109;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_2146_다리만들기 {
    static int N;
    static int[][] map;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        for(int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int idx = 2;

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(map[i][j] == 1) {
                    island(i, j, idx++);
                }
            }
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(map[i][j] >= 2 && map[i][j] < idx) {
                    bridge(i, j, map[i][j]);
                }
            }
        }



        System.out.println(min);

    }

    static void bridge(int x, int y, int idx) {
        ArrayDeque<int[]> q = new ArrayDeque<>();

        boolean[][] v = new boolean[N][N];
        q.offer(new int[] {x, y, 0});
        v[x][y] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0];
            int cy = cur[1];
            int dist = cur[2];

            if(dist - 1 >= min) return;

            if(map[cx][cy] != 0 && map[cx][cy] != idx) {
                min = dist - 1;
                return;
            }

            for(int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if(!isRange(nx,ny)  || v[nx][ny] || map[nx][ny] == -1 || map[nx][ny] == idx) continue;

                q.offer(new int[]{nx, ny, dist + 1});
                v[nx][ny] = true;
            }

        }

    }

    static void island (int sx, int sy, int idx){

        ArrayDeque<int[]> q = new ArrayDeque<>();

        q.offer(new int[]{sx, sy});
        num(sx, sy, idx);

        while(!q.isEmpty()) {
            int cx = q.peek()[0];
            int cy = q.poll()[1];

            for(int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if(isRange(nx, ny) && map[nx][ny] == 1) {

                    num(nx, ny, idx);
                    q.offer(new int[] {nx, ny});
                }
            }

        }
    }

    static void num(int x, int y, int idx) {

        for(int i = 0; i < 4; i++) {
            int tx = x + dx[i];
            int ty = y + dy[i];

            if(isRange(tx, ty) && map[tx][ty] == 0) {
                map[x][y] = idx;
                break;
            }
        }
        if(map[x][y] == 1) map[x][y] = -1;
    }

    static boolean isRange(int x, int y) {
        if(x >= N || x < 0 || y >= N || y < 0 ) return false;
        else return true;
    }
}
