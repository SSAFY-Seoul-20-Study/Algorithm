import java.util.*;
import java.io.*;

// 138324KB	296ms
public class Main_boj_2146_다리만들기 {
    static int N, min_res = 200;
    static int[][] map;
    static ArrayDeque<int[]> q = new ArrayDeque<>();
    static boolean[][] v = new boolean[N][N];
    static boolean[][] vv;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("res/input_boj_2146.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 각 섬에 고유번호 부여
        bfs_island_split();

        // 다리 놓기
        bridge();

        System.out.println(min_res);
    }

    private static void bridge(){
        v = new boolean[N][N];

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(map[i][j] > 0){
                    v[i][j] = true;
                    bfs_bridge(i, j, map[i][j]);
                }
            }
        }
    }

    private static void bfs_bridge(int x, int y, int island_num){
        q = new ArrayDeque<>();
        vv = new boolean[N][N];

        q.offer(new int[]{x, y, 0});
        vv[x][y] = true;

        while(!q.isEmpty()){
            int[] now = q.poll();
            for(int d=0; d<4; d++){
                int nx = now[0] + dx[d];
                int ny = now[1] + dy[d];
                if(isRange(nx, ny) && !vv[nx][ny] && map[nx][ny] != island_num){
                    if(map[nx][ny] == 0){
                        q.offer(new int[]{nx, ny, now[2]+1});
                        vv[nx][ny] = true;
                    } else {
                        min_res = Math.min(min_res, now[2]);
                        return;
                    }
                }
            }
        }
    }

    private static void bfs_island_split(){
        int island_count = 0;
        v = new boolean[N][N];

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(!v[i][j] && map[i][j] == 1){
                    island_count++;
                    bfs_island(i, j, island_count);
                }
            }
        }
    }
    private static void bfs_island(int x, int y, int island_num){
        q = new ArrayDeque<>();

        q.offer(new int[]{x, y});
        map[x][y] = island_num; // 이 코드 없으면, island 크기 1일 때, 체크가 안됨.
        while(!q.isEmpty()) {
            int[] now = q.poll();
            for(int d=0; d<4; d++){
                int nx = now[0] + dx[d];
                int ny = now[1] + dy[d];
                if(isRange(nx, ny) && map[nx][ny] == 1 && !v[nx][ny]){
                    q.offer(new int[]{nx, ny});
                    v[nx][ny] = true;
                    map[nx][ny] = island_num;
                }
            }
        }
    }

    private static boolean isRange(int x, int y){
        if(0 <= x && x < N && 0 <= y && y < N) return true;
        return false;
    }
}
