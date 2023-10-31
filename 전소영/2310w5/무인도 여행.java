import java.util.*;

class Solution {

    static int n, m;
    static int[][] map;
    static boolean[][] visited;
    static ArrayDeque<int[]> queue = new ArrayDeque<>();
    static ArrayList<Integer> ans = new ArrayList<>();
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public int[] solution(String[] maps) {

        n = maps.length;
        m = maps[0].length();
        map = new int[n][m];
        visited = new boolean[n][m];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                char ch = maps[i].charAt(j);
                if(ch != 'X') map[i][j] = ch - '0';
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(!visited[i][j] && map[i][j] > 0) {
                    ans.add(bfs(i, j));
                }
            }
        }

        int[] answer = new int[ans.size()];
        if(ans.size() == 0) {
            answer = new int[] {-1};
        }
        else {
            Collections.sort(ans);
            for(int i = 0; i < ans.size(); i++) {
            answer[i] = ans.get(i);
            }
        }

        return answer;
    }

    private static int bfs(int x, int y) {

        int sum = map[x][y];
        visited[x][y] = true;
        queue.add(new int[] {x, y});

        while(!queue.isEmpty()) {

            int[] xy = queue.poll();
            for(int d = 0; d < 4; d++) {
                int xx = xy[0] + dx[d];
                int yy = xy[1] + dy[d];

                if(xx < 0 || xx >= n || yy < 0 || yy >= m) continue;
                if(!visited[xx][yy] && map[xx][yy] > 0) {
                    sum += map[xx][yy];
                    visited[xx][yy] = true;
                    queue.add(new int[] {xx, yy});
                }
            }
        }

        return sum;
    }
}
