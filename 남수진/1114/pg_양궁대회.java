import java.util.*;
class Solution {
    
    static int[] answer, tmp;
    static int max = 0, maxCnt = 0, minIdx = 0;
    public int[] solution(int n, int[] info) {        
        answer = new int[11];
        tmp = new int[11];
        
        recur(n, 0, info);

        if(max == 0) return new int[] {-1};
        else return answer;
    }
    
    static void recur(int cnt, int start, int[] info) {
        if(cnt == 0) {
            int sumA = 0, sumB = 0, count = 0, idx = 0;
            for(int i = 0; i <= 10; i++) {
                if(info[i] < tmp[i]) sumB += 10 - i;
                else if(info[i] > tmp[i]) sumA += 10 - i;
                if(tmp[i] != 0) {
                    count = tmp[i];
                    idx = 10 - i;
                }
            }
            boolean flag = false;
            
            if(sumA < sumB) {
                if(max < sumB - sumA) {
                    minIdx = idx;
                    maxCnt = count;
                    max = sumB - sumA;
                    flag = true;                   
                } else if(max == sumB - sumA) {
                    if(minIdx > idx) {
                        minIdx = idx;
                        maxCnt = count;
                        flag = true;
                    } else if(minIdx == idx && maxCnt < count) {
                        maxCnt = count;
                        flag = true;
                    }
                }
            }
            
            if(flag) {
                for(int i = 0; i <= 10; i++) {
                    answer[i] = tmp[i];
                }
            }
            return;
        }
        
        for(int i = start; i <= 10; i++) {
            if(i != 10) {
                if(cnt < info[i] + 1) continue;
                tmp[i] = info[i] + 1;
                recur(cnt - tmp[i], i + 1, info);
                tmp[i] = 0; 
            } else {
                tmp[i] = cnt;
                recur(0, i + 1, info);
                tmp[i] = 0;
            }
        }
    }
}
