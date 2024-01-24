import java.util.*;
import java.io.*;
public class Main {
    static int N, M;
    static int [][]board, nextMelt;
    static boolean[][] visited;
    static int dx[] = {-1,1,0,0}, dy[] = {0,0,-1,1};
    static boolean flag = true;
    static int ans, year;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j <M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while(flag){
            boolean tmpFlag = true;
            int count = 0;
            nextMelt = new int[N][M];
            visited = new boolean[N][M];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if(board[i][j] != 0 && !visited[i][j]) {
                        tmpFlag = false;
                        count++;
                        bfs(i,j);
                    }
                }
            }
            if(count >= 2){
                ans = count;
                break;
            }
            else if(tmpFlag){
                ans = 0;
                break;
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    board[i][j] -= nextMelt[i][j];
                    if(board[i][j] < 0) board[i][j] = 0;
                }
            }
            year++;

        }

        if(ans > 0) System.out.println(year);
        else System.out.println(0);
    }
    static void bfs(int x, int y){
        ArrayDeque<int []> q = new ArrayDeque<>();
        visited[x][y] = true;
        q.add(new int[] {x, y});
        while(!q.isEmpty()){
            int cur[] = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if(nx<0 || nx>=N || ny<0 || ny>=M) continue;
                if(board[nx][ny] != 0 && !visited[nx][ny]){
                    q.add(new int[] {nx, ny});
                    visited[nx][ny] = true;
                }
                else if(board[nx][ny] == 0) nextMelt[cur[0]][cur[1]]++;
            }
        }
    }

}