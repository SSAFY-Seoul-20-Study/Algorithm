import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int R, C;
    static char[][] map;
    static int[] dx ={0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static int[][] swans;
    static ArrayDeque<int []> iceQ, swanQ;
    static boolean[][] swanV;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];
        iceQ = new ArrayDeque<>();
        swans = new int[2][2];
        swanQ = new ArrayDeque<>();
        swanV = new boolean[R][C];

        int idx = 0;
        for(int i = 0; i < R; i++) {
            String input = br.readLine();
            for(int j = 0; j < C; j++) {
                map[i][j] = input.charAt(j);
                if(map[i][j] != 'X') iceQ.offer(new int[] {i, j});
                if(map[i][j] == 'L') {
                    swans[idx][0] = i;
                    swans[idx++][1] = j;
                }
            }
        }

        swanQ.offer(new int[] {swans[0][0], swans[0][1]});
        swanV[swans[0][0]][swans[0][1]] = true;

        int ans = 0;

        while(!isConnect()) {
            melt();
            ans++;
        }

        System.out.println(ans);
    }

    static boolean isConnect() {
        ArrayDeque<int[]> q = new ArrayDeque<>();

        while(!swanQ.isEmpty()) {
            int[] cur = swanQ.poll();

            for(int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if(!isRange(nx, ny) || swanV[nx][ny]) continue;

                swanV[nx][ny] = true;

                if(nx == swans[1][0] && ny == swans[1][1]) {
                    return true;
                }
                else if(map[nx][ny] == 'X' ) q.offer(new int[]{nx, ny});
                else swanQ.offer(new int[] {nx, ny});
            }
        }

        swanQ = q;
        return false;
    }

    static void melt() {

        int size = iceQ.size();

        for(int i =0 ; i < size; i++) {
            int[] cur = iceQ.poll();

            for(int j = 0; j < 4; j++) {
                int nx = cur[0] + dx[j];
                int ny = cur[1] + dy[j];

                if(!isRange(nx, ny) || map[nx][ny] != 'X') continue;

                map[nx][ny] = '.';
                iceQ.offer(new int[]{nx, ny});
            }
        }

    }

    static boolean isRange(int x, int y) {
        if(x < 0 || x >= R || y < 0 || y >= C) return false;
        return true;
    }
}
