package a0830;
import java.util.*;
import java.io.*;

public class 수영장만들기 {
    static int board[][],dx[] = {-1,1,0,0}, dy[] = {0,0,-1,1};
    static boolean visited[][];
    static int N, M;
    static int ans;
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("C:\\SSAFY\\work_algo\\algo\\input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(Character.toString(input.charAt(j)));}
        }

        //물의 높이를 1씩 높여나가면서 수영장 물의 양을 체크
        for (int h = 1; h <10 ; h++) {
            visited = new boolean[N][M];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j <M; j++) {
                    if(board[i][j] <= h && !visited[i][j]) bfs(i,j,h);
                }
            }
        }


        System.out.println(ans);


    }
    static void bfs(int x, int y, int flag){
        ArrayDeque<int []> q = new ArrayDeque<>();
        visited[x][y] = true;
        q.add(new int [] {x,y});
        int count = 1;
        boolean check = true;
        while(!q.isEmpty()){
            int cur[] = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                //flag로 관리를 해야지 바로 return을 해버리면 count 값이 남는다..?
                if(nx < 0 || nx >= N || ny < 0 || ny >=M) {
                    //return
                    check = false;
                    continue;
                }
                if(0<=nx && nx< N && 0<=ny && ny<M && !visited[nx][ny] && board[nx][ny] <= flag){
                    visited[nx][ny] = true;
                    q.add(new int [] {nx, ny});
                    count++;
                }
            }
        }
        if(check) ans += count;

    }
}
