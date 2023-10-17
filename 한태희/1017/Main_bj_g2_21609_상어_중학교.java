import java.util.*;
import java.io.*;

/*
구현의 끝판왕 문제
난해한 알고리즘은 없이 문제에 주어진 상황을 그대로 구현하면 되지만,
그 구현양이 매우 많은 것이 문제다.

무지개 블록이 아닌 일반 블록 중 대표 블록 선정: PQ를 이용하여 정렬
각 블록이 묶여있는 집합 :Group Class를 작성. Group Class에 Comparable 인터페이스를 정의한다.
Group 클래스는 문제에 주어진 대로 정렬되며 정령은 PQ를 이용하여 달성한다.
=> 결론, PQ 2개 사용

그룹 탐색에서 v 와 local_v의 두개의 visited 배열 사용.
<용어: 일반 블록 -> 값 1 이상의 블록 (무지개 블록이 아닌 삭제 가능한 블록)>
v배열: 일반블록에만 방문 체크 된다. 일반블록은 오로지 하나의 그룹에만 속하므로, 일반 블록이 중복되는 그룹에 속하지 않도록 막는 역할을 한다.
local_v배열: 하나의 그룹을 탐색하는 과정에서 생성되고, 삭제된다. 한번의 그룹 탐색에서 한번 탐색한 위치는 다시는 들어가지 않도록 하는 역할을 한다.

-2는 임의로 비어있는 위치에 대한 정보로 정의하였다.

이 문제에서 가장 어려웠던 부분은 중력에 의해 블록이 떨어지는 함수인 gravity() 였다.
현재 탐색 위치와 별개로 현재 탐색 위치의 블록이 떨어져야 하는 위치를 나타내는 변수인 t를 이용하여 구현했다.
t는 블록이 떨어질때만 증가하고, 만약 탐색 위치 i가 금강불괴 블록(-1블록)이나 끝 위치를 만나면 i+1이상 t이하의 위치를 모두 -2 (비어있는 공간)으로 채운다.

 */
public class Main_bj_g2_21609_상어_중학교 {
	static int N, M;
	static int[][] arr;
	static boolean[][] v;

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	static int score;

	static class Group implements Comparable {
		PriorityQueue<int[]> normalCords = new PriorityQueue<>(
				(o1, o2) -> {
					if (o1[0] == o2[0]) {
						return Integer.compare(o1[1], o2[1]);
					} else {
						return Integer.compare(o1[0], o2[0]);
					}
				});
		List<int[]> rainbowCords = new ArrayList<>();

		public int compareTo(Object o) {
			Group ohter = (Group) o;
			if (this.size() == ohter.size()) {
				if (this.rainbowCords.size() == ohter.rainbowCords.size()) {
					if (this.normalCords.peek()[0] == ohter.normalCords.peek()[0]) {
						return -Integer.compare(this.normalCords.peek()[1], ohter.normalCords.peek()[1]);
					} else {
						return -Integer.compare(this.normalCords.peek()[0], ohter.normalCords.peek()[0]);
					}
				} else {
					return -Integer.compare(this.rainbowCords.size(), ohter.rainbowCords.size());
				}
			} else {
				return -Integer.compare(this.size(), ohter.size());
			}
		}

		int size() {
			return this.normalCords.size() + this.rainbowCords.size();
		}
	}

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		score = 0;
		while (true) {
			PriorityQueue<Group> groups = getGroupPQ();
			if (groups.size() == 0) {
				break;
			}
			Group tg = groups.peek();
			deleteTargetGroup(tg);
			gravity();
			rotate();
			gravity();
		}
		System.out.println(score);

		br.close();
	}

	static PriorityQueue<Group> getGroupPQ() {
		v = new boolean[N][N];
		PriorityQueue<Group> retPQ = new PriorityQueue<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (v[i][j]) {
					continue;
				} else if (arr[i][j] <= 0) {
					continue;
				}

				Group group = makeGroup(new Group(), new boolean[N][N], arr[i][j], i, j);
				if (group.size() > 1) {
					retPQ.add(group);
				}
			}
		}

		return retPQ;
	}

	static Group makeGroup(Group g, boolean[][] local_v, int color, int r, int c) {
		if (arr[r][c] != 0) {
			g.normalCords.add(new int[] { r, c });
			v[r][c] = true;
		} else if (arr[r][c] == 0) {
			g.rainbowCords.add(new int[] { r, c });

		} else {
			return g;
		}
		local_v[r][c] = true;

		for (int k = 0; k < 4; k++) {
			int nr = r + dr[k];
			int nc = c + dc[k];
			if (isValid(nr, nc) == false) {
				continue;
			}
			if (local_v[nr][nc]) {
				continue;
			}
			if (arr[nr][nc] == color || arr[nr][nc] == 0) {
				makeGroup(g, local_v, color, nr, nc);
			}
		}

		return g;
	}

	static void deleteTargetGroup(Group tg) {
		List<int[]> tgCords = tg.rainbowCords;
		tgCords.addAll(tg.normalCords);
		for (int[] cord : tgCords) {
			int r = cord[0];
			int c = cord[1];
			arr[r][c] = -2;
		}
		score += (tgCords.size() * tgCords.size());
	}

	static void gravity() {
		for (int j = 0; j < N; j++) {
			int t = N - 1;
			for (int i = N - 1; i >= 0; i--) {
				if (arr[i][j] != -2 && arr[i][j] != -1) {
					arr[t--][j] = arr[i][j];
				} else if (arr[i][j] == -1) {
					for (int k = i + 1; k <= t; k++) {
						arr[k][j] = -2;
					}
					t = i - 1;
				}
			}
			for (int k = 0; k <= t; k++) {
				arr[k][j] = -2;
			}
		}
	}

	static void rotate() {
		int[][] temp = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				temp[N - j - 1][i] = arr[i][j];
			}
		}
		arr = temp;
	}

	static boolean isValid(int r, int c) {
		if (0 <= r && r < N && 0 <= c && c < N) {
			return true;
		} else {
			return false;
		}
	}
}