import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {
    static int n,m;
    static int[][] move = {{-1,0},{0,1},{1,0},{0,-1}};
    static int[][] board;
    static int[][] gap;
    static boolean[][] v;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new int[n][m];
        gap = new int[n][m];
        v = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int day = 0;
        while (true) {
//            System.out.printf("day %d : \n",day);
            int code = isDivided();
//            System.out.printf("code %d \n",code);
            if (code==-1) {
                System.out.println(0);
                return;
            } else if (code==1) {
                System.out.println(day);
                return;
            }
            melt();
//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < m; j++) {
//                    System.out.print(board[i][j]+" ");
//                }
//                System.out.println();
//            }
            day++;
        }
    }
    static void melt() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                gap[i][j] = 0;
                if (board[i][j]!=0) {
                    int t = 0;
                    for (int[] dxy:move
                         ) {
                        int nx = i + dxy[0];
                        int ny = j + dxy[1];
                        if (0<=nx && nx<n && 0<=ny && ny<m && board[nx][ny]==0) {
                            t++;
                        }
                    }
                    gap[i][j] = t;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (gap[i][j] >= board[i][j]) {
                    board[i][j] = 0;
                } else board[i][j] -= gap[i][j];
            }
        }
    }


    // 다 녹으면 -1 안나뉘면 0 나뉘면 1
    static int isDivided() {
        boolean exhausted = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                v[i][j] = false;
                if (board[i][j]!=0) exhausted=false;
            }
        }
        if (exhausted) return -1;
        int divided = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j]!=0 && !v[i][j]) {
                    if (divided++ >= 1){
                        return 1;
                    }
                    v[i][j] = true;
                    ArrayDeque<int[]> q = new ArrayDeque<>();
                    q.offer(new int[]{i,j});
                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        for (int[] dxy:move
                             ) {
                            int nx = cur[0] + dxy[0];
                            int ny = cur[1] + dxy[1];
                            if (0<=nx && nx<n && 0<=ny && ny<m && board[nx][ny]!=0 && !v[nx][ny]) {
                                v[nx][ny] = true;
                                q.offer(new int[]{nx,ny});
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }
}
