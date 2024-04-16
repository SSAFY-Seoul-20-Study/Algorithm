import java.util.*;
import java.io.*;

public class Main {

    static class Knight{
        int r, c, h, w, k;

        public Knight(int r, int c, int h, int w, int k){
            this.r = r;
            this.c = c;
            this.h = h;
            this.w = w;
            this.k = k;
        }
    }

    public static int L, N, Q;
    public static int[][] board;
    public static int[] bef_k;
    public static Knight[] knights;
    public static int[] nr, nc;
    public static int[] dmg;
    public static boolean[] is_moved;

    public static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public static boolean tryMovement(int idx, int dir) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean is_pos = true;

        for(int i = 1; i <= N; i++) {
            dmg[i] = 0;
            is_moved[i] = false;
            nr[i] = knights[i].r;
            nc[i] = knights[i].c;
        }

        q.offer(idx);
        is_moved[idx] = true;

        while(!q.isEmpty()) {
            int x = q.poll();

            nr[x] += dx[dir];
            nc[x] += dy[dir];

            Knight now = knights[x];

            // 경계 체크
            if(nr[x] < 1 || nc[x] < 1 || nr[x] + now.h - 1 > L || nc[x] + now.w - 1 > L)
                return false;

             // 장애물 충돌 검사
            for(int i = nr[x]; i <= nr[x] + now.h - 1; i++) {
                for(int j = nc[x]; j <= nc[x] + now.w - 1; j++) {
                    if(board[i][j] == 1) 
                        dmg[x]++;
                    if(board[i][j] == 2)
                        return false;
                }
            }

            // 다른 조각과 충돌하는 경우, 해당 조각도 같이 이동
            for(int i = 1; i <= N; i++) {
                if(is_moved[i] || knights[i].k <= 0) 
                    continue;
                if(knights[i].r > nr[x] + now.h - 1 || nr[x] > knights[i].r + knights[i].h - 1) 
                    continue;
                if(knights[i].c > nc[x] + now.w - 1 || nc[x] > knights[i].c + knights[i].w - 1) 
                    continue;

                is_moved[i] = true;
                q.add(i);
            }
        }
        dmg[idx] = 0;
        return true;
    }

    // 특정 조각을 지정된 방향으로 이동시키는 함수
    public static void movePiece(int idx, int dir) {
        if(knights[idx].k <= 0) return;

        // 이동이 가능한 경우, 실제 위치와 체력을 업데이트
        if(tryMovement(idx, dir)) {
            for(int i = 1; i <= N; i++) {
                knights[i].r = nr[i];
                knights[i].c = nc[i];
                knights[i].k -= dmg[i];
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        board = new int[L + 1][L + 1];
        
        for(int i = 1; i<= L; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= L; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        knights = new Knight[N+ 1];
        bef_k = new int[N + 1];
        nr = new int[N + 1];
        nc = new int[N + 1];
        dmg = new int[N + 1];
        is_moved = new boolean[N + 1];
        // 기사 정보
        for(int i = 1; i<= N; i++) {
            st = new StringTokenizer(br.readLine());
            
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            knights[i] = new Knight(r, c, h, w, k);
            bef_k[i] = k;
        }

        // 명령
        for(int i = 1; i<= Q; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            movePiece(idx, dir);
        }

        long ans = 0;
        for(int i = 1; i<= N; i++) {
            if(knights[i].k > 0) ans+= bef_k[i] - knights[i].k;
        }

        System.out.println(ans);
    }
}
