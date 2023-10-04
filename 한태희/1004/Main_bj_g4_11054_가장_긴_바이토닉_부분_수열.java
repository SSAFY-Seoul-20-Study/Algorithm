import java.util.*;
import java.io.*;

public class Main_bj_g4_11054_가장_긴_바이토닉_부분_수열 {
	static int N;
	static int[] a;
	static int[] inc, dec;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		a = new int[N];
		inc = new int[N];
		dec = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			a[i] = Integer.parseInt(st.nextToken());
			inc[i] = 1;
			dec[i] = 1;
		}

		for (int i = 1; i < N; i++) {
			for (int j = 0; j < i; j++) {
				if (a[j] < a[i]) {
					inc[i] = Math.max(inc[i], inc[j] + 1);
				}
			}
		}

		for (int i = N - 2; i >= 0; i--) {
			for (int j = N - 1; j > i; j--) {
				if (a[j] < a[i]) {
					dec[i] = Math.max(dec[i], dec[j] + 1);
				}
			}
		}

		int ans = 0;
		for (int i = 0; i < N; i++) {
			ans = Math.max(ans, inc[i] + dec[i] - 1);
		}

		System.out.println(ans);

		br.close();
	}
}