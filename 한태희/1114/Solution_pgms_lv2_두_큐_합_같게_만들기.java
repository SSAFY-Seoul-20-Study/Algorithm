import java.util.*;

/*
두 큐의 합 구하기
완전 탐색이나 DP같은 방법으로는 공간, 시간 복잡도를 절대 감당 못한다는 것은 문제 입력을 보고 바로 인지했습니다.

빠르게 풀 수 있는 방법으로 그리디, 분할 정복, 구간합 등을 생각해보았습니다.

그렇게 생각해보면서, 왠지 큐가 두개밖에 없어서 가장 연산 횟수가 적은 방법은 두 큐의 차이를 탐욕적으로 좁히는 방법일 것 같다는 직감이 들어서 손풀이를 해보니까 예제 테케가 맞았고, 그대로 코드로 제출하니까 통과됐습니다.

결국 풀긴 풀었는데, 이게 왜 수학적으로 탐욕적 접근이 가능한지는 이해가 안된 상태입니다. 인터넷으로 검색해보니까 예상대로 구간합 풀이도 있더라고요.

더 공부를 해야할 문제인것 같습니다.
*/
class Solution {
    public int solution(int[] queue1, int[] queue2) {
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        long s1 = 0;
        long s2 = 0;
        int n = queue1.length;
        for (int i = 0; i < n; i++) {
            q1.add(queue1[i]);
            s1 += queue1[i];
            q2.add(queue2[i]);
            s2 += queue2[i];
        }

        for (int t = 0; t <= 3 * n; t++) {
            if (s1 == s2) {
                return t;
            } else if (s1 > s2) {
                int k = q1.poll();
                s1 -= k;
                q2.offer(k);
                s2 += k;
            } else {
                int k = q2.poll();
                s2 -= k;
                q1.offer(k);
                s1 += k;
            }
        }

        return -1;
    }

}
