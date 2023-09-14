import java.util.*;
import java.io.*;

/**
 * 인터넷에서 아이디어를 참고했습니다.
 * 
 * 만약 부분집합을 사용하며, 조건에 만족하지 않는 케이스를 중간에 프루닝 하더라도, 경우의 수가 1억이 넘어가기 때문에 제한 시간 안에
 * 실행이 불가능하다.
 * 필연적으로 이 문제는 다이나믹 프로그래밍, 이분탐색, 그래프 이론 중 하나를 사용해야 함을 알 수 있다.
 * 
 * 이 문제는 다이나믹 프로그래밍을 사용해서 풀 수 있다.
 * 이 문제의 첫번째 난관은 마을들이 트리 구조로 연결 되어 있으면서, 막상 루트 노드를 특정해주지는 않는다는 것이다.
 * 이것을 해결하기 위해선 트리의 속성에 대한 지식이 필요한데, 임의의 노드를 루트 노드로 선정하면 그 루트 노드를 기준으로 트리가 구성
 * 가능하다.
 * 
 * 트리가 계층으로 구성되었으면, 임의의 노드 N이 있다고 하고, 해당 노드의 자식들을 c1, c2, ... ck 라고 할때, 다음과 같은
 * 재귀 식을 세울 수 있다.
 * 노드 N의 인구 population(N)
 * 노드 N을 우수 마을로 선정 했을 때, 해당 노드를 루트로 하는 서브 트리의 우수마을 인구의 합 choice(N)
 * 노드 N을 우수 마을로 선정하지 않았을 때, 해당 노드를 루트로 하는 서브 트리의 우수마을 인구의 합 abandon(N)
 * choice(N) = population(N) + abandon(c1) + abandon(c2) + ... + abandon(ck)
 * abandon(N) = Max( choice(c1), abandon(c1) ) + Max( choice(c2), abandon(c2) )
 * + ... + Max( choice(ck), abandon(ck) )
 * 
 * 헤맸던 부분
 * 3번 조건. 선정되지 못한 마을에 경각심을 불러일으키기 위해서, '우수 마을'로 선정되지 못한 마을은 적어도 하나의 '우수 마을'과는
 * 인접해 있어야 한다.
 * 이 부분을 고민하느라고 문제를 풀지 못했습니다.
 * 인터넷을 보니, 이 부분은 최대값을 구하는 과정에서 자연스럽게 만족된다는 것을 알게 되었습니다.
 * 
 * 생각해보면 다음과 같습니다.
 * 만약 마을 A-B-C-D-E가 있다면 X-B-X-X-E로 선택된 경우가 A-X-C-X-E로 선택된 경우보다 더 클때,
 * abandon(A)는 자연스럽게 X-B-X-X-E로 업데이트 되게 되고, abandon(A)가 choice(A)보다 크게 됨으로 자연스럽게
 * 해답을 구할 수 있습니다.
 * A-X-X-X-E 는 무조건 A-X-C-X-E보다 작게 됨으로, 선택되지 않는 마을이 우수 마을과 인접하지 않는 경우는 무조건 최대값이
 * 아닙니다.
 */
public class Main_bj_g2_1949_우수_마을 {
	static int N;
	static int[] pop;
	static List[] next;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 마을의 개수 N
		N = Integer.parseInt(br.readLine());
		// 임의의 마을 E의 인구 Pouplation(E)
		pop = new int[N + 1];
		// 임의의 마을 E와 연결된 마을들 next(E)
		next = new List[N + 1];

		// 배열 초기화 및 인구 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			pop[i] = (Integer.parseInt(st.nextToken()));
			next[i] = new ArrayList<>();
		}

		// 주어진 연결을 입력함
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			next[a].add(b);
			next[b].add(a);
		}

		// 임의로 루트 노드를 정한다.
		int v = -1;
		for (int i = 1; i <= N; i++) {
			if (next[i].size() == 1) {
				v = i;
				break;
			}
		}

		// 임의의 루트 노드를 기준으로 재귀 탐색을 진행하고, choice(v) 와 abandon(v) 중 더 큰 것을 출력한다.
		int max = -1;
		for (int k : search(v)) {
			max = Math.max(max, k);
		}
		System.out.println(max);

		br.close();
	}

	/**
	 * 임의의 노드 v에 대하여, {choice(v), abandon(v)} 를 반환
	 */
	static int[] search(int v) {
		List<Integer> childs = next[v];

		List<int[]> res = new ArrayList<>();
		for (int c : childs) {
			// 자식 c의 {choice(c), abandon(c)}를 구해서 res List에 더한다.
			next[c].remove(Integer.valueOf(v));
			res.add(search(c));
		}

		// 선택을 하는 경우
		// choice(N) = population(N) + abandon(c1) + abandon(c2) + ... + abandon(ck)
		int choice = pop[v];
		for (int[] l : res) {
			choice += l[1];
		}

		// 선택을 안하는 경우
		// abandon(N) = Max( choice(c1), abandon(c1) ) + Max( choice(c2), abandon(c2) )
		// + ... + Max( choice(ck), abandon(ck) )
		int abandon = 0;
		for (int[] l : res) {
			abandon += Math.max(l[0], l[1]);
		}
		return new int[] { choice, abandon };
	}

}