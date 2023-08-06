import java.util.*;
import java.io.*;

// 78724KB	676ms
public class Main_boj_17281_야구공 {
    static int N,res;
    static int[][] game_result; // 입력받은 각 이닝별 결과
    static boolean[] visited = new boolean[10]; // 1번타자~9번타자
    static int[] temp_ordered = new int[8]; // 8명 정렬된 순서(1번 빼고)
    static int[] player_ordered = new int[10]; // 1번째~9번째 순서
    static int[] ground = new int[4]; // 0,1루,2루,3루 선수 저장
    static int cur_player, cur_play, cur_idx=1;
    static int out, innings=1, round_score;

    public static void main(String[] args) throws Exception{
        System.setIn(new FileInputStream("res/input_boj_17281.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        game_result = new int[N][9];

        // 입력받기
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<9; j++) {
                game_result[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        playerOrder(0); // 타자 순서정하기
//        player_ordered = new int[]{0, 4, 2, 9, 1, 7, 3, 8, 5, 6};
//        game();

        System.out.println(res);
        br.close();
    }
    static void game(){
        while(true) {
            if (innings == N+1) {
                res = Math.max(res, round_score);
                // 초기화
                round_score = 0;
                innings = 1;
                cur_idx = 1;
                return;
            }
            cur_player = player_ordered[cur_idx]; // 현재 타자번호
            cur_play = game_result[innings - 1][cur_player - 1]; // 현재 타자의 결과

            switch (cur_play) {
                case 1: // 안타
                    if (ground[3] == 1) {
                        round_score++;
                        ground[3] = 0;
                    }
                    if (ground[2] == 1) {
                        ground[3] = 1;
                        ground[2] = 0;
                    }
                    if (ground[1] == 1) {
                        ground[2] = 1;
                        ground[1] = 0;
                    }
                    ground[1] = 1;
                    break;
                case 2: // 2루타
                    for (int i = 3; i >= 1; i--) {
                        if (i == 3 || i == 2) {
                            if (ground[i] == 1) {
                                round_score++;
                                ground[i] = 0;
                            }
                        } else if (i == 1 && ground[i] == 1) {
                            ground[i + 2] = 1;
                            ground[i] = 0;
                        }
                    }
                    ground[2] = 1;
                    break;
                case 3: // 3루타
                    for (int i = 3; i >= 1; i--) {
                        if (ground[i] == 1) {
                            round_score++;
                            ground[i] = 0;
                        }
                    }
                    ground[3] = 1;
                    break;
                case 4: // 홈런
                    for (int i = 3; i >= 1; i--) {
                        if (ground[i] == 1) {
                            round_score++;
                            ground[i] = 0;
                        }
                    }
                    round_score++;
                    break;
                case 0: // 아웃
                    out++;
                    if (out == 3) {
                        innings++;
                        out = 0;
                        ground = new int[4];
                    }
                    break;
            }
//            System.out.println(cur_player+":"+cur_play+":"+round_score);
            if (cur_idx == 9) cur_idx = 1;
            else cur_idx++;
        }
    }

    // 1번째 ~ 9번째 순서대로 타자 번호 저장
    static void setPlayerOrdered(){
        for(int i=1; i<=9; i++){
            if(i==4) {
                player_ordered[i] = 1; // 1번타자 고정
            } else if (i<4) {
                player_ordered[i] = temp_ordered[i-1];
            }else { // i > 4s
                player_ordered[i] = temp_ordered[i-2];
            }
        }
        game(); // 게임 시작
    }

    // 선수들 순서 정하기 (순열)
    static void playerOrder(int cnt){
        if(cnt == 8) {
            setPlayerOrdered();
            return;
        }
        for(int i=2; i<=9; i++){
            if(visited[i]) continue;

            visited[i] = true;
            temp_ordered[cnt] = i;
            playerOrder(cnt+1);
            visited[i] = false;
        }
    }
}
