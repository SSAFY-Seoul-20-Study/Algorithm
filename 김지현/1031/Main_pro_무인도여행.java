import java.util.*;

// 25분 소요
public class Main_pro_무인도여행 {
    int[][] map;
    boolean[][] v;

    final int[] dx = {-1, 0, 1, 0};
    final int[] dy = {0, -1, 0, 1};

    public int[] solution(String[] maps) {
        List<Integer> ans = new ArrayList<>();
        map = new int[maps.length][maps[0].length()];
        v = new boolean[maps.length][maps[0].length()];

        for (int i = 0; i < maps.length; i++) {
            String m = maps[i];
            for (int j = 0; j < m.length(); j++) {
                char c = m.charAt(j);
                if (c == 'X') {
                    map[i][j] = 0;
                } else {
                    map[i][j] = c - '0';
                }
            }
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (!v[i][j] && map[i][j] > 0) {
                    v[i][j] = true;
                    ans.add(bfs(i, j));
                }
            }
        }

        int[] answer;
        if (ans.size() > 0) {
            answer = new int[ans.size()];
            int idx = 0;
            for (int a : ans) {
                answer[idx++] = a;
            }
            Arrays.sort(answer);
        } else answer = new int[]{-1};

        return answer;
    }

    private int bfs(int x, int y) {
        Queue<int[]> q = new ArrayDeque<>();
        int result = 0;

        result += map[x][y];
        q.offer(new int[]{x, y});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nx = cur[0] + dx[d];
                int ny = cur[1] + dy[d];
                if (isRange(nx, ny) && !v[nx][ny] && map[nx][ny] > 0) {
                    result += map[nx][ny];
                    v[nx][ny] = true;
                    q.offer(new int[]{nx, ny});
                }
            }
        }
        return result;
    }

    private boolean isRange(int x, int y) {
        if (0 <= x && x < map.length && 0 <= y && y < map[0].length) return true;
        return false;
    }
}
/*
maps / result
["X591X","X1X5X","X231X", "1XXX1"]	[1, 1, 27]
["XXX","XXX","XXX"]	[-1]
 */
