import java.util.*;
import java.io.*;

public class Main {

    static int N, M, P, C, D;
    static int redX, redY;
    static int[][] board;
    static int[][] santa;
    static boolean[] is_dead;
    static int[] is_sleep;
    static int[] dx = {-1, 0, 1, 0, 1, 1, -1, -1};
    static int[] dy = {0, 1, 0, -1, -1, 1, -1, 1};
    static int[] score;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 게임판 크기
        M = Integer.parseInt(st.nextToken()); // 게임 턴 수
        P = Integer.parseInt(st.nextToken()); // 산타 수
        C = Integer.parseInt(st.nextToken()); // 루돌프 힘
        D = Integer.parseInt(st.nextToken()); // 산타 힘
        board = new int[N + 1][N + 1];

        st = new StringTokenizer(br.readLine());
        // 루돌프 초기 위치
        redX = Integer.parseInt(st.nextToken());
        redY = Integer.parseInt(st.nextToken());
        board[redX][redY] = -1;

        santa = new int[P + 1][2];
        is_dead = new boolean[P + 1];
        is_sleep = new int[P + 1];
        score = new int[P + 1];

        for(int i = 1; i <= P; i++) {
            st = new StringTokenizer(br.readLine());

            int idx = Integer.parseInt(st.nextToken());
            santa[idx][0] = Integer.parseInt(st.nextToken());
            santa[idx][1] = Integer.parseInt(st.nextToken());

            board[santa[idx][0]][santa[idx][1]] = idx;
        }

        for(int i = 0; i < M; i++) {
            if(!moveRed()) break;
            moveSanta();

            for(int j = 1; j <= P; j++) {
                if(is_dead[j]) continue;
                score[j] += 1;
            }
        }

        for(int i = 1; i <= P; i++) {
            System.out.print(score[i]+ " ");
        }
    }

    static void interaction(int num, int dir) {
        int nx = santa[num][0] + dx[dir];
        int ny = santa[num][1] + dy[dir];

        santa[num][0] = nx;
        santa[num][1] = ny;
        if(nx < 1 || nx > N|| ny < 1 || ny > N) {
            is_dead[num] = true;
            return;
        }

        if(board[nx][ny] >= 1){
            interaction(board[nx][ny], dir);
        }
        
        board[nx][ny] = num;
    }

    static void collision(int num, int m, int dir) {
        int nx = santa[num][0] + dx[dir] * m;
        int ny = santa[num][1] + dy[dir] * m;
        
        santa[num][0] = nx;
        santa[num][1] = ny;
        if(nx < 1 || nx > N || ny < 1 || ny > N) {
            is_dead[num] = true;
            return;
        }
        
        if(board[nx][ny] >= 1) {
            interaction(board[nx][ny], dir);
        }
        board[nx][ny] = num;
    }

    static void moveSanta() {
        for(int i = 1; i <= P; i++) {
            if(is_dead[i]) continue;
            if(is_sleep[i] != 0) {
                is_sleep[i] -= 1;
                continue;
            }

            int min = dist(santa[i][0], santa[i][1], redX, redY);
            int idx = -1;
            // 산타 이동
            for(int d = 0; d < 4; d++) {
                int x = santa[i][0] + dx[d];
                int y = santa[i][1] + dy[d];

                if(x < 1 || x > N || y < 1 || y > N) continue;
                if(board[x][y] > 0) continue;

                int tmp = dist(x, y, redX, redY);
                if(tmp < min) {
                    idx = d;
                    min = tmp;
                }
            }

            if(idx == -1) continue;

            board[santa[i][0]][santa[i][1]] = 0;
            santa[i][0] += dx[idx];
            santa[i][1] += dy[idx];

            if(board[santa[i][0]][santa[i][1]] < 0) { // 루돌프 존재
                score[i] += D;

                is_sleep[i] = 1;
                if(idx == 0) idx = 2;
                else if(idx == 1) idx = 3;
                else if(idx == 2) idx = 0;
                else if(idx == 3) idx = 1;

                collision(i, D, idx);
            }
            else if(board[santa[i][0]][santa[i][1]] > 0) {
                interaction(board[santa[i][0]][santa[i][1]], idx);
            }

            if(is_dead[i]) continue;
            board[santa[i][0]][santa[i][1]] = i;

        }
    }

    static boolean moveRed() {
        // 가장 가까운 산타 찾기
        int min = 5001, idx = -1;

        for(int i = 1; i <= P; i++) {
            if(is_dead[i]) continue;

            int d = dist(redX, redY, santa[i][0], santa[i][1]);
            
            if(d < min) {
                min = d;
                idx = i;
            } else if(d == min) {
                if(santa[idx][0] < santa[i][0]) idx = i;
                else if(santa[idx][0] == santa[i][0]) {
                    if(santa[idx][1] < santa[i][1]) idx = i;
                }
            }
        }

        if(idx == -1) return false;

        int dir = -1;
        min = 5001;
        // 루돌프 이동
        for(int i = 0; i < 8; i++) {
            int x = redX + dx[i];
            int y = redY + dy[i];

            int d = dist(x, y, santa[idx][0], santa[idx][1]);

            if(d < min) {
                dir = i;
                min = d;
            }
        }

        board[redX][redY] = 0;
        redX += dx[dir];
        redY += dy[dir];

        if(board[redX][redY] > 0) { // 산타 존재
            int tmp = board[redX][redY];

            score[tmp] += C;
            is_sleep[tmp] = 2;
            collision(tmp, C, dir);
        }

        board[redX][redY] = -1;

        return true;
    }

    static int dist(int r1, int c1, int r2, int c2) {
        return (int)(Math.pow((r1-r2), 2) + Math.pow((c1 - c2), 2));
    }
}
