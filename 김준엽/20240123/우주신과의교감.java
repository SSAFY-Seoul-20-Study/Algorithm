import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static int N, M;
    static ArrayList<int []> data = new ArrayList<>();
    static ArrayList<double []> routes = new ArrayList<>();
    static int[] parents;
    static int count;
    static double ans;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        data.add(new int [] {-1,-1});
        parents= new int[N+1];
        count = M;
        for (int i = 1; i < N+1; i++) {
            parents[i] = i;
        }
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            data.add(new int[] {x,y});
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            union(from, to);
        }
        for (int i = 1; i < N+1; i++) {
            for (int j = i+1; j < N+1; j++) {
                int []from = data.get(i);
                int [] to = data.get(j);
                double fromX = from[0], fromY = from[1];
                double toX = to[0], toY = to[1];
                double dist = Math.sqrt(Math.pow(Math.abs(fromX - toX), 2) + Math.pow(Math.abs(fromY-toY), 2));
                routes.add(new double [] {i,j,dist});
            }
        }
        Collections.sort(routes, (o1,o2) -> Double.compare(o1[2], o2[2]));
        //kruskal
        for(double route[] : routes){
            int from = (int) route[0];
            int to = (int) route[1];
            //부모가 같지 않으므로 union
            if(find(from) != find(to)){
//                System.out.println(from + " " + to);
                union(from ,to);
                ans += route[2];
                count++;
//                System.out.println(Arrays.toString(parents));
            }
        }

//        System.out.println(count);
        System.out.printf("%.2f", ans);

//        System.out.println(Arrays.toString(parents));
//        for (int i = 0; i < routes.size(); i++) {
//            System.out.println(Arrays.toString(routes.get(i)));
//        }
    }
    static int find(int n){
        if(parents[n] == n) return n;
        else return parents[n] = find(parents[n]);
    }
    static void union(int from, int to){
        int x = find(from);
        int y = find(to);
        if(x != y){
            parents[y] = x;
        }
    }
}