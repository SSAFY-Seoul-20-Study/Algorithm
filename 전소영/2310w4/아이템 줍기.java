import java.util.*;

class Solution {
    
    static final int LEN = 51 * 2;
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        
        characterX *= 2;
        characterY *= 2;
        itemX *= 2;
        itemY *= 2;
        
        boolean[][] map = new boolean[LEN][LEN];
        boolean[][] visited = new boolean[LEN][LEN];
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        
        // 입력값 받아 직사각형 내부 true로 채우기
        for(int i = 0; i < rectangle.length; i++) {
            int x1 = rectangle[i][0] * 2;
            int y1 = rectangle[i][1] * 2;
            int x2 = rectangle[i][2] * 2;
            int y2 = rectangle[i][3] * 2;
            
            for(int r = x1; r <= x2; r++) {
                for(int c = y1; c <= y2; c++) {
                    map[r][c] = true;
                }
            }
        }
        
        // 경계선 남겨두고 false로 채우기
        for(int i = 0; i < rectangle.length; i++) {
            int x1 = rectangle[i][0] * 2 + 1;
            int y1 = rectangle[i][1] * 2 + 1;
            int x2 = rectangle[i][2] * 2 - 1;
            int y2 = rectangle[i][3] * 2 - 1;
            
            for(int r = x1; r <= x2; r++) {
                for(int c = y1; c <= y2; c++) {
                    map[r][c] = false;
                }
            }
        }
        
        // BFS 탐색하기
        visited[characterX][characterY] = true;
        queue.add(new int[] {characterX, characterY, 0});
        
        while(!queue.isEmpty()) {
            
            int[] xyd = queue.poll();
            for(int d = 0; d < 4; d++) {
                int xx = xyd[0] + dx[d];
                int yy = xyd[1] + dy[d];
                
                if(xx == itemX && yy == itemY) return (xyd[2] + 1) / 2;
                if(xx < 0 || xx >= LEN || yy < 0 ||  yy >= LEN || visited[xx][yy] || !map[xx][yy]) continue;
                visited[xx][yy] = true;
                queue.add(new int[] {xx, yy, xyd[2] + 1});
            }
        }
        
        return -1;
    }

}
