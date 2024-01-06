import java.util.*;
import java.io.*;

// 55268KB	560ms
public class Main_boj_10451_순열사이클 {

    static int N;
    static int[] arr, parents;

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("res/input_boj_10451.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for(int t=0; t<T; t++){
            N = Integer.parseInt(br.readLine());
            arr = new int[N+1];
            st = new StringTokenizer(br.readLine());
            for(int i=1; i<=N; i++){
                arr[i] = Integer.parseInt(st.nextToken());
            }
            // parents 초기화
            parents = new int[N+1];
            for(int i=1; i<=N; i++){
                parents[i] = i;
            }
            System.out.println(find_cycle());
        }
    }

    private static int find_cycle(){
        for(int i=1; i<=N; i++){
            union(i, arr[i]);
        }

        int cycle_count = 0;
        for(int i=1; i<=N; i++){
            if(i == parents[i]) cycle_count++;
        }
//        System.out.println(Arrays.toString(parents));
        return cycle_count;
    }

    private static void union(int a, int b){
        a = find(a);
        b = find(b);
        if(a > b) parents[a] = b;
        else parents[b] = a;
    }

    private static int find(int x){
        if(parents[x] == x) return parents[x];
        return parents[x]=find(parents[x]);
    }
}
