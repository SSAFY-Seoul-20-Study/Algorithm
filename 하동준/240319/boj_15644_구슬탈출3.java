import java.io.*;
import java.util.*;

class Main {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Point o) {
            return this.x == o.x && this.y == o.y;
        }
    }

    static class Element {
        Point R, B;
        int t;
        String root;

        public Element(Point R, Point B, int t, String root) {
            this.R = R;
            this.B = B;
            this.t = t;
            this.root = root;
        }
    }

    static BufferedReader br;
    static BufferedWriter bw;

    static int N, M;
    static int[] dx = {0,0,1,-1}; //R, L, D, U
    static int[] dy = {1,-1,0,0};
    static char[][] map;
    static Point R, B, O;

    static String nextRoot(String cur, int d) {
        if (d == 0) return cur + "R";
        else if (d == 1) return cur + "L";
        else if (d == 2) return cur + "D";
        else return cur + "U";
    }

    //빨강 우선인지 파랑 우선인지 확인
    static boolean isRedFirst(Point R, Point B, int d) {
        if (d == 0) return R.y > B.y;
        else if (d == 1) return R.y < B.y;
        else if (d == 2) return R.x > B.x;
        else return R.x < B.x;
    }

    //구슬 움직임
    static void move(Point target, Point other, int d) {
        while (true) {
            target.x += dx[d];
            target.y += dy[d];
            //벽일 경우
            if (map[target.x][target.y] == '#') {
                target.x -= dx[d];
                target.y -= dy[d];
                break;
            }
            //다른 구슬일 경우
            if (target.equals(other)) {
                target.x -= dx[d];
                target.y -= dy[d];
                break;
            }
            //구멍일 경우
            if (target.equals(O)) {
                target.x = -1;
                break;
            }
        }
    }

    static void solve() throws IOException {
        Queue<Element> q = new ArrayDeque<>();
        q.add(new Element(R, B, 0, ""));
        while(!q.isEmpty()) {
            Element cur = q.poll();
            if (cur.t == 10) break;
            for (int d = 0; d < 4; d++) {
                Point NR = new Point(cur.R.x, cur.R.y);
                Point NB = new Point(cur.B.x, cur.B.y);
                String NRoot = nextRoot(cur.root, d);

                if (isRedFirst(cur.R, cur.B, d)) {
                    //빨강 먼저
                    move(NR, NB, d);
                    move(NB, NR, d);
                } else {
                    //파랑 먼저
                    move(NB, NR, d);
                    move(NR, NB, d);
                }

                //1. 파랑만 빠졌을 경우
                if (NR.x != -1 && NB.x == -1)
                    continue;
                //2. 둘 다 빠졋을 경우
                if (NR.x == -1 && NB.x == -1)
                    continue;
                //3. 빨강만 빠졌을 경우
                if (NR.x == -1 && NB.x != -1) {
                    bw.write(cur.t + 1 + "\n");
                    bw.write(NRoot);
                    return;
                }

                q.add(new Element(NR, NB, cur.t + 1, NRoot));

            }
        }

        //불가능
        bw.write("-1\n");
    }

    static void input() throws IOException {
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        for (int i = 0; i < N; i++) {
            String in = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = in.charAt(j);
                if (map[i][j] == 'R') {
                    R = new Point(i, j);
                    map[i][j] = '.';
                } else if (map[i][j] == 'B') {
                    B = new Point(i, j);
                    map[i][j] = '.';
                } else if (map[i][j] == 'O') {
                    O = new Point(i, j);
                    map[i][j] = '.';
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        input();
        solve();
        bw.flush();
    }


}
