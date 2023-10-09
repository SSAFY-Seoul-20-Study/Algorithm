package a1003;

import java.util.*;
import java.io.*;

public class Main_bj_11054_가장긴바이토닉부분수열 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		int[] dp1 = new int[N];
		int[] dp2 = new int[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int max = 0;
		for(int i = 0; i < N; i++) {
			dp1[i] = 1;
			for(int j = 0; j < i; j++) {
				if(arr[i] > arr[j]) {
					dp1[i] = Math.max(dp1[i], dp1[j] + 1);
				}
			}
		}
		
		for(int i = N - 1; i >= 0; i--) {
			dp2[i] = 1;
			for(int j = N - 1; j > i; j--) {
				if(arr[i] > arr[j]) {
					dp2[i] = Math.max(dp2[i], dp2[j] + 1);
				}
			}
		}

		for(int i = 0; i < N; i++) {
			max = Math.max(max, dp1[i] + dp2[i]);
		}

		System.out.println(max - 1);
	}
}
