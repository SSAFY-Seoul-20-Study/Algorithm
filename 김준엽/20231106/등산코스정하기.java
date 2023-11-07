import java.util.*;
class Solution {
    static ArrayList<Integer> answer = new ArrayList<>();
    static ArrayList<int []> graph[];
    static Set<Integer> summitSet = new HashSet<>();
    static int N;
    static int []result;
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        graph = new ArrayList[n+1];
        N = n;
        for(int i =0; i<=n; i++)
            graph[i] = new ArrayList<>();
        for(int[]path:paths){
            int from = path[0];
            int to = path[1];
            int w = path[2];
            graph[from].add(new int [] {to, w});
            graph[to].add(new int [] {from, w});
        }
        Arrays.sort(summits);
        for(int i=0; i<summits.length; i++) summitSet.add(summits[i]);
        dijkstra(summits,gates);
        return result;
    }
    public static void dijkstra(int[] summits, int []gates){
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int []distance = new int[N+1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        for(int gate : gates){
            distance[gate] = 0;
            pq.add(new int [] {0, gate});
        }
        while(!pq.isEmpty()){
            int cur[] = pq.poll();
            int cost = cur[0];
            int idx = cur[1];
            if(summitSet.contains(idx) || cost > distance[idx]) continue;
            for(int next[] : graph[idx]){
                int new_cost = Math.max(cost, next[1]);
                if(new_cost < distance[next[0]]){
                    distance[next[0]] = new_cost;
                    pq.add(new int [] {new_cost, next[0]});
                }
            }
        }
        int minDistance = Integer.MAX_VALUE;
        int selectedSummit = -1;
        for(int s : summits){
            if(distance[s] < minDistance){
                minDistance = distance[s];
                selectedSummit = s;
            }
        }
        result = new int [] {selectedSummit,minDistance};
    }
}