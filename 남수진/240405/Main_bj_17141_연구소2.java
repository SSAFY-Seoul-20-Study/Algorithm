import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int M, N;
    static int[][] map;
    static int[][] v;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static int ans = -1;
    static ArrayList<int[]> virus;
    static int[][] check;
    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        virus = new ArrayList<>();
        check = new int[M][2];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 2) {
                    virus.add(new int[] {i, j});
                }
            }
        }

        recur(0, 0);

        System.out.println(ans);
    }

    static void recur(int cnt, int start) {
        if(cnt == M) {
            v = new int[N][N];
            bfs();
            int tmp = searchMin() - 1;
            if(tmp != -1) {
                if(ans == -1) ans = tmp;
                else ans = Math.min(ans, tmp);
            }
            return;
        }

        for(int i = start; i < virus.size(); i++){
            int[] tmp = virus.get(i);
            check[cnt][0] = tmp[0];
            check[cnt][1] = tmp[1];
            recur(cnt + 1, i + 1);
        }

    }

    static int searchMin() {
        int max = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(map[i][j] != 1 && v[i][j] == 0) return 0;
                max = Math.max(v[i][j], max);
            }
        }
        return max;
    }

    static void bfs() {
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));

        for(int i = 0; i < M; i++) {
            q.offer(new int[]{check[i][0], check[i][1], 1});
            v[check[i][0]][check[i][1]] = 1;
        }

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0];
            int cy = cur[1];
            int time = cur[2];

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (!isRange(nx, ny) || map[nx][ny] == 1 || v[nx][ny] != 0) continue;

                v[nx][ny] = time + 1;
                q.offer(new int[]{nx, ny, v[nx][ny]});
            }

        }
    }

    static boolean isRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}
