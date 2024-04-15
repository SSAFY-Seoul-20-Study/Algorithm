import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RudolphRebellion {
    static class Rudolph {
        int r, c;

        public Rudolph(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class Santa {
        int r, c;
        boolean isAlive;
        int score;
        int faintTime;

        public Santa(int r, int c) {
            this.r = r;
            this.c = c;
            isAlive = true;
            score = 0;
            faintTime = -2;
        }
    }

    static int N, M, P, C, D;
    static Rudolph rudolph;
    static Santa[] santas;
    static int[][] board; 
    static int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

    static boolean inBoard(int r, int c) {
        return r >= 0 && c >= 0 && r < N && c < N;
    }

    static int getDistance(int r1, int c1, int r2, int c2) {
        return (int) (Math.pow(r1 - r2, 2) + Math.pow(c1 - c2, 2));
    }

    /*
        루돌프의 움직임
     */
    static void moveRudolph(int turn) {
        int minDistance = Integer.MAX_VALUE;
        int targetSanta = -1;

        // 가장 가까운 산타 선택
        for (int i = 0; i < P; i++) {
            if (!santas[i].isAlive) { // 탈락한 산타 제외
                continue;
            }

            int distance = getDistance(santas[i].r, santas[i].c, rudolph.r, rudolph.c);

            if (targetSanta == -1) {
                minDistance = distance;
                targetSanta = i;
                continue;
            }

            if (distance > minDistance) {
                continue;
            }

            if (distance == minDistance) {
                if (santas[i].r < santas[targetSanta].r) {
                    continue;
                }

                if (santas[i].r > santas[targetSanta].r) {
                    targetSanta = i;
                    continue;
                }

                if (santas[i].c > santas[targetSanta].c) {
                    targetSanta = i;
                }

                continue;
            }

            minDistance = distance;
            targetSanta = i;
        }

        int dR = santas[targetSanta].r - rudolph.r;
        int dC = santas[targetSanta].c - rudolph.c;

        int nD = 0;

        // 이동 방향 선택
        if (dR == 0 || dC == 0) { // 상하좌우
            for (int d = 0; d < 4; d++) {
                if (dR * directions[d][0] > 0 || dC * directions[d][1] > 0) {
                    nD = d;
                    break;
                }
            }
        } else { // 대각선
            for (int d = 4; d < 8; d++) {
                if (dR * directions[d][0] > 0 && dC * directions[d][1] > 0) {
                    nD = d;
                    break;
                }
            }
        }

        rudolph.r += directions[nD][0];
        rudolph.c += directions[nD][1];

        if (board[rudolph.r][rudolph.c] > 0) { // 루돌프가 움직여서 충돌한 경우
            crash(board[rudolph.r][rudolph.c] - 1, nD, C, turn);
        }
    }

    /*
        산타의 움직임
     */
    static void moveSanta(int turn) {
        for (int i = 0; i < P; i++) {
            if (!santas[i].isAlive) { // 탈락한 산타 제외
                continue;
            }

            if (santas[i].faintTime + 2 > turn) { // 기절한 산타 제외
                continue;
            }

            // 최소 거리를 이동 전 산타와 루돌프의 거리로 초기화
            int minDistance = getDistance(santas[i].r, santas[i].c, rudolph.r, rudolph.c);
            int nD = 4;

            for (int d = 0; d < 4; d++) {
                int nR = santas[i].r + directions[d][0];
                int nC = santas[i].c + directions[d][1];

                if (!inBoard(nR, nC) || board[nR][nC] > 0) {
                    continue;
                }

                int distance = getDistance(nR, nC, rudolph.r, rudolph.c);

                if (minDistance <= distance) {
                    continue;
                }

                minDistance = distance;
                nD = d;
            }

            // 움직일 수 없는 경우
            if (nD == 4) {
                continue;
            }

            int nR = santas[i].r + directions[nD][0];
            int nC = santas[i].c + directions[nD][1];

            board[santas[i].r][santas[i].c] = 0;
            santas[i].r = nR;
            santas[i].c = nC;
            board[nR][nC] = (i + 1);

            if (nR == rudolph.r && nC == rudolph.c) { // 산타가 움직여서 충돌이 난 경우
                crash(i, (nD + 2) % 4, D, turn);
            }
        }
    }

    /*
        충돌
     */
    static void crash(int santaNumber, int d, int score, int turn) {
        board[santas[santaNumber].r][santas[santaNumber].c] = 0;
        santas[santaNumber].score += score;
        santas[santaNumber].faintTime = turn;

        int nR = santas[santaNumber].r + score * directions[d][0];
        int nC = santas[santaNumber].c + score * directions[d][1];

        if (!inBoard(nR, nC)) {
            santas[santaNumber].isAlive = false;
            return;
        }

        santas[santaNumber].r = nR;
        santas[santaNumber].c = nC;

        if (board[nR][nC] == 0) {
            board[nR][nC] = santaNumber + 1;
            return;
        }

        // 상호작용
        interact(santaNumber, nR, nC, d);
    }

    /*
        상호작용
     */
    static void interact(int santaNumber, int r, int c, int d) {
        int prevSantaNumber = board[r][c] - 1;
        board[r][c] = santaNumber + 1;

        r += directions[d][0];
        c += directions[d][1];

        if (!inBoard(r, c)) {
            santas[prevSantaNumber].isAlive = false;
            return;
        }

        santas[prevSantaNumber].r = r;
        santas[prevSantaNumber].c = c;

        if (board[r][c] == 0) {
            board[r][c] = prevSantaNumber + 1;
            return;
        }

        interact(prevSantaNumber, r, c, d);
    }

    /*
        탈락하지 않은 산타들에게 1점씩 추가 부여
     */
    static int bonus() {
        int count = 0;

        for (int i = 0; i < P; i++) {
            if (santas[i].isAlive) {
                santas[i].score++;
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        santas = new Santa[P];

        st = new StringTokenizer(br.readLine());
        rudolph = new Rudolph(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());

            int santaNumber = Integer.parseInt(st.nextToken());
            int sR = Integer.parseInt(st.nextToken()) - 1;
            int sC = Integer.parseInt(st.nextToken()) - 1;

            santas[santaNumber - 1] = new Santa(sR, sC);
            board[sR][sC] = santaNumber;
        }

        for (int i = 0; i < M; i++) {
            moveRudolph(i);
            moveSanta(i);

            if (bonus() == 0) {
                break;
            }
        }

        for (int i = 0; i < P; i++) {
            sb.append(santas[i].score).append(" ");
        }

        System.out.println(sb);
        br.close();
    }
}
