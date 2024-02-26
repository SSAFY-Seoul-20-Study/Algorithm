package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class boj_9252_lcs2 {

	static char[] contrast, experiment;
	static int[][] dp;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		contrast = br.readLine().toCharArray();
		experiment = br.readLine().toCharArray();

		dp = new int[experiment.length + 1][contrast.length + 1];
		for (int i = 1; i <= experiment.length; i++) {
			for (int j = 1; j <= contrast.length; j++) {
				if (experiment[i - 1] == contrast[j - 1]) {
					// 같네? 대각선위+1
					dp[i][j] = dp[i - 1][j - 1] + 1;
				} else {
					// 다르네? 위와 왼쪽 맥스
					dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
				}
			}
		}

		System.out.println(dp[experiment.length][contrast.length]);
		if (dp[experiment.length][contrast.length] != 0) {
			StringBuilder sb = new StringBuilder();
			int ei = experiment.length, ci = contrast.length;
			while (ei != 0 && ci != 0) {
				if (experiment[ei - 1] == contrast[ci - 1]) {
					sb.append(experiment[ei - 1]);
					ei--;
					ci--;
					continue;
				}
				if (dp[ei - 1][ci] < dp[ei][ci - 1]) {
					// 왼쪽이 더 커
					ci--;
				} else {
					ei--;
				}
			}
			StringBuilder sbReversed = new StringBuilder();
			for (int i = sb.length()-1; i >= 0 ; i--) {
				sbReversed.append(sb.charAt(i));
			}
			System.out.println(sbReversed);
		}

	}

	static void backTracking(StringBuilder sb, int i, int j) {
		if (i < 1 || j < 1) {
			return;
		}
		if (experiment[i - 1] == contrast[j - 1]) {
			// 같으면 추가
			sb.append(experiment[i - 1]);
		}

		if (dp[i - 1][j] < dp[i][j - 1]) {
			// 왼쪽이 더 큼 그쪽으로 ㄱ
			backTracking(sb, i - 1, j - 2);
		} else {
			backTracking(sb, i - 2, j - 1);
		}
	}
}
