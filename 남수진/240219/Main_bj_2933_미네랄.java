package a0125;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_2933_미네랄 {

    static int R, C, N;
    static char[][] map;
    static boolean[][] visited;
    static int[] dx = {0, -1, 0, 1};
    static int[] dy = {1, 0, -1, 0};
    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];

        for(int i = 0; i < R; i++){
            String tmp = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = tmp.charAt(j);
            }
        }

        N = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        for(int tc = 0; tc < N; tc++) {

            int H = Integer.parseInt(st.nextToken());

            int[] target;
            if(tc % 2 == 0) target = smash(H, 0);
            else target = smash(H, 1);


            if(target[0] == R && target[1] == C) continue;

            for(int i = 0; i < 4; i++) {
                int nx = target[0] + dx[i];
                int ny = target[1] + dy[i];

                if(isRange(nx, ny) && map[nx][ny] == 'x') {
                    bfs(nx, ny);
                }

            }

        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < R; i++ ) {
            for(int j = 0; j < C; j++) {
                sb.append(map[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    static void bfs(int x, int y) {

        if(x == R - 1) return;

        ArrayDeque<int[]> q = new ArrayDeque<>();
        visited = new boolean[R][C];

        q.offer(new int[]{x, y});
        boolean isDown = true;
        visited[x][y] = true;
        ArrayList<int[]> arrayList = new ArrayList<>();
        arrayList.add(new int[] {x, y});

        while(!q.isEmpty()) {
            int cx = q.peek()[0];
            int cy = q.poll()[1];

            for(int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if(isRange(nx, ny) && map[nx][ny] == 'x' && !visited[nx][ny]) {
                    if(nx == R - 1) {
                        return;
                    }
                    q.offer(new int[] {nx, ny});
                    visited[nx][ny] = true;
                    arrayList.add(new int[] {nx, ny});
                }
            }
        }
        int max = R;
        if(isDown) {
            for(int j = 0; j < C; j++){
                int first = -1;
                int second = -1;
                for(int i = R - 1; i >= 0; i--) {
                    if(first == -1 && map[i][j] == '.') first = i;
                    if(map[i][j] == 'x' && !visited[i][j]) {
                        first = i - 1;
                    }
                    if(first != -1 && visited[i][j]) {
                        second = i;
                        max = Math.min(first - second, max);
                    }
                }
                if(second == -1) continue;

            }

            arrayList.sort((o1, o2) -> -Integer.compare(o1[0], o2[0]));

            if(max == R) return;
            for(int[] arr : arrayList) {
                int cx = arr[0];
                int cy = arr[1];

                map[cx][cy] = '.';
                map[cx + max][cy] = 'x';
            }
        }
    }

    static int[] smash(int h, int dir) {
        if(dir == 0) {
            for (int i = 0; i < C; i++) {
                if(map[R - h][i] == 'x') {
                    map[R - h][i] = '.';
                    return new int[]{R - h, i};
                }
            }
        } else {
            for (int i = C - 1; i >= 0; i--) {
                if(map[R - h][i] == 'x') {
                    map[R - h][i] = '.';
                    return new int[]{R - h, i};
                }
            }
        }
        return new int[] {R, C};
    }

    static boolean isRange(int x, int y){
        if(x >= R || x < 0 || y >= C || y < 0) return false;
        return true;
    }
}
