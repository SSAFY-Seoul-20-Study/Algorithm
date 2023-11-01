import java.util.*;

/*
# 양과 늑대
완전탐색 + 가지치기

DP 같은 경우도 고민해 보았지만, 문제에서 주어진 상황대로 늑대가 양의 숫자를 넘으면 프루닝 하면서 완전 탐색을 하면 통과되었습니다. 

이때, BFS를 이용해 구현했는데, 주의해야할 점은 하나의 노드를 방문하고, 다음으로 방문 가능한 경우는 그 노드의 자식 노드가 아니라, 이전에 방문 가능했던 노드들 + 이번에 방문한 노드의 자식 노드들 입니다.

(다익스트라 알고리즘 처럼, 그래프 뭉텅이에서 탐색 가능한 다음 노드들을 모두 고려)

class Solution {
    
    int[][] g;
    int[] info;
    int N;
    int ans = 0;
    
    class Info{
        int[] v; //0방문 안함 1:다음 목적지 2:방문함
        int curr;
        int point;
    }
    
    public int solution(int[] local_info, int[][] edges) {
        info = local_info;
        N = info.length;
        
        g = new int[N][N];
        for(int[] edge:edges){
            g[edge[0]][edge[1]] = 1;
        }
        
        Queue<Info> q = new ArrayDeque<>();
        Info startinfo = new Info();
        startinfo.v = new int[N];
        startinfo.v[0] = 1;
        startinfo.curr = 0;
        startinfo.point = 0;
        q.add(startinfo);
        
        //int cnt = 100;
    
        while(q.isEmpty()==false){
            Info now = q.poll();
            //cnt--;
            //if(cnt==0) break;
            //System.out.println(Arrays.toString(now.v));
            //System.out.println(now.curr);
            //System.out.println(now.point);
            for(int i=0;i<N;i++){
                if(now.v[i]==1){ //다음 방문 예정지라면
                    Info next = new Info();
                    next.v = deepcopy(now.v);
                    next.v[i] = 2;
                    for(int j=0;j<N;j++){
                        if(g[i][j]==1){
                            next.v[j] = 1;
                        }
                    }
                    
                    if(info[i]==0){
                        next.curr = now.curr +1;
                        next.point = now.point +1;
                    }
                    else{
                        next.curr = now.curr -1;
                        next.point = now.point;
                    }
                    
                    if(ans < next.point){
                        ans = next.point;
                    }
                    if(next.curr >0){
                        q.add(next);
                    }
                }
            }
        }
        
        return ans;
    }
    
    int[] deepcopy(int[] orig){
        int[] ret = new int[orig.length];
        for(int i=0;i<orig.length;i++){
            ret[i] = orig[i];
        }
        return ret;
    }
}