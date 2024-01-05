import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class boj_2146_다리만들기 {
    static int n,min=Integer.MAX_VALUE;
    static int[][] move = {{-1,0},{0,1},{1,0},{0,-1}};
    static int[][] board;
    static boolean[][] v;
    static ArrayList<int[]> path = new ArrayList<>();
    static ArrayList<int[]> landEdges = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        board = new int[n][n];
        v = new boolean[n][n];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int num = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j]>0 && !v[i][j]) {
                    paint(i,j,num);
                    num++;
                }
            }
        }
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.printf(board[i][j]+" ");
//            }
//            System.out.println();
//        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                v[i][j] = false;
                if (board[i][j]>0){
                    boolean isEdge = false;
                    for (int[] dxy:move) {
                        int nx = i + dxy[0];
                        int ny = j + dxy[1];
                        if (0<=nx && nx<n && 0<=ny && ny<n && board[nx][ny]==0) {
                            isEdge = true;
                        }
                    }
                    if (isEdge) landEdges.add(new int[]{i,j});
                }
            }
        }



        for (int i = 0; i < landEdges.size(); i++) {
            int x = landEdges.get(i)[0];
            int y = landEdges.get(i)[1];
            int landNum = board[x][y];
//            path.clear();

            ArrayDeque<int[]> q = new ArrayDeque<>();
            q.offer(new int[]{x,y,0});
//            path.add(new int[]{x,y,0});
            v[x][y] = true;

            while (!q.isEmpty()) {
                int[] cur = q.poll();
                if (board[cur[0]][cur[1]]>0 && board[cur[0]][cur[1]]!=landNum) {
                    // 이동했는데 다른 섬 -> 갱신후 stop
                    min = Math.min(min, cur[2]-1);
                    break;
                }
                if (x!=cur[0] && y!=cur[1] && board[cur[0]][cur[1]]==landNum) {
                    // 이동했는데 같은섬 -> 블록
                    continue;
                }
                for (int[] dxy:move) {
                    int nx = cur[0]+dxy[0];
                    int ny = cur[1]+dxy[1];
                    if (0<=nx && nx<n && 0<=ny && ny<n && !v[nx][ny]) {
                        q.offer(new int[]{nx,ny,cur[2]+1});
                        v[nx][ny] = true;
//                        path.add(new int[]{nx,ny,cur[2]+1});
                    }
                }
            }

            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    v[j][k] = false;
                }
            }
        }
//        for (int[] xy:path) {
//            v[xy[0]][xy[1]] = false;
//        }

        System.out.println(min);

    }
    static void paint(int x, int y, int num){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{x,y});
        board[x][y] = num;
        v[x][y] = true;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int[] dxy:move) {
                int nx = cur[0]+dxy[0];
                int ny = cur[1]+dxy[1];
                if (0<=nx && nx<n && 0<=ny && ny<n && board[nx][ny]>0 && !v[nx][ny]){
                    board[nx][ny] = num;
                    v[nx][ny] = true;
                    q.offer(new int[]{nx,ny});
                }
            }
        }
    }
}
