import java.util.*;
import java.io.*;

// 	14308KB	128ms
public class boj_16637 {
    static ArrayList<Integer> nums = new ArrayList<>();
    static ArrayList<Character> opers = new ArrayList<>();
    static int ans = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("res/input_boj_16637.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        String input = br.readLine();

        for (int i = 0; i < N; i++) {
            char c = input.charAt(i);
            if (i % 2 == 0)
                nums.add(Character.getNumericValue(c));
            else
                opers.add(c);
        }

        dfs(nums.get(0), 0);
        System.out.println(ans);

        br.close();
    }

    // DFS, 백트래킹
    public static void dfs(int result, int opIdx) {
        // 주어진 연산자의 개수를 초과했을 때, 종료
        if (opIdx >= opers.size()) {
            ans = Math.max(ans, result);
            return;
        }

        // 괄호가 없는 경우
        int res1 = calc(opers.get(opIdx), result, nums.get(opIdx+1));
        dfs(res1, opIdx + 1);

        // 괄호가 있는 경우
        if (opIdx + 1 < opers.size()) {
            int res2 = calc(opers.get(opIdx + 1), nums.get(opIdx + 1), nums.get(opIdx + 2));

            // 현재 result와 방금 구한 괄호 값을 연산한 결과와 괄호 오른쪽에 존재하는 연산자의 인덱스를 넘김.
            dfs(calc(opers.get(opIdx), result, res2), opIdx + 2);
        }
    }

    public static int calc(char op, int n1, int n2) {
        switch (op) {
            case '+':
                return n1 + n2;
            case '-':
                return n1 - n2;
            case '*':
                return n1 * n2;
            default:
                return -1;
        }
    }
}
