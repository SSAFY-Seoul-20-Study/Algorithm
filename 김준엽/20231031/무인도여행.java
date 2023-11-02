import java.util.*;

class Solution {
    static int board[][], dx[] = { -1, 1, 0, 0 }, dy[] = { 0, 0, -1, 1 };
    static int N, M;
    static boolean visited[][];
    static ArrayList<Integer> a = new ArrayList<>();

    public int[] solution(String[] maps) {
        int[] answer = {};
        N = maps.length;
        M = maps[0].length();
        board = new int[N][M];
        visited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < maps[i].length(); j++) {
                if (maps[i].charAt(j) == 'X')
                    board[i][j] = -1;
                else
                    board[i][j] = (int) maps[i].charAt(j) - 48;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] != -1 && !visited[i][j])
                    bfs(i, j);
            }
        }
        answer = new int[a.size()];
        for (int i = 0; i < a.size(); i++) {
            answer[i] = a.get(i);
        }
        Arrays.sort(answer);
        if (answer.length == 0)
            return new int[] { -1 };
        return answer;
    }

    static void bfs(int x, int y) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        int count = board[x][y];
        visited[x][y] = true;
        q.add(new int[] { x, y });
        while (!q.isEmpty()) {
            int cur[] = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if (nx >= N || nx < 0 || ny >= M || ny < 0)
                    continue;
                if (!visited[nx][ny] && board[nx][ny] != -1) {
                    count += board[nx][ny];
                    visited[nx][ny] = true;
                    q.add(new int[] { nx, ny });
                }
            }
        }
        a.add(count);
    }
}