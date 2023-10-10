package a1010;

import java.io.*;
import java.util.*;


//90796KB	612ms
public class Main_boj_26093_고양이목에리본달기 {
	
	static int N, K;
	static int[][] dp;
	static int max=0, preMax=0;
	
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_boj_26093.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 입력받기
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		dp = new int[N][K];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<K; j++) {
				dp[i][j] = Integer.parseInt(st.nextToken());
				
				if(i == 0) {
					if(dp[i][j] >= max) {
						preMax = max;
						max = dp[i][j];
					} else if(dp[i][j] > preMax) {
						preMax = dp[i][j];
					}
				}
			}
		}
		
		for(int i=1; i<N; i++) {
			int newMax=0, newPreMax=0;
			for(int j=0; j<K; j++) {
				dp[i][j] += (max == dp[i-1][j]) ? preMax : max;
				
				if(dp[i][j] >= newMax) {
					newPreMax = newMax;
					newMax = dp[i][j];
				} else if (dp[i][j] > newPreMax) {
					newPreMax = dp[i][j];
				}
			}
			max = newMax;
			preMax = newPreMax;
		}
		
		Arrays.sort(dp[N-1]);
		System.out.println(dp[N-1][K-1]);
		br.close();
	}
}

// 바로 직전 꺼에만 영향을 받음!
