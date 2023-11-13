import java.util.*;
class Solution {
    static int answer;
    static ArrayDeque<Integer> q1 = new ArrayDeque<>();
    static ArrayDeque<Integer> q2 = new ArrayDeque<>();
    static long sum1, sum2;
    public int solution(int[] queue1, int[] queue2) {
        for(int i=0; i<queue1.length; i++){
            q1.add(queue1[i]);
            q2.add(queue2[i]);
            sum1 += queue1[i];
            sum2 += queue2[i];
        }
        while(!q1.isEmpty() && !q2.isEmpty()){
            if(sum1 == sum2) return answer;
            
            if(sum1 > sum2){
                int n1 = q1.poll();
                sum1 -= n1;
                sum2 += n1;
                q2.add(n1);
            }
            else{
                int n2 = q2.poll();
                sum1 += n2;
                sum2 -= n2;
                q1.add(n2);
            }
            answer++;
            if(answer >= 300000) break;
        }
        
        return -1;
    }
}