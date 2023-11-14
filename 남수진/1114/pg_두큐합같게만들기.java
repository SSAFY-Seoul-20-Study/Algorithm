import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;
        
        ArrayDeque<Integer> q1 = new ArrayDeque<>();
        ArrayDeque<Integer> q2 = new ArrayDeque<>();
        
        long sum1 = 0, sum2 = 0;
        for(int i = 0; i< queue1.length; i++) {
            sum1 += queue1[i];
            q1.offer(queue1[i]);
        }
        
        for(int i = 0; i < queue2.length; i++) {
            sum2 += queue2[i];
            q2.offer(queue2[i]);
        }
        
        
        if((sum1 + sum2) % 2 != 0) return -1;
        
        while(true) {
            if(sum1 == sum2) return answer;
            if(answer > queue1.length + queue2.length) return -1;
            
            if(sum1 < sum2) {
                int tmp = q2.poll();
                sum1 += tmp;
                sum2 -= tmp;
                q1.offer(tmp);
            } else {
                int tmp = q1.poll();
                sum1 -= tmp;
                sum2 += tmp;
                q2.offer(tmp);
            }
            answer++;
        }
        // return answer;
    }
}
