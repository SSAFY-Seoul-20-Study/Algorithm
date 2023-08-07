
import java.util.*;
import java.io.*;
public class Main {
    static int N;
    static int [][] sunsu;
    static int[] lineUp;
    static boolean [] visited;
    static int ans;
    public static void main(String[] args) throws Exception{
        System.setIn(new FileInputStream("study/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        sunsu = new int [N+1][10];
        //입력
        for(int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 9; j++) {
                sunsu[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        visited = new boolean[10];
        lineUp = new int[10];

        visited[4] = true;
        lineUp[4] = 1;

        ans = 0;
        comb(2);
        System.out.println(ans);

    }
    static void comb(int depth) {
        if(depth == 10) {
            simulation();
            return;
        }
        for(int i=1; i<=9; i++) {
            if(visited[i]) continue;
            visited[i] = true;
            lineUp[i] = depth;
            comb(depth+1);
            visited[i] = false;
        }
    }
    static void simulation() {
        int score = 0;
        int startPlayer = 1;
        boolean[] base;

        for (int i = 1; i <= N; i++) {
            int out = 0;
            base = new boolean[4];
            flag:while (true) {
                for (int j = startPlayer; j <= 9; j++) {
                    int hitter = sunsu[i][lineUp[j]];
                    switch (hitter) {
                        case 0:
                            out++;
                            break;
                        case 1:
                            for (int k = 3; k >= 1; k--) {
                                if (base[k]) {
                                    if (k == 3) {
                                        score++;
                                        base[k] = false;
                                        continue;
                                    }
                                    base[k] = false;
                                    base[k + 1] = true;
                                }
                            }
                            base[1] = true;
                            break;
                        case 2:
                            for (int k = 3; k >= 1; k--) {
                                if (base[k]) {
                                    if (k == 3 || k == 2) {
                                        score++;
                                        base[k] = false;
                                        continue;
                                    }
                                    base[k] = false;
                                    base[k + 2] = true;
                                }
                            }
                                base[2] = true;
                                break;
                        case 3:
                            for (int k = 3; k >= 1; k--) {
                                if (base[k]) {
                                    score++;
                                    base[k] = false;
                                }
                            }
                            base[3] = true; // 홈에서 3루로 진루.
                            break;
                        case 4:
                            for (int k = 1; k <= 3; k++) {
                                if (base[k]) {
                                    score++;
                                    base[k] = false;
                                }
                            }
                            score++;
                            break;
                    }
                    if (out == 3) {
                        startPlayer = j + 1;
                        if (startPlayer == 10) {
                            startPlayer = 1;
                        }
                        break flag;
                    }
                }
                startPlayer = 1;
            }
        }
        ans = Math.max(ans,score);
    }
}
