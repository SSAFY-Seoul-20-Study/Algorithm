import java.io.*;
import java.util.*;
public class Main {

    static class Knight {
        int num;
        int r;
        int c;
        int h;
        int w;
        int k;

        int damaged = 0;
        boolean isAlive = true;

        Knight(int num, int r, int c, int h, int w, int k) {
            this.num = num;
            this.r = r;
            this.c = c;
            this.h = h;
            this.w = w;
            this.k = k;
        }

        void moveKnight(int d) {
            int dr = move[d][0];
            int dc = move[d][1];

            r += dr;
            c += dc;
        }

    }

    static int[][] move = {{-1,0}, {0,1}, {1,0}, {0,-1}};
    static int[][] board, boardStatus;
    static Map<Integer, Knight> knightMap;
    static Set<Integer> knightVisted;
    static int L,N,Q;

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        board = new int[L][L];
        boardStatus = new int[L][L];
        knightMap = new HashMap<>();
        for (int i=0; i<L; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<L; j++) {
                boardStatus[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            Knight knight = new Knight(i, r, c, h, w, k);
            knightMap.put(i, knight);
        }

        for (int cc=0; cc<Q; cc++) {
            // System.out.println("round: "+cc);

            // for (Map.Entry<Integer, Knight> entry : knightMap.entrySet()) {
            //     if (entry.getValue().isAlive) {
            //         System.out.println(entry.getKey()+": "+entry.getValue().r+","+entry.getValue().c+" "+entry.getValue().damaged);
            //     }
            // }


            st = new StringTokenizer(br.readLine());
            int srcKnightNum = Integer.parseInt(st.nextToken());
            // System.out.println(srcKnightNum);
            
            int d = Integer.parseInt(st.nextToken());
            int dr = move[d][0];
            int dc = move[d][1];
            // System.out.println(dr+" "+dc);

            // 대상기사가 살아있는지
            Knight srcKnight = knightMap.get(srcKnightNum);
            if (!srcKnight.isAlive) {
                continue;
            }

            // 움직일 수 있는가?
            ArrayDeque<int[]> queue = new ArrayDeque<>();
            knightVisted = new HashSet<>();
            boolean[][] v = new boolean[L][L];
            boolean moveFlag = true;
            for (int i=0; i<srcKnight.h; i++) {
                for (int j=0; j<srcKnight.w; j++) {
                    if (0<=srcKnight.r+i && srcKnight.r+i<L &&
                        0<=srcKnight.c+j && srcKnight.c+j<L) {
                        queue.add(new int[]{srcKnight.r+i, srcKnight.c+j, srcKnightNum});   
                    }
                }
            }
            while (!queue.isEmpty()) {
                // 하나 이동한 칸
                int[] now = queue.pollFirst();
                int nr = now[0] + dr;
                int nc = now[1] + dc;
                // System.out.println(Arrays.toString(now));
                // System.out.println(queue.size());
                // System.out.println(nr+" "+nc);

                // 벽이거나 밖이면 flag=false 이동불가
                if (nr<0 || L<=nr || nc<0 || L<=nc || boardStatus[nr][nc]==2) {
                    moveFlag = false;
                    break;
                }
                // 해당 칸이 누가 점유중인지, 혹은 빈 칸인지 찾는다.
                int destKnightNum = -1;
                for (Map.Entry<Integer, Knight> entry : knightMap.entrySet()) {
                    // int destKnightNum = entry.key();
                    Knight destKnight = entry.getValue();
                    if (!destKnight.isAlive) continue;
                    if (destKnight.r<=nr && nr<destKnight.r+destKnight.h &&
                        destKnight.c<=nc && nc<destKnight.c+destKnight.w) {
                        // 내가 점유중이오!
                        destKnightNum = entry.getKey();
                        // System.out.println("점유 : "+destKnightNum);
                        break;
                    }
                }
                // 나랑 같은 번호이거나 빈칸이거나 이미 와봤던 칸이면 더 진행 x
                if (destKnightNum == now[2] || destKnightNum==-1 || v[nr][nc]) {
                    continue;
                }
                // System.out.println(Arrays.toString(now));
                // System.out.println(nr+"+"+nc);
                // 새로 만나는 다른 기사이다. 큐에 담고 방문처리
                knightVisted.add(destKnightNum);
                Knight destKnight = knightMap.get(destKnightNum);
                for (int i=0; i<destKnight.h; i++) {
                    for (int j=0; j<destKnight.w; j++) {
                        if (0<=i+destKnight.r && i+destKnight.r<L &&
                            0<=j+destKnight.c && j+destKnight.c<L) {
                            queue.add(new int[]{i+destKnight.r, j+destKnight.c, destKnightNum});
                            v[i+destKnight.r][j+destKnight.c] = true;
                            // System.out.println("check "+(i+destKnight.r)+" "+(j+destKnight.c));
                        }
                    }
                }
            }
            knightVisted.add(srcKnightNum);
            // System.out.println(knightVisted);

            if (!moveFlag) {
                continue;
            }
            // 움직일 수 있으니 움직입시다. 데미지를 입힙시다.
            for (int k : knightVisted) {
                Knight targetKnight = knightMap.get(k);
                targetKnight.moveKnight(d);
                int bombCnt = 0;
                for (int i=0; i<targetKnight.h; i++) {
                    for (int j=0; j<targetKnight.w; j++) {
                        if (0<=i+targetKnight.r && i+targetKnight.r<L &&
                            0<=j+targetKnight.c && j+targetKnight.c<L) {
                            if (boardStatus[i+targetKnight.r][j+targetKnight.c]==1) {
                                bombCnt++;
                            }
                        }
                    }
                }
                // 움직인 놈은 안 다쳐.
                if (k!=srcKnightNum) {
                    targetKnight.damaged += bombCnt;
                    if (targetKnight.k <= targetKnight.damaged) {
                        targetKnight.isAlive = false;
                    }
                }
            }
        }

        // System.out.println("round: "+Q);
        int result = 0;
        for (Map.Entry<Integer, Knight> entry : knightMap.entrySet()) {
            Knight knight = entry.getValue();
            if (knight.isAlive) {
                // System.out.println(entry.getKey()+": "+entry.getValue().r+","+entry.getValue().c+" "+entry.getValue().damaged);
                result += knight.damaged;
            }
        }

        System.out.println(result);








    }
}
