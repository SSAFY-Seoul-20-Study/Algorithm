import java.io.*;
import java.util.*;

// 어항 정리

public class BJ23291 {

    static final int MAX = 99999;
    static int n, k;
    static int cnt;                     // 어항 정리 횟수
    static int idx, row, col;           // firstFly()에서 사용되는 변수
    static int[][] arr;
    static int[][] temp;

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        arr = new int[n + 1][n + 1];
        temp = new int[n + 1][n + 1];
        
        st = new StringTokenizer(br.readLine());
        int min = MAX;
        int max = 0;
        for(int i = 1; i <= n; i++) {
            arr[n][i] = Integer.parseInt(st.nextToken());
            if(min > arr[n][i]) min = arr[n][i];
            if(max < arr[n][i]) max = arr[n][i];
        }

        if(max - min > k) solution();
        System.out.println(cnt);
    }

    private static void solution() {

        cnt = 1;
        while(true) {
            insertOnePutOne();      // 물고기 수 가장 적은 어항에 물고기 한 마리 넣고, 가장 왼쪽 어항을 오른쪽 어항 위 올리기
            flyFirst();             // 첫번째 방식의 공중부양
            adjustFishNumber();     // 물고기 수 조절하기
            putLine();              // 일렬로 놓기
            flySecond();            // 두번째 방식의 공중부양
            adjustFishNumber();     // 물고기 수 조절하기
            putLine();              // 일렬로 놓기
            cnt++;
        }
    }

    private static void insertOnePutOne() {
        
        int min = MAX;
        ArrayDeque<Integer> minIdx = new ArrayDeque<>();
        for(int i = 1; i <= n; i++) {
            if(min > arr[n][i]) {
                minIdx.clear();
                min = arr[n][i];
                minIdx.add(i);
            }
            else if(min == arr[n][i]) {
                minIdx.add(i);
            }
        }

        while(!minIdx.isEmpty()) {
            arr[n][minIdx.poll()]++;
        }

        arr[n - 1][2] = arr[n][1];
        arr[n][1] = 0;
    }

    private static void flyFirst() {
        
        idx = 2;
        row = 1;
        col = 2;

        int time = 0;
        while(true) {

            if(idx + row + col - 1 > n) break;
            moveFirst();
            idx += row;
            if (time++ % 2 == 0) row++;
            else col++;
        }
    }

    private static void moveFirst() {

        for(int j = idx; j < idx + row; j++) {
            for(int i = n; i > n - col; i--) {
                arr[n - row + (j - idx)][idx + row + (n - i)] = arr[i][j];
                arr[i][j] = 0;
            }
        }
    }
    
    private static void adjustFishNumber() {
        
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                // 인접 열 탐색
                if(j < n && arr[i][j] != 0 && arr[i][j + 1] != 0) {
                    int d = Math.abs(arr[i][j] - arr[i][j + 1]) / 5;
                    if(d > 0) {
                        if(arr[i][j] > arr[i][j + 1]) {
                            temp[i][j] -= d;
                            temp[i][j + 1] += d;
                        }
                        else {
                            temp[i][j] += d;
                            temp[i][j + 1] -= d;
                        }
                    }
                }

                // 인접 행 탐색
                if(i < n && arr[i][j] != 0 && arr[i + 1][j] != 0) {
                    int d = Math.abs(arr[i][j] - arr[i + 1][j]) / 5;
                    if(d > 0) {
                        if(arr[i][j] > arr[i + 1][j]) {
                            temp[i][j] -= d;
                            temp[i + 1][j] += d;
                        } 
                        else {
                            temp[i][j] += d;
                            temp[i + 1][j] -= d;
                        } 
                    }
                }
            }
        }

        int min = MAX;
        int max = 0;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                arr[i][j] += temp[i][j];
                temp[i][j] = 0;

                if(arr[i][j] == 0) continue;
                min = Integer.min(min, arr[i][j]);
                max = Integer.max(max, arr[i][j]);
            }
        }
        if(max - min <= k) {
            System.out.println(cnt);
            System.exit(0);
        }
    }
    
    private static void putLine() {

        int pos = 1;
        for(int j = idx; j < idx + row; j++) {
            for(int i = n; i > n - col; i--) {
                arr[n][pos++] = arr[i][j];
                arr[i][j] = 0;
            }
        }
    }
    
    private static void flySecond() {
        
        // 1번 작업
        for(int i = 1; i <= n / 2; i++) {
            arr[n - 1][n + 1 - i] = arr[n][i];
            arr[n][i] = 0;
        }

        // 2번 작업
        int threeQuarters = n - n  / 4;
        for(int i = n - 1; i <= n; i++) {
            for(int j = threeQuarters; j > n / 2; j--) {
                arr[2 * n - 3 - i][threeQuarters + (threeQuarters - j + 1)] = arr[i][j];
                arr[i][j] = 0;
            }
        }

        idx = threeQuarters + 1;
        row = n / 4;
        col = 4;
    }
    
}
