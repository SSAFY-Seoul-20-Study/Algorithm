import java.util.*;
class Solution {
    static int board[][];
    static int N;
    static int dx[]={-1,1,0,0}, dy[]={0,0,-1,1};
    static int ix, iy;
    static int ans;
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        board = new int[101][101];
        ix = itemX;
        iy = itemY;
        int answer = 0;
        for(int []rec : rectangle){
            int x1 = rec[0]*2;
            int y1 = rec[1]*2;
            int x2 = rec[2]*2;
            int y2 = rec[3]*2;
            for(int i=x1; i<=x2; i++){
                for(int j=y1; j<=y2; j++){
                    if(board[i][j] ==2 )continue;
                    board[i][j] = 2;
                    if(i==x1 || i == x2 || j==y1 || j==y2){
                        board[i][j] = 1;
                    }
                }
            }
        }
        bfs(characterX*2,characterY*2);
        return ans/2;
    }
    static void bfs(int x, int y){
        boolean [][]visited=new boolean[101][101];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{x,y,0});
        while(!q.isEmpty()){
            int cur[]= q.poll();
            if(cur[0] == ix*2 && cur[1] == iy*2){
                ans =  cur[2];
                return;
            }
            for(int i=0; i<4; i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if(nx < 0 || nx>100 || ny <0 || ny>100) continue;
                if(!visited[nx][ny] && board[nx][ny] == 1){
                    visited[nx][ny] = true;
                    q.add(new int[] {nx, ny, cur[2] + 1});
                }
            }
        }

    }
}