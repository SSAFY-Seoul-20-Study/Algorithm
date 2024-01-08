import java.io.*;
import java.util.*;

public class Bj_G3_2146_다리만들기 {
    static int N;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static int minDist = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        StringTokenizer st;
        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        visited = new boolean[N][N];
        int num = 2;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(map[i][j]==1 && !visited[i][j]){
                    divideLand(i,j,num);
                    num++;
                }
            }
        }
        visited = new boolean[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(map[i][j]!=0 && !visited[i][j]){
                    makeBridge(i,j,map[i][j]);
                }
            }
        }
        System.out.println(minDist);
        br.close();
    }
    static void divideLand(int i,int j,int num){
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{i,j});
        visited[i][j] = true;
        map[i][j] = num;

        while(!queue.isEmpty()){
            int[] poll = queue.poll();
            for(int d=0;d<4;d++){
                int nx = poll[0] + dx[d];
                int ny = poll[1] + dy[d];
                if(isAvailable(nx,ny) && map[nx][ny]==1){
                    queue.offer(new int[]{nx,ny});
                    visited[nx][ny] = true;
                    map[nx][ny] = num;
                }
            }
        }
    }
    static void makeBridge(int x,int y,int landNum){
        ArrayDeque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(x,y,0));
        visited[x][y] = true;

        boolean[][] currentVisited = new boolean[N][N];
        currentVisited[x][y] = true;

        while(!queue.isEmpty()){
            Point poll = queue.poll();
            for(int d=0;d<4;d++){
                int nx = poll.x + dx[d];
                int ny = poll.y + dy[d];
                if(!isAvailable(nx,ny)) continue;
                if(map[nx][ny]==landNum){
                    visited[nx][ny] = true;
                    continue;
                }
                if(currentVisited[nx][ny]) continue;
                if(map[nx][ny]==0){
                    currentVisited[nx][ny]=true;
                    queue.offer(new Point(nx,ny,poll.dist+1));
                }
                else{
                    minDist = Math.min(minDist,poll.dist);
                    return;
                }
            }
        }
    }
    static boolean isAvailable(int nx,int ny){
        if(0<=nx && nx<N && 0<=ny && ny<N) return true;
        return false;
    }
    static class Point{
        int x,y,dist;
        public Point(int x,int y,int dist){
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
}
