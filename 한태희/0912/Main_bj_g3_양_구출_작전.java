import java.util.*;
import java.io.*;

public class Main_bj_g3_양_구출_작전 {

	static class Node {
		int snum; // sheep_num;
		int wnum; // wolf_num;
		int parrent; // parent_idx;

		Node(int sn, int wn, int p) {
			int snum = sn;
			int wnum = wn;
			parrent = p;
		}

		int getDepth(Node[] arr) {
			if (parrent == -1)
				return 0;
			else
				return arr[parrent].getDepth(arr) + 1;
		}

		@Override
		public String toString() {
			return "Node [snum=" + snum + ", wnum=" + wnum + ", parrent=" + parrent + "]";
		}

	}

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		Node[] arr = new Node[N + 1];
		boolean[] hca = new boolean[N + 1]; // has_child_arr

		arr[1] = new Node(0, 0, -1);

		for (int i = 2; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			char t = st.nextToken().charAt(0);
			int a = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());

			if (t == 'S') {
				arr[i] = new Node(a, 0, p);
			} else { // t=='W'
				arr[i] = new Node(0, a, p);
			}

			hca[p] = true;
		}

		// pq에 저장되는 배열
		// [depth, before, next]
		PriorityQueue<int[]> pq = new PriorityQueue<>(
				(o1, o2) -> -Integer.compare(o1[0], o2[0]));

		// leaf Node일 경우 다음 노드를 queue에 탐색 등록
		for (int i = 2; i <= N; i++) {
			if (hca[i] == false) {
				pq.offer(new int[] { arr[i].getDepth(arr) - 1, i, arr[i].parrent });
			}
		}

		// for (int[] t : pq) {
		// System.out.println(Arrays.toString(t));
		// }

		int ans;
		while (true) {
			for (int[] t : pq) {
				System.out.println(Arrays.toString(t));
			}
			System.out.println();
			int[] info = pq.poll();
			int depth = info[0];
			int before = info[1];
			int next = info[2];

			// 다음 노드가 없을 경우 ans를 업데이트하고 종료
			if (next == -1) {
				ans = arr[next].snum;
				break;
			}
			// 다음 노드가 있을 경우,
			// 만약 늑대가 다음 노드에 있는 경우 늑대가 양을 잡아먹고
			// 살아남은 양들이 다음 노드로 이동.
			else {
				// 늑대가 양을 잡아먹는다:
				// 만약 다음 섬의 늑대의 수가 더 많거나 같으면 양은 모두 죽고
				// 죽은 양 수만큼 배고픈 늑대가 사라진다.
				if (arr[next].wnum >= info[2]) {

				}
				// 만약 다음 섬의 양의 수가 더 많으면 배고픈 늑대는 모두 사라지고
				// 사라진 늑대만큼 양이 죽는다.
			}
		}

		System.out.println(ans);

		br.close();
	}

}