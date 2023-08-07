import java.io.*;
import java.util.*;

// ⚾

public class BJ12781 {

    static int n;
    static int score = 0;                           // 득점
    static int maxScore = 0;                        // 최대 득점
    static int playerIdx = 0;                       // 타자의 인덱스
    static int[][] results;                         // 입력으로 받은 이닝마다 각 선수가 얻는 결과
    static int[] order = new int[9];                // 선수의 인덱스를 담아 타순을 저장
    static boolean[] visited = new boolean[9];      // 해당 인덱스의 선수를 선택했는지의 여부
    static boolean[] base = new boolean[3];         // 0루, 1루, 2루에 선수가 있는지의 여부
    
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        results = new int[n][9];

        StringTokenizer st;
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 9; j++) {
                results[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 1번 선수를 4번 타자로 지정
        visited[0] = true;
        order[3] = 0;

        makeOrder(0);
        System.out.println(maxScore);
    }

    private static void makeOrder(int idx) {

        if(idx == 3) {      // 4번 타자는 이미 정해졌으므로 다음 순서 탐색
            makeOrder(idx + 1);
            return;
        }

        if(idx == 9) {      // 타순이 모두 정해졌다면 게임 시작하기
            playGame();
            return;
        }

        for(int i = 1; i < 9; i++) {
            if(visited[i]) continue;

            visited[i] = true;
            order[idx] = i;
            makeOrder(idx + 1); 
            visited[i] = false;
        }
    }

    private static void playGame() {

        // 게임을 시작하면, 점수와 선수의 인덱스 초기화
        score = 0;
        playerIdx = 0;

        for(int inning = 0; inning < n; inning++) {
            // 각 이닝을 시작하면, 아웃된 횟수와 베이스 초기화
            int cntOfOut = 0;
            Arrays.fill(base, false);

            while(true) {
                int result = results[inning][order[playerIdx]];
                playerIdx = (playerIdx + 1) % 9;

                switch(result) {
                    case 0: cntOfOut++;
                            break;
                    case 1: movePlayer(1);
                            break;
                    case 2: movePlayer(2);
                            break;
                    case 3: movePlayer(3);
                            break;
                    case 4: movePlayer(4);
                            break;
                }

                if(cntOfOut == 3) break;
            }
        }

        maxScore = Integer.max(maxScore, score);
    }

    private static void movePlayer(int time) {      // time에 따라 베이스에 있는 선수들을 진루시킴

        if(time == 1) {
            if(base[2]) score++;
            base[2] = base[1];
            base[1] = base[0];
            base[0] = true;
        }
        else if(time == 2) {
            if(base[2]) score++;
            if(base[1]) score++;
            base[2] = base[0];
            base[1] = true;
            base[0] = false;
        }
        else if(time == 3) {
            if(base[2]) score++;
            if(base[1]) score++;
            if(base[0]) score++;
            base[2] = true;
            base[1] = false;
            base[0] = false;
        }
        else {
            for(int i = 0; i < 3; i++) {
                if(base[i]) score++;
                base[i] = false;
            }
            score++;
        }
    }

}
