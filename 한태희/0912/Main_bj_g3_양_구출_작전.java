import java.util.*;
import java.io.*;

/**
 * 재귀적으로 생각하면, 섬 I와 해당 섬의 자식 섬 c1, c2,... ck가 있을 때,
 * 
 * 자식 섬들에서 생존한 양들의 합이 섬 I의 늑대의 수보다 많은 경우:
 * 양은 늑대의 수만큼 잡아먹히고, 섬 I의 늑대가 모두 사라진 후, 남은 양들이 섬 I로 건너온다.
 * 
 * 자식 섬들에서 생존한 양들의 합이 섬 I의 늑대의 수보다 적은 경우:
 * 모든 자식 섬의 양들은 잡아먹히고, 잡아먹힌 양의 수만큼 늑대가 사라진다.
 * 
 * 다음과 같이 생각할 수 있다.
 * 
 * 문제는, 섬의 개수가 10만개가 넘어서 이것을 재귀함수로 구현할 경우, StackOverFlowError가 발생할 것이라는 것이다.
 * 최악으로 10만개의 섬이 일렬로 연결 되어 있으면 재귀적으로 실행 불가능하다.
 * 
 * 결국 BottomUp적인 방식으로 구현할 수 밖에 없는데, 이를 위해서 큐를 사용한다.
 * 큐에는 자식이 없는 섬들만 항상 들어간다. 만약 임의의 섬의 탐색이 완료되면 해당 섬의 부모와의 연결을
 * 끊는 방식으로 자식이 없는 섬들을 늘려나간다.
 * 하나의 자식 섬이 하나의 부모 섬으로 건너가는 로직을 사용하게 되지만,
 * 복수의 자식 섬이 하나의 부모 섬으로 건너가는 로직과 동일하게 구현하면 된다.
 * 
 */
public class Main_bj_g3_양_구출_작전 {

	static class Island {
		long snum; // sheep_num;
		long wnum; // wolf_num;
		int parent; // parent_idx;
		int child_size = 0; // 연결되어 있는 자식 섬의 개수

		void init(int s, int w, int p) {
			snum = s;
			wnum = w;
			parent = p;
		}

		@Override
		public String toString() { // 디버그용
			return "Island [snum=" + snum + ", wnum=" + wnum + ", parent=" + parent + "]";
		}

	}

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 섬 개수를 입력 받는다.
		int N = Integer.parseInt(br.readLine());
		Island[] arr = new Island[N + 1];

		// 각 섬을 초기화한다.
		for (int i = 1; i <= N; i++) {
			arr[i] = new Island();
		}

		// 1번 섬은 루트 노드이다.
		arr[1].init(0, 0, -1);

		// 양이 있는 섬, 혹은 늑대가 있는 섬의 정보를 입력받는다.
		for (int i = 2; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			char t = st.nextToken().charAt(0);
			int a = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());

			if (t == 'S') {
				arr[i].init(a, 0, p);
			} else { // t=='W'
				arr[i].init(0, a, p);
			}
			arr[p].child_size++;
		}

		// 자식이 없는 섬들을 q에 넣음
		Queue<Integer> q = new ArrayDeque<>();
		for (int i = 2; i <= N; i++) {
			if (arr[i].child_size == 0) {
				q.add(i);
			}
		}

		long ans;
		while (true) {
			int k = q.poll();

			// 1번 섬이 활성화 되면 그때 1번 섬의 양의 숫자가 정답.
			if (k == 1) {
				ans = arr[k].snum;
				break;
			}
			// 1번 섬이 아닐 경우,
			// 만약 늑대가 다음 섬에 있는 경우 늑대가 양을 잡아먹고
			// 살아남은 양들이 다음 섬로 이동.
			int p = arr[k].parent;
			// 만약 다음 섬의 늑대의 수가 더 많거나 같으면 양은 모두 죽고 (==건너오지 못하고)
			// 죽은 양 수만큼 늑대가 사라진다.
			if (arr[p].wnum >= arr[k].snum) {
				arr[p].wnum -= arr[k].snum;
			}
			//
			// 만약 다음 섬의 양의 수가 더 많으면 다음 섬의 수 만큼 이전 섬의 양이 죽고
			// 다음 섬의 늑대는 모두 사라진다.
			// 생존한 양은 다음 섬으로 넘어간다.
			else { // arr[k].snum > arr[p].wnum
				arr[k].snum -= arr[p].wnum;
				arr[p].wnum = 0;
				arr[p].snum += arr[k].snum;
			}

			// 부모 섬의 자식 개수를 1 감소시킨다.
			arr[p].child_size--;
			// 만약 부모 섬의 자식 개수가 0이라면, 해당 섬을 활성화 시킨다. (큐에 넣는다.)
			if (arr[p].child_size == 0) {
				q.add(p);
			}
		}

		System.out.println(ans);

		br.close();
	}

}