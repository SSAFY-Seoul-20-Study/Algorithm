import java.util.*;
class Solution {
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = 0;
        
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        
        int[][] map = new int[102][102];
        
        
        for(int t = 0; t < rectangle.length;t++) {
            int x1 = rectangle[t][0] * 2;
            int y1 = rectangle[t][1] * 2;
            int x2 = rectangle[t][2] * 2;
            int y2 = rectangle[t][3] * 2;
            
            for(int i = x1; i <= x2; i++) {
                for(int j = y1 ; j <= y2; j++){
                    if(map[i][j] == 2) continue;
                    if( i == x1 || i == x2) {
                        map[i][j] = 1;
                        continue;
                    }
                    if( j == y1 || j == y2) {
                        map[i][j] = 1;
                        continue;
                    }
                    map[i][j] = 2;
                }
            }
        }
        
        ArrayDeque<int[]> q= new ArrayDeque<>();
        boolean[][] v = new boolean[102][102];
        q.offer(new int[] {characterX * 2, characterY * 2, 1});
        v[characterX * 2][characterY * 2] = true;
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0];
            int cy = cur[1];
            int depth = cur[2];
            
            if(cx == itemX * 2 && cy == itemY * 2){
                answer = depth / 2;
                break;
            }
            
            for(int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                
                if(map[nx][ny] == 1 && !v[nx][ny]) {
                    q.offer(new int[] {nx, ny, depth + 1});
                    v[nx][ny] = true;
                }
            }
        }
        
        return answer;
    }
}
