import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
public class 다리만들기 {
    static int N, island = 1, result = Integer.MAX_VALUE;
    static int [][] board;
    static boolean [][] visited;
    static int [] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(board[i][j] != 0 && !visited[i][j]){
                    bfs(i,j);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(board[i][j] != 0){
                    int []ans = find(i,j,board[i][j]);
                    if(ans[0] == 1) result = Math.min(result, ans[1]);
                }
            }
        }
        System.out.println(result);

    }
    static void bfs(int x, int y){
        Deque<int []> q = new ArrayDeque<>();
        q.add(new int [] {x,y});
        visited[x][y] = true;
        while(!q.isEmpty()){
            int []cur = q.poll();
            board[cur[0]][cur[1]] = island;
            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if(0>nx || nx >= N || 0 > ny || ny >= N) continue;
                if(!visited[nx][ny] && board[nx][ny] != 0){
                    q.add(new int [] {nx,ny});
                    visited[nx][ny] = true;
                }
            }
        }
        island++;
    }
    static int[] find(int x, int y, int flag){
        int ans[] = new int[2];
        Deque<int []> q = new ArrayDeque<>();
        visited = new boolean[N][N];
        q.add(new int [] {x,y, 0});
        visited[x][y] = true;
        while(!q.isEmpty()){
            int []cur = q.poll();
            for (int i=0; i<4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if(0>nx || nx >= N || 0 > ny || ny >= N) continue;
                if(!visited[nx][ny] && board[nx][ny] == 0){
                    q.add(new int [] {nx,ny, cur[2]+1});
                    visited[nx][ny] = true;
                }
                if(board[nx][ny] != 0 && flag != board[nx][ny]){
                    return new int[] {1, cur[2]};
                }
            }
        }
        return ans;
    }
}
