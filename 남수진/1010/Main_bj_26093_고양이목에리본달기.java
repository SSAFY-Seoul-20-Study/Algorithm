package a1010;

import java.util.*;
import java.io.*;

public class Main_bj_26093_고양이목에리본달기 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[][] cat = new int[N][K];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < K; j++) {
				cat[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int max = 0, max2 = 0;
		for(int i = 0; i < K; i++) {
			if(cat[0][i] > max) {
				max2 = max;
				max = cat[0][i];
			} else if(cat[0][i] > max2) {
				max2 = cat[0][i];
			}
		}

		for(int i = 1; i < N; i++) {
			int now = 0, now2 = 0;
			for(int j = 0; j < K; j++) {
				if(cat[i - 1][j] == max) {
					cat[i][j] += max2;
				} else {
					cat[i][j] += max;
				}
				
				if(cat[i][j] > now) {
					now2 = now;
					now = cat[i][j];
				} else if(cat[i][j] > now2) {
					now2 = cat[i][j];
				}
			}
			max = now;
			max2 = now2;
		}
		
		int ans = 0;
		for(int i = 0; i < K; i++) {
			ans = Math.max(ans, cat[N - 1][i]);
		}
		System.out.println(ans);

	}

}
