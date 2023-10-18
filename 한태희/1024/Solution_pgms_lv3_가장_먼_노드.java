/*
BFS를 이용해 간단한 다익스트라 알고리즘을 구현하면 되는 문제.

BFS로 각 노드를 중복되지 않게 방문하면서, 해당 노드를 방문했을 때의 거리 (1번 노드로부터의 최단거리)를 배열 arr에 저장한다.

그 뒤, 기수배열 counter를 사용하여, arr 배열을 순회하면서 각 거리의 등장 빈도를 기록한다.

coutner 배열에서 0이 아니면서 가장 오른쪽에 있는 숫자가 정답이다.
 */

import java.util.*;

class Solution {
    List<Integer>[] e;

    public int solution(int n, int[][] edge) {
        e = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            e[i] = new ArrayList<>();
        }

        for (int[] info : edge) {
            e[info[0]].add(info[1]);
            e[info[1]].add(info[0]);
        }

        Queue<Integer> q = new ArrayDeque<>();
        int[] arr = new int[n + 1];
        boolean[] v = new boolean[n + 1];

        q.add(1);
        for (int i = 2; i <= n; i++) {
            arr[i] = Integer.MAX_VALUE;
        }

        while (q.isEmpty() == false) {
            int now = q.poll();
            for (int next : e[now]) {
                if (v[next])
                    continue;
                v[next] = true;
                arr[next] = Math.min(arr[next], arr[now] + 1);
                q.add(next);
            }
        }

        int[] counter = new int[n + 2];
        for (int i = 2; i <= n; i++) {
            int dist = arr[i];
            counter[dist]++;
        }
        int t = 2;
        while (counter[t] != 0) {
            t++;
        }
        int ans = counter[t - 1];

        return ans;
    }
}