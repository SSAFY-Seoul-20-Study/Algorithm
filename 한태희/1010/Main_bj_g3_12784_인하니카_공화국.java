import java.util.*;
import java.io.*;

/*
재귀적인 트리 탐색 문제.
점화식 search(parent_node, parent_weight) 는 아래와 같이 정의된다.
```
      │ parent_weight
      ▼
    ┌───┐
    │   │parent_node
    └─┬─┘
│     │     │  sum( search(child_node, child_weight) )
▼     ▼     ▼

  return min(parent_weight, sum(search(child_node, child_weight)) )
  ```

1번 섬에 연결되어 있는 가상의 섬을 가정하고, search(1, INF)로 탐색을 시작하면, 답을 구할 수 있다.

해멨던 부분은 N=1인 경우에 대한 처리였다. 문제를 읽어보면 N=1인 경우에 대한 직접적인 언급을 찾을 수 없다. 하지만, 잭더리퍼가 존재하는 조건을 잘 생각해보면 잭더리퍼는 N=1일때 존재하지 않음을 알 수 있다. 따라서 섬이 하나라 다리가 존재하지 않고 다리를 터트릴 필요도 없으므로 필요한 다이너마이트 개수는 0이다.

N=1인 경우는 점화식으로 도출되지 않으므로 (점화식의 결과는 INF임) 따로 예외처리를 해주어야 한다.
 */

public class Main_bj_g3_12784_인하니카_공화국 {
	static int N, M;
	static boolean[] v;
	static List<int[]>[] neisArr;

	final static int INF = Integer.MAX_VALUE;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			neisArr = new List[N + 1];
			for (int i = 1; i <= N; i++) {
				neisArr[i] = new ArrayList<>();
			}

			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				neisArr[x].add(new int[] { y, d });
				neisArr[y].add(new int[] { x, d });
			}

			v = new boolean[N + 1];
			v[1] = true;

			int ans = search(1, INF);
			if (ans == INF) {
				ans = 0;
			}

			sb.append(ans).append("\n");
		}

		System.out.print(sb);

		br.close();
	}

	static int search(int parent, int k) {
		int l = 0;
		for (int[] info : neisArr[parent]) {
			int child = info[0];
			if (v[child]) {
				continue;
			}
			int d = info[1];

			v[child] = true;
			l += search(child, d);
		}
		if (l == 0) {
			l = INF;
		}
		return Integer.min(k, l);
	}

}