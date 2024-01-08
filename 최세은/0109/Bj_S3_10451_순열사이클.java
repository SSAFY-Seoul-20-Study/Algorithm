import java.io.*;
import java.util.*;

public class Bj_S3_10451_순열사이클 {
    static int N;
    static int[] parents;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for(int tc=0;tc<T;tc++){
            N = Integer.parseInt(br.readLine());

            parents = new int[N+1];
            make();

            st = new StringTokenizer(br.readLine());
            for(int i=1;i<=N;i++){
                int num = Integer.parseInt(st.nextToken());
                union(i,num);
            }
            Set<Integer> cycle = new HashSet<>();
            for(int i=1;i<=N;i++){
                cycle.add(find(i));
            }
            sb.append(cycle.size()).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
    static void make(){
        for(int i=1;i<=N;i++){
            parents[i] = i;
        }
    }
    static int find(int i){
        if(parents[i]==i) return i;
        return find(parents[i]);
    }
    static void union(int a,int b){
        int a_parent = find(a);
        int b_parent = find(b);

        if(a_parent<b_parent) parents[b_parent] = a_parent;
        else parents[a_parent] = b_parent;
    }
}
