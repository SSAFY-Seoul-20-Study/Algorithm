import java.util.*;
import java.io.*;

class Solution {
    
    Queue<Integer> q1;
    Queue<Integer> q2;
    long sum, sum_q1;
    int answer;
    boolean flag;
    
    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;
        int total_length = queue1.length + queue2.length;
        // System.out.println("total length: "+total_length);
        long sum_q2=0;
        
        q1 = new ArrayDeque<>();
        q2 = new ArrayDeque<>();
        
        for(int i=0; i<queue1.length; i++){
            q1.offer(queue1[i]);
            sum_q1 += queue1[i];
        }
        for(int i=0; i<queue2.length; i++){
            q2.offer(queue2[i]);
            sum_q2 += queue2[i];
        }
        sum = sum_q1 + sum_q2;
        if(sum % 2 == 1) return -1; // 총 합이 홀수라면 종료
        
        while(answer <= total_length*2 && !q1.isEmpty() && !q2.isEmpty()){
            if(sum_q1 == sum/2){
                return answer;
            } else if (sum_q1 > sum/2){
                int temp = q1.poll();
                q2.offer(temp);
                sum_q1 -= temp;
                sum_q2 += temp;
                answer++;
            } else {
                int temp = q2.poll();
                q1.offer(temp);
                sum_q1 += temp;
                sum_q2 -= temp;
                answer++;
            }
        }
    
        if(answer > total_length || sum_q1 != sum/2) return -1;

        return answer;
    }
}
