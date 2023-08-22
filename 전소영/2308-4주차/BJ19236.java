import java.io.*;
import java.util.*;

// 청소년 상어

public class BJ19236 {
    
    private static class Fish {
        int x;
        int y;
        int dir;

        Fish(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
    static final int SIZE = 4;
    static int ans;
    static int[][] board = new int[SIZE][SIZE];             // 상어는 0, 빈칸은 99
    static Fish[] fishes = new Fish[SIZE * SIZE + 1];       // 0번째는 상어 정보
    static int[] dx = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = {0, 0, -1, -1, -1, 0, 1, 1, 1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        for(int i = 0; i < SIZE; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < SIZE; j++) {
                int id = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                board[i][j] = id;
                fishes[id] = new Fish(i, j, dir);
            }
        }

        // (0, 0)에 상어 넣기
        fishes[0] = new Fish(0, 0, fishes[board[0][0]].dir);
        fishes[board[0][0]] = new Fish(-1, -1, -1);
        ans = board[0][0];
        board[0][0] = 0;

        backTracking(ans);
        System.out.println(ans);
    }

    private static void backTracking(int total) {

        moveFishes();

        // 배열 복사
        int[][] tempBoard = new int[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                tempBoard[i][j] = board[i][j];
            }
        }
        Fish[] tempFishes = new Fish[SIZE * SIZE + 1];
        for(int i = 0; i < tempFishes.length; i++) {
            tempFishes[i] = fishes[i];
        }

        int xx = fishes[0].x;
        int yy = fishes[0].y;
        int dir = fishes[0].dir;
        boolean isSharkOutOfBounds = false;

        while(true) {           // 상어가 공간을 벗어날 떄까지 반복
            xx += dx[dir];
            yy += dy[dir];

            if(xx < 0 || xx >= SIZE || yy < 0 || yy >= SIZE) {
                isSharkOutOfBounds = true;
                break;
            }
            if(board[xx][yy] != 99) {       // 빈칸이 아니라면, 즉 물고기가 들어있다면
                board[fishes[0].x][fishes[0].y] = 99;
                fishes[0] = new Fish(xx, yy, fishes[board[xx][yy]].dir);
                fishes[board[xx][yy]] = new Fish(-1, -1, -1);       // -1로 설정해 물고기 없애기
                int eaten = board[xx][yy];
                board[xx][yy] = 0;
                
                backTracking(total + eaten);

                // 배열 되돌리기
                for(int i = 0; i < SIZE; i++) {
                    for(int j = 0; j < SIZE; j++) {
                        board[i][j] = tempBoard[i][j];
                    }
                }
                for(int i = 0; i < tempFishes.length; i++) {
                    fishes[i] = tempFishes[i];
                }
            }
        }

        // 상어가 공간을 벗어났다면, ans 값 업데이트
        if(isSharkOutOfBounds) {
            ans = Integer.max(ans, total);
        }
    }

    private static void moveFishes() {

        for(int idx = 1; idx <= SIZE * SIZE; idx++) {

            Fish fish = fishes[idx];

            if(fishes[idx].x == -1) continue;       // 없앤 물고기라면 패쓰

            for(int i = 0; i <= 8; i++) {
                int dir = (fish.dir + i) % 8;
                if(dir == 0) dir = 8;

                int xx = fish.x + dx[dir];
                int yy = fish.y + dy[dir];
                if(xx < 0 || xx >= SIZE || yy < 0 || yy >= SIZE || board[xx][yy] == 0) continue;    // 범위를 벗어난다면 계속해서 회전하기
                if(board[xx][yy] == 0) continue;        // 빈칸이라면 계속해서 회전하기
         
                if(board[xx][yy] <= SIZE * SIZE) {      // 물고기가 들어있는 칸이라면
                    int targetIdx = board[xx][yy];
                    board[xx][yy] = idx;
                    board[fish.x][fish.y] = targetIdx;
                        
                    fishes[idx] = new Fish(xx, yy, dir);
                    fishes[targetIdx] = new Fish(fish.x, fish.y, fishes[targetIdx].dir);
                }
                else {                                  // 빈칸이라면
                    board[xx][yy] = idx;
                    board[fish.x][fish.y] = 99;
                    fishes[idx] = new Fish(xx, yy, dir);
                }
                break;
            }
        }
    }
}
