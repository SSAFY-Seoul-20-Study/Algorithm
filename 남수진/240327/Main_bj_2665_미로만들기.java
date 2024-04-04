import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main {

    static int N;
    static int[][] map;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        int[][] dist = new int[N][N];

        for(int i = 0; i < N; i++) {
            String tmp = br.readLine();
            for(int j = 0; j < N; j++) {
                map[i][j] = tmp.charAt(j) - '0';
                dist[i][j] = Integer.MAX_VALUE;
            }
        }


        ArrayDeque<int[]> q = new ArrayDeque<>();
        dist[0][0] = 0;

        q.offer(new int[] {0, 0});

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0];
            int cy = cur[1];

            for(int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if(!isRange(nx, ny)) continue;

                if(dist[nx][ny] > dist[cx][cy]) {
                    if(map[nx][ny] == 1) dist[nx][ny] = dist[cx][cy];
                    else dist[nx][ny] = dist[cx][cy] + 1;
                    q.offer(new int[] {nx, ny});
                }
            }

        }


        System.out.println(dist[N - 1][N - 1]);
    }

    static boolean isRange(int x, int y) {
        if(x < 0 || x >= N || y < 0 || y >= N) return false;
        return true;
    }
}
