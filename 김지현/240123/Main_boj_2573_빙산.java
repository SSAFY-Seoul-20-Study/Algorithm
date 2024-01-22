import java.io.*;
import java.util.*;

public class Main_boj_2573_빙산 {
    static int N, M, time;
    static int[][] map, map_copy;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static ArrayDeque<int[]> q;
    static boolean[][] v;

    public static void main(String[] args) throws Exception{
//        System.setIn(new FileInputStream("res/input_boj_2573.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        time = 0;

        map_copy = new int[N][M];
        map = new int[N][M];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++){
                map_copy[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        map = copy_map(map_copy);

        int island_cnt = count_island();
        if(island_cnt > 1) {
            System.out.println(0);
        }

        while(true){
            time++;
            melted();
            map_copy = copy_map(map);
//            print_map();
            island_cnt = count_island();
            if(island_cnt > 1) {
                System.out.println(time);
                break;
            }
            if(island_cnt == 0){
                System.out.println(0);
                break;
            }
        }
    }

    private static void melted(){
        for(int i=1; i<N-1; i++){
            for(int j=1; j<M-1; j++){
                if(map_copy[i][j] > 0){
                    // 0 개수 세기
                    int zero_count = 0;
                    for(int d=0; d<4; d++){
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if(isRange(nx, ny) && map_copy[nx][ny] == 0) zero_count++;
                    }
                    if(zero_count > 0){
                        map[i][j] -= zero_count;
                        if(map[i][j]<0) map[i][j] = 0; // 최소 0으로 맞추기
                    }
                }
            }
        }
    }

    private static int count_island(){
        q = new ArrayDeque<>();
        v = new boolean[N][M];
        int island_cnt = 0;

        for(int i=1; i<N-1; i++){
            for(int j=1; j<M-1; j++){
                if(map[i][j] > 0 && !v[i][j]){
                    v[i][j] = true;
                    island_cnt++;
                    bfs(i, j);
                }
            }
        }
        return island_cnt;
    }

    private static void bfs(int x, int y){
        q.offer(new int[]{x, y});

        while(!q.isEmpty()){
            int[] now = q.poll();
            for(int d=0; d<4; d++){
                int nx = now[0] + dx[d];
                int ny = now[1] + dy[d];
                if(isRange(nx, ny) && map[nx][ny] > 0 && !v[nx][ny]){
                    q.offer(new int[]{nx, ny});
                    v[nx][ny] = true;
                }
            }
        }
    }
    private static boolean isRange(int x, int y){
        if(0<=x && x<N && 0<=y && y<M) return true;
        return false;
    }

    private static int[][] copy_map(int[][] original_map){
        int[][] copy_map = new int[N][M];
        for(int i=1; i<N-1; i++){
            for(int j=1; j<M-1; j++){
                copy_map[i][j] = original_map[i][j];
            }
        }
        return copy_map;
    }

    private static void print_map(){
        for(int i=0; i<N; i++) System.out.println(Arrays.toString(map[i]));
    }
}
