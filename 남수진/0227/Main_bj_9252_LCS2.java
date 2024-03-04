import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main {

    static String str1, str2;
    static int[][] dp;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        str1 = br.readLine();
        str2 = br.readLine();

        dp = new int[str1.length() + 1][str2.length() + 1];

        for(int i = 1; i <= str1.length(); i++) {
            for(int j = 1; j <= str2.length(); j++) {
                if(str1.charAt(i - 1) == str2.charAt(j - 1)){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        System.out.println(dp[str1.length()][str2.length()]);
        System.out.println(find(str1.length(), str2.length()));
    }

    static String find(int idx1, int idx2) {
        ArrayDeque<Character> stk = new ArrayDeque<>();

        while(idx1 > 0 && idx2 > 0) {
            if(dp[idx1][idx2] == dp[idx1 - 1][idx2]) idx1--;
            else if(dp[idx1][idx2] == dp[idx1][idx2 - 1]) idx2--;
            else {
                stk.push(str1.charAt(idx1 - 1));
                idx1--;
                idx2--;
            }
        }

        StringBuilder sb = new StringBuilder();

        while(!stk.isEmpty()) {
            sb.append(stk.pop());
        }

        return sb.toString();
    }

}
