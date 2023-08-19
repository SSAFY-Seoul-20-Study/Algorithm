import java.util.*;
import java.io.*;
public class Main {
    static int N,M,board[][], dx[] = {0,-1,0,1}, dy[] = {-1,0,1,0};
    static int backDx[] = {1,0,-1,0}, backDy[] = {0,-1,0,1};
    static int r,c,d, ans;
    static boolean visited [][];
    public static void main(String[] args) throws Exception{
        System.setIn(new FileInputStream("./study/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        visited = new boolean[N][M];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        move(r,c);
        System.out.println(ans);
//        for(int i =0; i<N; i++) System.out.println(Arrays.toString(visited[i]));

    }
    static void move(int x, int y){
        int count= 0;
        ans = 1;
        visited[x][y] = true;
        while(true){
            int nx = x + dx[d];
            int ny = y + dy[d];
            //청소 공간이 있는 경우
            if(0<=nx && nx<N && 0<=ny && ny<M && !visited[nx][ny] && board[nx][ny] == 0){
                x = nx;
                y = ny;
                visited[x][y] = true;
                ans++;
                count = 0;
                if(d == 0) d=3;
                else d--;
                continue;
            }
            //청소 공간이 없으므로 회전만
            else{
                if(d == 0) d=3;
                else d--;
                count++;
            }
            if(count == 4){
                nx = x + backDx[d];
                ny = y + backDy[d];
                if(0<=nx && nx<N && 0<=ny && ny<M && board[nx][ny] == 1){
                    break;
                }
                else{
                    x = nx;
                    y = ny;
                    visited[x][y] = true;
                    count = 0;
                }
            }
        }
    }
}
