import java.util.*;
class Solution {
    static int num[] = new int[11];
    static int gInfo[];
    static int answer[];
    static int max = Integer.MIN_VALUE;;
    public int[] solution(int n, int[] info) {
        answer = new int[11];
        comb(0, n, info);
        if(max == -1) return new int[] {-1};
        return answer;
    }
    
    static void comb(int depth, int n, int info[]){
        if(depth == n){
            int flag = calScore(info);
            if(max <= flag){
                answer = num.clone();
                max = flag;
            }
            return;
        }
        for(int i=0; i<num.length && num[i] <= info[i]; i++){
            num[i] ++;
            comb(depth+1, n, info);
            num[i] --;
        }
    }
    static int calScore(int gInfo[]){
        int score1=0, score2=0;
        for(int i=0; i<num.length; i++){
            if(gInfo[i] == 0 && num[i] == 0) continue;
            if(gInfo[i] >= num[i]){
                score1 += (10-i);
            }
            else score2 += (10-i);
        }

        int diff = score2 - score1;
        if(diff<=0) return -1;
        return diff;
    }
   
}