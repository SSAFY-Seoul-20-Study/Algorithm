import java.util.*;
class Solution {
    
    static final int INF = Integer.MAX_VALUE;
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        int[] answer = {};
        
        List<int[]>[] graph = new List[n + 1];
        
        for(int i = 0; i<= n; i++) {
            graph[i] = new LinkedList<>();
        }
        
        // 양방향 그래프
        for(int[] p : paths) {
            graph[p[0]].add(new int[] {p[1], p[2]});
            graph[p[1]].add(new int[] {p[0], p[2]});
        }
        
        // intensity가 작은 순서대로 조사
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
        
        int[] intensity = new int[n + 1];
        
        for(int i = 0; i <= n; i++) {
            intensity[i] = INF;
        }
        // 모든 입구를 pq에 삽입
        for(int g : gates) {
            pq.offer(new int[] {g, 0, 0});
            intensity[g] = 0;
        }

        // 산봉우리의 번호, intensity의 최솟값 반환
        int minSummit = n;
        int minValue = INF;
        boolean v = false;
        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            
            // intensity 값이 크면 break
            if (minValue < cur[1]) break;
            
            // 산봉우리를 발견하면 값 초기화
            for(int s: summits) {
                if(cur[2] >= 1) break; // 이미 산봉우리를 지났으면 break;
                if(s == cur[0]) {
                    minValue = cur[1]; 
                    minSummit = Math.min(minSummit, cur[0]);
                    answer = new int[] {minSummit, minValue};
                    cur[2] += 1;
                    break;
                }
            }
            
            for(int[] g : graph[cur[0]]) {
                int tmp = Math.max(cur[1], g[1]); // 현재 intensity와 다음 intensity 비교
                if(intensity[g[0]] > tmp) { // 저장된 값보다 작으면 update
                    intensity[g[0]] =tmp;
                    pq.offer(new int[] {g[0], tmp, cur[2]});
                }
            }
        }
        
        return answer;
    }
}
