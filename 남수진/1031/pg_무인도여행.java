import java.util.*;

class Solution {
    static boolean[][] v; 
    public int[] solution(String[] maps) {
        
        List<Integer> list = new ArrayList<>();
        
        v = new boolean[maps.length][maps[0].length()];
        for(int i = 0; i < maps.length; i++) {
            for(int j = 0; j < maps[i].length(); j++) {
                if(maps[i].charAt(j) != 'X' && !v[i][j]){
                    list.add(bfs(i, j, maps));
                }
            }
        }
        
        if(list.isEmpty()) {
            return new int[] {-1};
        }
        
        int[] answer = new int[list.size()];
        

        for(int i = 0; i < list.size(); i++){
            answer[i] = list.get(i);
        }
        Arrays.sort(answer);
        
        
        return answer;
    }
    
    static int bfs(int x, int y, String[] maps) {
        int cnt = 0;
        
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        
        ArrayDeque<int[] > q = new ArrayDeque<>();
        
        q.offer(new int[] {x, y});
        v[x][y] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0];
            int cy = cur[1];
            
            cnt += maps[cx].charAt(cy) - '0';

            for(int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                if(nx < 0 || nx >= maps.length || ny < 0 || ny >= maps[nx].length()) continue;          
                if(maps[nx].charAt(ny) != 'X' && !v[nx][ny]){
                    q.offer(new int[] {nx, ny});
                    v[nx][ny] = true;
                }
            }
            
        }
        
        
        return cnt;
    }
}
