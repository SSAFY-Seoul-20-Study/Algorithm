import java.util.*;

/*
이분탐색

다 풀고 나서 뭔가 찜찜해서 인터넷 검색해보니까 정석 풀이는 우선순위 큐를 이용한 bfs 탐색이 정석이더라고요..

전 이분탐색이 보이면 써버리는 병에 걸려서 이분탐색으로 풀었습니다. (테케 중 일부는 3초 정도에 통과됨. 효율적이지는 않습니다.)

풀이과정:

1. 이분 탐색으로 1~K 사이의 연결 만으로 시작점에서 끝점까지 이동 가능한 최솟값 K를 찾는다.

2. K를 구했으면, 1~K 사이의 연결 만으로 시작점에서 끝점까지 이동 가능한 경우 중, 1. intensity가 가장 작은 경우거나 2. intensity가 같으면 끝점의 번호가 가장 경우를 구한다.
*/
class Solution {
    List<int[]>[] g;
    boolean[] starts;
    boolean[] ends;
    int[] gates;
    int N;
    
    public int[] solution(int n, int[][] paths, int[] local_gates, int[] summits) {
        g = new List[n+1];
        for(int i=1;i<n+1;i++){
            g[i] = new ArrayList<>();
        }
        
        for(int[] path:paths){
            int x = path[0];
            int y = path[1];
            int d = path[2];
            g[x].add(new int[]{y, d});
            g[y].add(new int[]{x, d});
        }
        
        ends = new boolean[n+1];
        for(int p:summits){ends[p] = true;}
        starts = new boolean[n+1];
        for(int p:local_gates){starts[p] = true;}
        
        gates = local_gates;
        N = n;
        
        int start = 1;
        int end = 10000000;
        int K=-1;
        
        while(start<=end){
            
            if(end-start==1){
                if(check(start)){K = start;}
                else{K = end;}
                break;    
            }
            
            int mid = (start+end)/2;
            boolean poss = check(mid);
            
            if(poss==true){end = mid;}
            else{start = mid;}
        }
        
        return getAns(K);
    }
    
    boolean check(int W){
        for(int sp: gates){
            boolean[] v = new boolean[N+1];
            Queue<Integer> q = new ArrayDeque<>();
            
            q.add(sp);
            v[sp] = true;
            
            while(q.isEmpty()==false){
                int now = q.poll();
                for(int[] data: g[now]){
                    int next = data[0];
                    int weight = data[1];
                    
                    if(v[next] || weight>W || starts[next]){
                        continue;
                    }
                    if(ends[next]==true){
                        return true;
                    }
                    
                    v[next] = true;
                    q.add(next);
                }
            }
        }
        
        return false;
    }
    
    int[] getAns(int W){
        int[] ret = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        
        for(int sp: gates){
            boolean[] v = new boolean[N+1];
            Queue<int[]> q = new ArrayDeque<>();
            
            q.add(new int[]{sp, 0});
            v[sp] = true;
            
            while(q.isEmpty()==false){
                int[] data1 = q.poll();
                int now = data1[0];
                int currw = data1[1];
                for(int[] data2: g[now]){
                    int next = data2[0];
                    int weight = data2[1];
                    
                    if(v[next] || weight>W || starts[next]){
                        continue;
                    }
                    
                    if(currw < weight){
                        currw = weight;
                    }
                    
                    if(currw > ret[1]){
                        continue;
                    }
                    
                    if(ends[next]==true){
                        if(currw < ret[1]){
                            ret = new int[]{next, currw};
                        }
                        else if(currw==ret[1] && next < ret[0]){
                            ret = new int[]{next, currw};
                        }
                        continue;
                    }
                    
                    v[next] = true;
                    q.add(new int[]{next, currw});
                }
            }
        }
        
        return ret;
    }
    
    
}