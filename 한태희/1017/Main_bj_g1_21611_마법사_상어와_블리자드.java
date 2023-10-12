import java.util.*;
import java.io.*;

public class Main_bj_g1_21611_마법사_상어와_블리자드 {
	static int[] arr;
	static int ans;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		int[][] temp = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				temp[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		convert(temp);

		ans = 0;

		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			blizard(temp.length, d, s);
			while (explode()) {
			}
			breed();
			System.out.println();
			System.out.println((m + 1) + "번째 블리자드 후");
			System.out.println(Arrays.toString(arr));
			System.out.println();
		}

		System.out.println(ans);

		br.close();
	}

	private static void convert(int[][] temp) {
		int N = temp.length;
		int r = N / 2;
		int c = N / 2;

		int[] dr = { 1, 0, -1, 0 };
		int[] dc = { 0, 1, 0, -1 };

		arr = new int[N * N];
		int p = 0;
		for (int d = 1; d <= N / 2; d++) {
			r += dr[3];
			c += dc[3];
			p++;
			arr[p] = temp[r][c];

			for (int i = 0; i < 2 * d - 1; i++) {
				r += dr[0];
				c += dc[0];
				p++;
				arr[p] = temp[r][c];
			}

			for (int k = 1; k < 4; k++) {
				for (int i = 0; i < 2 * d; i++) {
					r += dr[k];
					c += dc[k];
					p++;
					arr[p] = temp[r][c];
				}
			}
		}
	}

	static int[] blizard(int N, int dir, int s) {
		int point = 0;
		int k = -1;
		if (dir == 1) {
			k = 7;
		} else if (dir == 2) {
			k = 3;
		} else if (dir == 3) {
			k = 1;
		} else if (dir == 4) {
			k = 5;
		}

		for (int d = 0; d < s; d++) {
			point += 8 * d + k;
			arr[point] = 0;
		}
		System.out.println("블리자드");
		System.out.println(Arrays.toString(arr));
		move();
		return arr;
	}

	static void move() {
		int[] ret = new int[arr.length];
		int p = 1;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] != 0) {
				ret[p++] = arr[i];
			}
		}
		arr = ret;
		System.out.println("밀어내기");
		System.out.println(Arrays.toString(arr));
	}

	static boolean explode() {
		boolean ret = false;
		int startP = 1;
		int endP = 1;
		int gsize = 1;

		if (arr[startP] == 0) {
			return ret;
		}

		while (true) {
			endP++;
			if (endP == arr.length) {
				break;
			} else if (arr[startP] == arr[endP]) {
				gsize++;
			} else {
				if (gsize >= 4) {
					ret = true;
					ans += arr[startP] * gsize;
					for (int i = startP; i < endP; i++) {
						arr[i] = 0;
					}
				}
				if (arr[endP] == 0) {
					break;
				}
				startP = endP;
				gsize = 1;
			}
		}

		System.out.println("폭발");
		System.out.println(Arrays.toString(arr));
		move();
		return ret;
	}

	static void breed() {
		int[] temp = new int[arr.length * 2];
		int startP = 1;
		int endP = 1;
		int gsize = 1;
		int wp = 1;

		if (arr[startP] == 0) {
			return;
		}

		while (true) {
			endP++;
			boolean cond1 = endP == arr.length || arr[endP] == 0;
			boolean cond2 = false;
			if (cond1 == false) {
				cond2 = arr[startP] != arr[endP];
			}
			if (cond1 || cond2) {
				temp[wp++] = gsize;
				temp[wp++] = arr[startP];
				if (cond1) {
					break;
				}
				startP = endP;
				gsize = 1;

			} else {
				gsize++;
			}
		}
		int[] ret = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = temp[i];
		}
		arr = ret;
		System.out.println("증식");
		System.out.println(Arrays.toString(arr));
	}
}