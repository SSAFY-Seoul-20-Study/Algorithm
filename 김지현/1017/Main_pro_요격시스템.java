import java.util.*;
public class Main_pro_요격시스템 {
    public int solution(int[][] targets) {
        int answer = -1;

        Arrays.sort(targets, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2){
                if(o1[1] == o2[1]) return o1[0]-o2[0];
                else return o1[1]-o2[1];
            }
        });

        int tmp = -1;
        for(int[] t : targets){
            if(tmp > t[0]) continue;
            else{
                tmp = t[1];
                answer++;
            }
        }
        if(targets[targets.length-1][0] < tmp) answer++;

        return answer;
    }
}

/*
입력 targets
[[4,5],[4,8],[10,14],[11,13],[5,12],[3,7],[1,4]]
result 3
 */