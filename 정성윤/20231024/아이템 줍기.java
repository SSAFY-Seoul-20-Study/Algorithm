import java.util.*;

class Solution {
    public static int[][] xline;
    public static int[][] yline;
    public static int[][] board;
    public static int[][] edge;
    public static int N,M;
    public static boolean[][] visited;
    public static boolean[][] edgevisited;
    
    public static int[] di = {-1,0,1,0};
    public static int[] dj = {0,1,0,-1};
    
    public static int ans = Integer.MAX_VALUE;
    
    public static void bfs(){
        board[0][0] = 1;
        ArrayDeque<int[]> ad = new ArrayDeque<>();
        ad.offer(new int[]{0,0});
        while(!ad.isEmpty()){
            int[] now = ad.poll();
            for(int d=0;d<4;d++){
                int ni = now[0]+di[d];
                int nj = now[1]+dj[d];
                if(ni>=0 && ni<N && nj>=0 && nj<M && visited[ni][nj] == false){
                    if(d==0 && xline[N-1-ni][nj] > 0){
                        xline[N-1-ni][nj]++;
                        continue;
                    }
                    else if(d== 1 && yline[nj][N-1-ni]>0){
                        yline[nj][N-1-ni]++;
                        continue;
                    }
                    else if(d== 2 && xline[N-1-now[0]][nj]>0){
                        xline[N-1-now[0]][nj]++;
                        continue;
                    }
                    else if(d== 3 && yline[now[1]][N-1-ni]>0){
                        yline[now[1]][N-1-ni]++;
                        continue;
                    }
                    else{
                        board[ni][nj] = 1;
                        visited[ni][nj] = true;
                        ad.offer(new int[] {ni,nj});
                    }  
                }
            }
        }
    }
    
    public static void dfs(int cx,int cy, int ix, int iy, int distance){
        if(cx == ix && cy == iy){
            edgevisited[iy][ix] = false;
            ans = Math.min(ans,distance);
            return;
        }
        
        for(int d = 0; d<4;d++){
            int ni = cy + di[d];
            int nj = cx + dj[d];
            if(ni>=0 && ni< N && nj>=0 && nj<M && edge[ni][nj] == 1 && edgevisited[ni][nj] == false){
                int flag = 0;
                if(d==0 && yline[cx][N-1-cy]==2){
                    flag = 1;
                }
                else if(d==1 && xline[N-1-cy][cx]==2){
                    flag = 1;
                }
                else if(d==2 && yline[cx][N-1-ni]==2){
                    flag = 1;
                }
                else if(d==3 && xline[N-1-cy][nj]==2){
                    flag = 1;
                }
                if (flag == 1){
                    edgevisited[ni][nj] = true;
                    dfs(nj,ni,ix,iy,distance+1);
                }
            }
        }
        return;
    }
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = 0;
        int largex = 0;
        int largey = 0;
        for(int[] dot:rectangle){
            largex = Math.max(largex,dot[2]);
            largey = Math.max(largey,dot[3]);
        }
        N = largey+1;
        M = largex+1;
        board = new int[N][M];
        edge = new int[N][M];
        visited = new boolean[N][M];
        edgevisited = new boolean[N][M];
        xline = new int[N][M];
        yline = new int[M][N];

        for(int[] dot:rectangle){
            int startx = dot[0];
            int starty = dot[1];
            int endx = dot[2];
            int endy = dot[3];
            
            //board 저장용
            for(int i=starty;i<endy;i++){
                yline[startx][i] = 1;
                yline[endx][i] = 1;
            }
            for(int i=startx;i<endx;i++){
                xline[starty][i] = 1;
                xline[endy][i] = 1;
            }
            //edge 저장용
            for(int i= N-1-endy;i<=N-1-starty;i++){
                edge[i][startx] = 1;
                edge[i][endx] = 1;
            }
            for(int j=startx;j<=endx;j++){
                edge[N-1-starty][j] = 1;
                edge[N-1-endy][j] = 1;
            }
        }

        bfs();
        
        edgevisited[N-1-characterY][characterX] = true;
        dfs(characterX,N-1-characterY,itemX,N-1-itemY,0);
        answer = ans;
        return answer;
    }
}