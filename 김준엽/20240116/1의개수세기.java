package a0105;

import java.util.*;
import java.io.*;
public class Main {
    static long start, end;
    static long oneCount, ans;
    static long[] dp = new long[64];
    public static void main(String[] args) throws Exception{
        System.setIn(new FileInputStream("/Users/kim-junyup/Downloads/ssafy_algo_backup/algo/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        start = Long.parseLong(st.nextToken());
        end = Long.parseLong(st.nextToken());
        initDp();
        ans = calc(end) - calc(start - 1);
        System.out.println(ans);
    }
    static long calc(long n){
        long count = n & 1;
        int size = (int) (Math.log(n) / Math.log(2)); //bit size
        //N - (1L << i) : 지정된 1이 반복 사용될 개수
        // + 1 : 1000...
        for(int i=size;i>0;i--) {
            if((n & (1L<<i)) != 0L) {
                count += dp[i-1] +(n - (1L<<i) + 1);
                n -= (1L << i);	//비트 이동시키기
            }
        }
        return count;
    }
    static void initDp(){
        dp[0] = 1;
        for (int i = 1; i < 64; i++) {
            dp[i] = (dp[i-1] * 2) + (1L << i);
        }
    }
}