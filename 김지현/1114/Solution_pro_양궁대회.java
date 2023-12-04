import java.util.*;
import java.io.*;

class Solution {
    
    int[] uppeach;
    int N, gap=Integer.MIN_VALUE;
    int[] answer;
    
    public int[] solution(int n, int[] info) {
        answer = new int[11];
        uppeach = info;
        N = n;        
        
        dfs(0, 0, new int[11]);
        
        if(gap == Integer.MIN_VALUE) return new int[]{-1};
        else return answer;
    }
    
    public void cal(int[] lion){        
        int sum_lion=0, sum_up=0;
        for(int i=0; i<11; i++){
            if(uppeach[i] > 0 && uppeach[i] >= lion[i]){
                sum_up += (10-i);
            } else if (lion[i] > 0) {
                sum_lion += (10-i);
            }
        }
        
        if(sum_lion > sum_up && (sum_lion-sum_up) >= gap){
            if((sum_lion - sum_up) == gap){
                for(int i=10; i>=0; i--){
                    if(lion[i] > 0 && answer[i] < lion[i]){
                        // lion으로 갱신
                        for(int j=0; j<11; j++){
                            answer[j] = lion[j];
                        }
                    } else if(answer[i] > 0 && answer[i] > lion[i]){
                        return;
                    }
                }
            } else {
                // lion으로 갱신
                gap = sum_lion-sum_up;
                for(int j=0; j<11; j++){
                    answer[j] = lion[j];
                }
            }
        }
    }
    
    public void dfs(int idx, int sum, int[] lion){
        if(idx == 11 || sum == N){
            int tmp = 0;
            for(int i=0; i<11; i++){
                tmp += lion[i];
            }
            if(tmp < N){
                lion[10] = (N-tmp);
            } else if(tmp > N){
                lion[10] = 0;
            }
            cal(lion);
            return;
        }
        int up = uppeach[idx];
        if(sum+(up+1) <= N){
            lion[idx] = up+1;
            dfs(idx+1, sum+=(up+1), lion); // 해당 점수 먹기
            lion[idx] = 0;
            sum -= (up+1);
        }
        dfs(idx+1, sum, lion); // 해당 점수 먹지 않기
    }
}
