import java.io.*;
import java.util.*;

// 다리 만들기 2

public class BJ17472 {
    
    private static class Bridge implements Comparable<Bridge> {
        int dist;       // 다리의 길이
        int islandA;    // 연결된 섬 A의 id
        int islandB;    // 연결된 섬 B의 id

        Bridge(int dist, int islandA, int islandB) {
            this.dist = dist;
            this.islandA = islandA;
            this.islandB = islandB;
        }

        @Override
        public int compareTo(Bridge o) {
            return this.dist - o.dist;
        }

        @Override
        public boolean equals(Object o) {
            if(this.dist == ((Bridge)o).dist && this.islandA == ((Bridge)o).islandA && this.islandB == ((Bridge)o).islandB) return true;
            return false;
        }
    }
    static int n, m;
    static int ans;
    static int[][] board;               // 바다 0, 땅 9로 표기
    // 1. 섬을 찾기 위한 변수
    static ArrayDeque<int[]> queue = new ArrayDeque<>();
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    // 2. 다리를 찾기 위한 변수
    static boolean[][][] visited;       // [0][x][y]: (x, y) 기준 가로 방향으로 탐색했는지, [1][x][y]: (x, y) 기준 세로 방향을 탐색했는지
    static PriorityQueue<Bridge> bridges = new PriorityQueue<>();       // 만들 수 있는 다리들을 저장
    // 3. 다리 길이의 최솟값을 찾기 위한 변수
    static int[] parent;                // 각 섬과 연결된 부모 섬의 아이디
    

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        board = new int[n][m];
        visited = new boolean[2][n][m];
        
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < m; j++) {
                if(st.nextToken().equals("1")) board[i][j] = 9;
            }
        }

        solution();
        System.out.println(ans);
    }

    private static void solution() {

        // BFS 탐색으로 board[][]에 번호를 매기며 섬을 구분함
        searchIslands();
        // 만들 수 있는 다리들을 찾아서 bridges에 저장하기
        searchBridges();
        // 최솟값으로 연결할 수 있는 다리들을 골라 섬 연결하기
        connectIslands();
    }

    private static void searchIslands() {
        
        int id = 1;     // 섬의 아이디
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] == 9) {
                    bfs(i, j, id++);      // 해당 덩어리를 id 값의 섬으로 구분
                }
            }
        }

        // 마지막 id 값, 즉 섬의 총 개수로 parent[] 선언
        parent = new int[id];
        for(int i = 1; i < id; i++) {
            parent[i] = i;
        }
    }

    private static void bfs(int x, int y, int id) {

        boolean[][] visited = new boolean[n][m];

        board[x][y] = id;
        visited[x][y] = true;
        queue.add(new int[] {x, y});
        
        while(!queue.isEmpty()) {
            int[] poll = queue.poll();

            for(int i = 0; i < 4; i++) {
                int xx = poll[0] + dx[i];
                int yy = poll[1] + dy[i];

                if(xx < 0 || xx >= n || yy < 0 || yy >= m) continue;
                if(board[xx][yy] == 9 && !visited[xx][yy]) {
                    board[xx][yy] = id;
                    visited[xx][yy] = true;
                    queue.add(new int[] {xx, yy});
                }
            }
        }
    }

    private static void searchBridges() {
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] == 0) {
                    if(!visited[0][i][j]) makeBridge(0, i, j);      // (i, j)를 기준으로 가로 방향으로 다리 만들기 
                    if(!visited[1][i][j]) makeBridge(1, i, j);      // (i, j)를 기준으로 세로 방향으로 다리 만들기
                }
            }
        }
    }

    private static void makeBridge(int dir, int r, int c) {

        int dist = 1;
        int idA = 0, idB = 0;       // 연결된 두 섬의 아이디
        visited[dir][r][c] = true;
        
        // 가로 방향으로 조사하는 경우
        if(dir == 0) {
            // 왼쪽 탐색
            int y = c + dy[3];
            while(true) {
                if(y < 0) return;
                if(visited[dir][r][y]) return;
                if(board[r][y] != 0) {
                    idA = board[r][y];
                    break;
                }
                y += dy[3];
                dist++;
            }
            // 오른쪽 탐색
            y = c + dy[1];
            while(true) {
                if(y >= m) return;
                if(visited[dir][r][y]) return;
                if(board[r][y] != 0) {
                    idB = board[r][y];
                    break;
                }
                y += dy[1];
                dist++;
            }
        }
        // 세로 방향으로 조사하는 경우
        else {            
            // 위쪽 탐색
            int x = r + dx[0];
            while(true) {
                if(x < 0) return;
                if(visited[dir][x][c]) return;
                if(board[x][c] != 0) {
                    idA = board[x][c];
                    break;
                }
                x += dx[0];
                dist++;
            }
            // 아래쪽 탐색
            x = r + dx[2];
            while(true) {
                if(x >= n) return;
                if(visited[dir][x][c]) return;
                if(board[x][c] != 0) {
                    idB = board[x][c];
                    break;
                }
                x += dx[2];
                dist++;
            }
        }

        if(dist == 1) return;       // 다리의 길이가 1일 경우 무효
        if(idA == idB) return;      // 서로 다른 두 섬을 연결하지 않은 경우 무효
        
        Bridge newBridge = new Bridge(dist, idA, idB);
        if(!bridges.contains(newBridge)) bridges.add(newBridge);
    }

    private static void connectIslands() {

        if(bridges.size() == 0) {       // 만들어진 다리가 없는 경우
            ans = -1;
            return;
        }

        // dist가 짧은 다리부터 뽑아 섬 연결하기
        int cntOfSelected = 0;         // 선택된 다리의 개수
        while(!bridges.isEmpty()) {
            Bridge poll = bridges.poll();

            if(isConnected(poll.islandA, poll.islandB)) continue;

            // poll한 다리의 두 섬이 연결된 섬이 아니라면
            ans += poll.dist;
            cntOfSelected++;
            if(cntOfSelected == parent.length - 2) return;      // (섬의 개수 - 1) 만큼 연결되었을 때, 종료
        }

        ans = -1;
    }

    private static boolean isConnected(int a, int b) {

        int pa = findParent(a);
        int pb = findParent(b);

        if(pa == pb) return true;
        parent[pb] = pa;
        return false;
    }

    private static int findParent(int x) {
        
        if(parent[x] == x) return x;
        return parent[x] = findParent(parent[x]);
    }

}

