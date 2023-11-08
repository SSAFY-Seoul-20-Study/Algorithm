import java.util.*;

/*
양궁 대회

쓸데없이 시선을 분산시키는 라이언 어피치가 있는 양궁과녁 사진이 제일 짜증나는 문제였습니다.

처음엔 완탐을 고려해 보았지만, n이 커지면 11^n 꼴로 복잡도가 커질것 같아서 중단했습니다.

'아니 이걸 탐색을 안하고 어떻게 풀지? DP나 이분탐색으로도 안되는데??' 라고 생각하며 테케를 자세히 보다 문제에서 함의하고 있는 방법대로 풀면 되는 탐색+구현문제라는 것을 깨달았습니다.

이 문제에서 함의하고 있는 중요 사실은
```
라이언이 어피치를 제치고 점수 K를 얻기 위해선, K 과녁에 어피치보다 딱 한개 더 많은 화살을 꽂으면 된다.
```
입니다.

따라서, 탐색의 분기는 라이언이 어피치보다 딱 하나 더 많은 화살을 꽂거나, 해당 점수는 깔끔하게 포기하거나 둘중 하나임을 알 수 있습니다.

그리고, 문제에서도 밑줄이 쳐진 점수의 차가 동점일 경우에 대한 예외처리까지 하면 문제가 풀립니다.
*/
class Solution {
    int N;
    int[] ohter_info, ans_info, temp_info;
    int max_diff = 0;

    public int[] solution(int n, int[] info) {
        N = n;
        ohter_info = info;
        ans_info = new int[11];
        temp_info = new int[11];

        if (ohter_info[10 - 10] < N) {
            int K = ohter_info[10 - 10] + 1;
            temp_info[10 - 10] += K;
            N -= K;
            search(10 - 1);
            temp_info[10 - 10] -= K;
            N += K;
        }

        search(10 - 1);

        if (max_diff == 0) {
            return new int[] { -1 };
        } else {
            return ans_info;
        }
    }

    void search(int t) {
        if (t == 0) {
            temp_info[10 - 0] += N;
            int x_sum = 0, y_sum = 0;
            for (int i = 0; i < 11; i++) {
                int x = temp_info[10 - i];
                int y = ohter_info[10 - i];

                if (x == 0 && y == 0) {
                    continue;
                } else if (x > y) {
                    x_sum += i;
                } else {
                    y_sum += i;
                }
            }
            int diff = x_sum - y_sum;
            if (diff > max_diff || (diff == max_diff && checkChangeCond())) {
                max_diff = diff;
                ans_info = new int[11];
                for (int i = 0; i < 11; i++) {
                    ans_info[i] = temp_info[i];
                }
            }

            temp_info[10 - 0] -= N;
            return;
        }

        if (ohter_info[10 - t] < N) {
            int K = ohter_info[10 - t] + 1;
            temp_info[10 - t] += K;
            N -= K;
            search(t - 1);
            temp_info[10 - t] -= K;
            N += K;
        }

        search(t - 1);

    }

    boolean checkChangeCond() {
        for (int i = 0; i < 11; i++) {
            int x = temp_info[10 - i];
            int y = ans_info[10 - i];
            if (x > y)
                return true;
            else if (y > x)
                return false;
        }
        return true;
    }
}