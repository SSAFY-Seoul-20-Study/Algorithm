package algo_study;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main_bj_15644_구슬탈출3 {

    static class Ball {
        int x, y, cnt;
        String rot;
        Ball(int x, int y, int cnt, String rot){
            this.x = x;
            this.y = y;
            this.cnt= cnt;
            this.rot=rot;
        }
    }

    static int N, M;
    static char[][] map;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static char[] r = {'D', 'R','U', 'L'};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];

        Ball red = null;
        Ball blue = null;

        for(int i = 0; i< N; i++) {
            String input = br.readLine();
            for(int j = 0; j< M; j++) {
                map[i][j] = input.charAt(j);
                if(map[i][j] == 'R'){
                    red = new Ball(i, j, 0, "");
                }else if(map[i][j] == 'B') {
                    blue = new Ball(i, j, 0, "");
                }
            }
        }

        Ball ans = moveBall(red, blue);
        System.out.println(ans.cnt);
        if(ans.cnt != -1) System.out.println(ans.rot);
    }

    static Ball moveBall(Ball red, Ball blue) {
        ArrayDeque<Ball> redQ = new ArrayDeque<>();
        ArrayDeque<Ball> blueQ = new ArrayDeque<>();
        boolean[][][][] visited = new boolean[N][M][N][M];

        redQ.offer(red);
        blueQ.offer(blue);

        visited[red.x][red.y][blue.x][blue.y] = true;

        while(!redQ.isEmpty() && !blueQ.isEmpty()) {
            Ball curRed = redQ.poll();
            Ball curBlue = blueQ.poll();

            if(curRed.cnt > 10) return new Ball(curRed.x, curRed.y, -1, "");

            if(map[curBlue.x][curBlue.y] == 'O') continue;

            if(map[curRed.x][curRed.y] == 'O') return curRed;

            for(int i = 0; i < 4; i++) {

                int[] nowB = move(curBlue.x, curBlue.y, dx[i], dy[i]);
                int[] nowR = move(curRed.x, curRed.y, dx[i], dy[i]);

                if(nowB[0] == nowR[0] && nowB[1]  == nowR[1] && map[nowR[0]][nowR[1]] != 'O') {
                    int r_dis = Math.abs(curRed.x -nowR[0]) + Math.abs(curRed.y - nowR[1]);
                    int b_dis = Math.abs(curBlue.x - nowB[0]) + Math.abs(curBlue.y - nowB[1]);

                    if(r_dis > b_dis) {
                        nowR[0] -= dx[i];
                        nowR[1] -= dy[i];
                    }else{
                        nowB[0] -= dx[i];
                        nowB[1] -= dy[i];
                    }
                }

                if(!visited[nowR[0]][nowR[1]][nowB[0]][nowB[1]]){
                    visited[nowR[0]][nowR[1]][nowB[0]][nowB[1]] = true;

                    redQ.offer(new Ball(nowR[0], nowR[1], curRed.cnt + 1, curRed.rot + r[i] ));
                    blueQ.offer(new Ball(nowB[0], nowB[1], curBlue.cnt + 1, curBlue.rot + r[i]));
                }
            }
        }
        return new Ball(-1, -1, -1, "");
    }

    static int[] move(int x, int y, int dx, int dy) {

        while(true) {
            x += dx;
            y += dy;

            if (map[x][y] == 'O') return new int[]{x, y};
            else if (map[x][y] == '#') {
                return new int[]{x - dx, y - dy};
            }
        }
    }
}
