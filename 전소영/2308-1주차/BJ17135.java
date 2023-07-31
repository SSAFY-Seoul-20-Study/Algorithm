import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 캐슬 디펜스

public class BJ17135 {

    static class Point {
        int x;
        int y;
        int cnt;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point(int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }

        @Override
        public boolean equals(Object o) {
            return this.x == ((Point)o).x && this.y == ((Point)o).y;
        }

        @Override
        public int hashCode() {
            return this.x * 10 + this.y;
        }
    }
    static int n, m;
    static int d;
    static int cnt, cntMax;
    static int[] archerCol = new int[3];        // 배치할 궁수의 컬럼 인덱스
    static boolean[][] board;
    static boolean[][] temp;
    static boolean[][] visited;
    static Queue<Point> queue = new LinkedList<>();
    static HashSet<Point> attacked = new HashSet<>();       // 공격 당한 적의 위치
    static int[] dx = {0, -1, 0};
    static int[] dy = {-1, 0, 1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        
        board = new boolean[n][m];
        temp = new boolean[n][m];
        visited = new boolean[n][m];
        
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < m; j++) {
                if(st.nextToken().equals("1")) board[i][j] = true;
            }
        }

        backTracking(0, 0);
        System.out.println(cntMax);
    }

    private static void backTracking(int depth, int idx) {

        if(depth == 3) {        // 배치할 궁수의 조합을 archerCol에 모두 저장했다면
            attack();           // 공격하기
            return;
        }

        for(int i = idx; i < m; i++) {
            archerCol[depth] = i;
            backTracking(depth + 1, i + 1);
        }
    }

    private static void attack() {

        cnt = 0;                // 공격 가능한 적의 수
        
        // temp에 board 복사
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                temp[i][j] = board[i][j];
            }
        }        

        for(int time = 0; time < n; time++) {       // n회만큼 이동

            attacked.clear();                       // 회차당 공격 당한 적의 위치를 담음
            for(int i = 0; i < 3; i++) {
                searchEnemy(archerCol[i]);          // 해당 위치의 궁수를 기준으로 공격할 수 있는 적을 찾음
            }

            cnt += attacked.size();
            for(Point p : attacked) {
                board[p.x][p.y] = false;            // 공격 당했으므로 적을 없앰
            }

            moveBoard(time);                        // 아래로 한 칸씩 이동
        }

        // board에 temp 복사
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                board[i][j] = temp[i][j];
            }
        }

        cntMax = Integer.max(cntMax, cnt);
    }

    private static void searchEnemy(int col) {

        // visited[][] 초기화
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                visited[i][j] = false;
            }
        }

        queue.clear();
        queue.add(new Point(n, col, 1));
    
        while(!queue.isEmpty()) {
            Point poll = queue.poll();

            for(int i = 0; i < 3; i++) {
                int xx = poll.x + dx[i];
                int yy = poll.y + dy[i];

                if(xx < 0 || xx >= n || yy < 0 || yy >= m) continue;
                if(visited[xx][yy]) continue;

                visited[xx][yy] = true;

                if(board[xx][yy]) {     // 적이 있다면, 적의 위치를 attacked에 담고 탐색 종료
                    attacked.add(new Point(xx, yy));
                    return;
                }

                if(poll.cnt < d) {      // 거리가 d이하인 경우에 대해서만 탐색 진행
                    queue.add(new Point(xx, yy, poll.cnt + 1));
                }
            }
        }
    }

    private static void moveBoard(int time) {

        for(int i = n - 2; i >= 0; i--) {
            for(int j = 0; j < m; j++) {
                board[i + 1][j] = board[i][j];
            }
        }

        for(int j = 0; j < m; j++) {
            board[time][j] = false;
        }
    }
}
