package a0116;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_9527_1의개수세기 {
    static long[] bit; // 0부터 2^n 일 때 1의 개수 누적합
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        long A = Long.parseLong(st.nextToken());
        long B = Long.parseLong(st.nextToken());

        bit = new long[55]; // 10^16 비트 길이 54
        bit[0] = 1; // 2^0 = 1 일 때, 1의 개수 = 1

        // 누적합 저장
        for(int i = 1; i < 55; i++) {
            // 2^(i)일 때 1의 개수
            // bit[i - 1] * 2 => 누적합 + (2^i에 있는 2^(i-1) 개수)
            // 1L << i => 2^(i-1)에 1을 붙일 때  => 2^i개 추가
            bit[i] = 2 * bit[i - 1] + (1L << i);
        }

        // B의 누적합 - (A-1)의 누적합
        long ans = getBitCount(B) - getBitCount(A - 1);
        System.out.println(ans);


    }

    // 1 ~ N 정수에 대한 1의 개수
   static long getBitCount(long x) {
        long ans = x & 1; // 0번째 비트가 1일 경우, 인덱스가 음수가 되므로 처음 ans에 추가

        for(int i = 54; i> 0; i--) {
            if((x & (1L << i)) > 0L){ // x의 i번째 비트가 1이면
                // ex) 14 = 1110 개수
                // x의 첫번째 bit가 1이 나타나기 전까지 (000 ~ 111) => bit[2]
                // x의 첫번째 bit를 제외한 값들이 변할 때마다 첫번째 bit는 항상 존재 => x - 1L << i
                // +1 => 1000인 경우
                ans += bit[i - 1] + (x - (1L << i) + 1);
                x -= (1L << i); // x의 첫번째 bit 제거
            }
        }
        return ans;
   }
}
