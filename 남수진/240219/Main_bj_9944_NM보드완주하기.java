import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_9944_NM보드완주하기 {

    static int N, M;
    static char[][] map;
    static boolean[][] v;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int ans = Integer.MAX_VALUE;
    static int emptySum = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = "";

        StringBuilder sb = new StringBuilder();

        int tc = 1;

        while((str = br.readLine()) != null) { // 입력이 끝날 때까지 반복
            StringTokenizer st = new StringTokenizer(str);
            ans = Integer.MAX_VALUE;
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            map = new char[N][M];
            emptySum = 0;

            for (int i = 0; i < N; i++) {
                String tmp = br.readLine();
                for (int j = 0; j < M; j++) {
                    map[i][j] = tmp.charAt(j);
                    if(map[i][j] == '.') emptySum++;
                }
            }

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    if(map[i][j] == '*') continue;
                    v = new boolean[N][M];
                    v[i][j] = true;
                    locate(i, j, 0, 1);
                }
            }
            
            if(ans == Integer.MAX_VALUE) ans = -1;
            sb.append("Case ").append(tc++).append(": ").append(ans).append("\n");
        }
        System.out.println(sb.toString());
    }

    static void locate(int cx, int cy, int cnt, int sum) {
        if(cnt > ans) return;

        if(sum == emptySum) {
            ans = cnt;
            return;
        }

        for(int i = 0; i < 4; i++) {
            int nx = cx;
            int ny = cy;

            int tmp = 0;
            while(true) {
                nx += dx[i];
                ny += dy[i];

                if(!isRange(nx, ny) || v[nx][ny] || map[nx][ny] == '*' ) {
                    nx -= dx[i];
                    ny -= dy[i];
                    break;
                }

                v[nx][ny] = true;
                tmp++;
            }

            if(cx != nx || cy != ny) {
                locate(nx, ny, cnt + 1, sum + tmp);

                for(int j = 1; j <= tmp; j++) {
                    int tmpX = cx + dx[i] * j;
                    int tmpY = cy + dy[i]*j;
                    v[tmpX][tmpY] = false;
                }
            }


        }
    }

    static boolean isRange(int x, int y) {
        if(x >= N || x < 0 || y >= M || y < 0 ) return false;
        return true;
    }
}
