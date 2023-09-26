import java.io.*;
import java.util.*;

// 수영장 만들기

public class BJ1113 {
    
    static int n, m;
    static int ans;
    static int maxHeight;
    static int[][] map;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static ArrayDeque<int[]> queue = new ArrayDeque<>();
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        
        for(int i = 0; i < n; i++) {
            String line = br.readLine();
            for(int j = 0; j < m; j++) {
                map[i][j] = line.charAt(j) - '0';
                maxHeight = Integer.max(maxHeight, map[i][j]);
            }
        }
        
        for(int h = 2; h <= maxHeight; h++) {
            solution(h);
        }
        System.out.println(ans);
    }
    
    private static void solution(int height) {

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(map[i][j] < height) {
                    ans += bfs(i, j, height);
                }
            }
        }
    }
    
    private static int bfs(int x, int y, int height) {
        
        boolean edge = false;
        int cnt = 0;

        map[x][y]++;
        queue.add(new int[] {x, y});
        if(isEdge(x, y)) edge = true;
        cnt++;
        
        while(!queue.isEmpty()) {
            
            int[] poll = queue.poll();
            for(int i = 0; i < 4; i++) {
                
                int xx = poll[0] + dx[i];
                int yy = poll[1] + dy[i];
                
                if(xx < 0 || xx >= n || yy < 0 || yy >= m || map[xx][yy] >= height) continue;
                map[xx][yy]++;
                queue.add(new int[] {xx, yy});
                if(isEdge(xx, yy)) edge = true;
                cnt++;
            }
        }

        if(edge) cnt = 0;
        return cnt;
    }
    
    private static boolean isEdge(int r, int c) {
        
        if(r == 0 || r == n - 1 || c == 0 || c == m - 1) return true;
        return false;
    }
}
