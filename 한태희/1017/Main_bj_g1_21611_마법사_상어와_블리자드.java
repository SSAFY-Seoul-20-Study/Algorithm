import java.util.*;
import java.io.*;

/*
다 풀어놓고도 도대체 내가 뭘 푼건지 모르겠는 문제

문제의 입력은 2차원 배열로 주어지지만, 문제 속 블록들의 작동들은 좌하우상 순서로 골뱅이처럼 돌아가는 1차원 배열의 형태를 띄고 있음을 알 수 있음.
contvert 함수에서 2차원 배열을 회오리 순서대로 1차원 배열로 변환함.
여기서 만들어진 배열은 inx:0이 중앙 위치이며, 그 뒤 순서대로 회오리 위치가 됨.

블리자드 마법을 구현하기 위해서 1차원 배열에서의 위치를 표현하는 점화식을 찾음.
블리자드 함수에는 상하좌우 방향과 최종거리 s가 주어짐.
현재 위치가 pos, 나아간 칸이 d라고 하면 (1<=d<=s)
위쪽:     pos(d) = pos(d-1) + 8*(d-1) + 7
아래쪽:   pos(d) = pos(d-1) + 8*(d-1) + 3
왼쪽:     pos(d) = pos(d-1) + 8*(d-1) + 1
오른쪽:   pos(d) = pos(d-1) + 8*(d-1) + 5
(초기조건으로, pos(0) = 0)

move 함수는 배열 밀기로 간단하게 구현

explode 함수는 4이상의 크기를 가진 그룹을 찾아 값을 0으로 만들고, bleed 함수는 문제에 나와있는 조건대로 (그룹 크기, 그룹원의 숫자)로 그룹을 변환함.
사실 이 두개는 그룹 만드는 함수를 하나로 합쳐서 더 간단하게 구현 가능할것 같긴 함.

 */

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
	}
}