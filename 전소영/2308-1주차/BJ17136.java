import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

// 색종이 붙이기

public class Main {
    
    static int cntOne;       // 1이 적힌 칸의 개수
    static int ans = Integer.MAX_VALUE;
    static int[] numOfPapers = {0, 5, 5, 5, 5, 5};
    static boolean[][] board = new boolean[10][10];

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        for(int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 10; j++) {
                if(st.nextToken().equals("1")) {
                    board[i][j] = true;
                    cntOne++;
                }
            }
        }

        backTracking(0);

        if(ans == Integer.MAX_VALUE) ans = -1;
        System.out.println(ans);
    }

    private static void backTracking(int depth) {       // depth는 붙인 색종이 개수

        if(depth >= ans) return;                        // 최솟값보다 크다면 종료
        if(cntOne == 0) {                               // 1이 적힌 칸이 남지 않았다면, 즉 색종이로 모두 덮었다면
            ans = Integer.min(ans, depth);
            return;
        }

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(board[i][j]) {                                   // 1이 적힌 칸을 만났을 때
                    int maxSize = calculateMaxPaperSize(i, j);      // 해당 칸을 기준으로 덮을 수 있는 색종이의 최대 사이즈
                    
                    for(int size = maxSize; size >= 1; size--) {    // 각 사이즈에 대하여 덮는 경우를 나누어 탐색

                        // size에 해당하는 색종이가 더이상 남지 않았다면 패스
                        if(numOfPapers[size] == 0) continue;
                        // size에 해당하는 색종이 개수 줄이기
                        numOfPapers[size]--;

                        int cntOfDeleted = 0;
                        // (i, j)를 기준으로 size만큼 board[][]를 0으로 바꾸기
                        for(int r = i; r < i + size; r++) {
                            for(int c = j; c < j + size; c++) {
                                board[r][c] = false;
                                cntOfDeleted++;
                            }
                        }
                        // 1이 적힌 칸은 줄어듦
                        cntOne -= cntOfDeleted;
            
                        backTracking(depth + 1);

                        // 1이 적힌 칸 복구
                        cntOne += cntOfDeleted;
                        // board[][]를 1로 복구
                        for(int r = i; r < i + size; r++) {
                            for(int c = j; c < j + size; c++) {
                                board[r][c] = true;
                            }
                        }
                        // size에 해당하는 색종이 개수 복구
                        numOfPapers[size]++;
                    }

                    return;     // 1이 적힌 칸을 만났으면 색종이로 덮어야 했는데, 그러지 못하고 도달했으므로 유효하지 않은 탐색이기에 종료
                }
            }
        }
    }

    private static int calculateMaxPaperSize(int r, int c) {

        // (r, c)를 기준으로 덮을 수 있는 색종이의 최대 사이즈 계산하기
        for(int cnt = 1; cnt <= 4; cnt++) {
            for(int i = r; i <= r + cnt; i++) {
                for(int j = c; j <= c + cnt; j++) {
                    if(i >= r + cnt || j <= c + cnt) {
                        if(i >= 10 || j >= 10 || !board[i][j]) return cnt;
                    }
                }
            }
        }

        return 5;
    }


}
