import java.util.*;
import java.io.*;

public class Solution_pro_아이템줍기 {
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        // 입력받기
        int answer = 0;
        int max_x = 0;
        int max_y = 0;
        for (int[] r : rectangle) {
            max_x = Math.max(r[2], max_x);
            max_y = Math.max(r[3], max_y);
        }
        // System.out.println(max_x + " "+max_y);
        int[][] map = new int[max_x * 2 + 1][max_y * 2 + 1];

        for (int[] r : rectangle) {
            // [r[0]*2, r[1]*2] ~ [r[2]*2, r[3]*2]
            // 테두리만
            int s_x = r[0] * 2, s_y = r[1] * 2, e_x = r[2] * 2, e_y = r[3] * 2;
            for (int i = s_x; i <= e_x; i++) {
                for (int j = s_y; j <= e_y; j++) {
                    // 다른 사각형의 내부라면
                    if (map[i][j] == 2) {
                        continue;
                    }
                    // 테두리 1로 채우기
                    if (i == s_x || i == e_x || j == s_y || j == e_y) {
                        map[i][j] = 1;
                    } else {
                        map[i][j] = 2; // 내부는 2로 채우기
                    }
                }
            }
        }
        // for(int[] m : map)System.out.println(Arrays.toString(m));

        int ans = bfs(new int[]{characterX * 2, characterY * 2}, new int[]{itemX * 2, itemY * 2}, map);
        System.out.println(ans);
        answer = ans / 2;

        return answer;
    }


    private int bfs(int[] start, int[] end, int[][] map) {
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        int r = map.length, c = map[0].length;
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] v = new boolean[r][c];

        q.offer(new int[]{start[0], start[1], 0});
        v[start[0]][start[1]] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nx = cur[0] + dx[d];
                int ny = cur[1] + dy[d];
                if (1 <= nx && nx < r && 1 <= ny && ny < c && map[nx][ny] == 1 && !v[nx][ny]) {
                    if (nx == end[0] && ny == end[1]) return cur[2] + 1;

                    q.offer(new int[]{nx, ny, cur[2] + 1});
                    v[nx][ny] = true;
                }
            }

        }
        return -1;
    }
}

