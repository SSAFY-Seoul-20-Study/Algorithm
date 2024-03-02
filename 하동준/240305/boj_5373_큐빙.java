package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/*            U
          0  1  2
          3  4  5
          6  7  8
L        __________    R            B
36 37 38|F18 19 20 | 45 46 47 | 27 28 29
39 40 41| 21 22 23 | 48 49 50 | 30 31 32
42 43 44| 24 25 26 | 51 52 53 | 33 34 35
        -----------
          9  10 11
          12 13 14
          15 16 17
          D
*/


public class boj_5373_큐빙 {

    static char[] arr = new char[54];
    static char[] q = new char[12];
    static char[] temp = new char[9];
    static int[][] rot = {
            {36, 37, 38, 18, 19, 20, 45, 46, 47, 27, 28, 29},        //U
            {33, 34, 35, 51, 52, 53, 24, 25, 26, 42, 43, 44},        //D
            {6, 7, 8, 44, 41, 38, 11, 10, 9, 45, 48, 51},            //F
            {2, 1, 0, 53, 50, 47, 15, 16, 17, 36, 39, 42},            //B
            {0, 3, 6, 35, 32, 29, 9, 12, 15, 18, 21, 24},            //L
            {8, 5, 2, 26, 23, 20, 17, 14, 11, 27, 30, 33}            //R
    };

    public static void main(String[] args) throws Exception {
        HashMap<Character, Integer> num2Idx = new HashMap<>();
        num2Idx.put('U', 0);
        num2Idx.put('D', 1);
        num2Idx.put('F', 2);
        num2Idx.put('B', 3);
        num2Idx.put('L', 4);
        num2Idx.put('R', 5);

        char[] colors = {'w', 'y', 'r', 'o', 'g', 'b'};

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 9; j++) {
                    arr[i * 9 + j] = colors[i];
                }
            }

            int n = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            for (int it = 0; it < n; it++) {
                String command = st.nextToken();
                int sideIdx = num2Idx.get(command.charAt(0));
                int time = command.charAt(1) == '+' ? 1 : 3;

                rotate(sideIdx, time);
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sb.append(arr[i * 3 + j]);
                }
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
    }

    static void rotate(int sideIdx, int time) {
        for (int t = 0; t < time; t++) {
            // 일단 기존 12개를 큐에 넣는다.
            for (int i = 0; i < 12; i++) {
                q[i] = arr[rot[sideIdx][i]];
            }
            // 3개 시프트한 색을 기존 arr에 넣는다.
            for (int i = 0; i < 12; i++) {
                arr[rot[sideIdx][i]] = q[(i + 3) % 12];
            }
            // 해당 면을 돌린다.
            int i = 0;
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    temp[k * 3 + (2 - j)] = arr[sideIdx * 9 + i++];
                }
            }
            for (int j = 0; j < 9; j++) {
                arr[sideIdx * 9 + j] = temp[j];
            }
        }
    }
}
