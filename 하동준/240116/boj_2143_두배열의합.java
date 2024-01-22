import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int T;
    static int n,m;
    static int[] a,b;
    static ArrayList<Integer> subA = new ArrayList<>();
    static ArrayList<Integer> subB = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        n = Integer.parseInt(br.readLine());
        a = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < n; i++) {
            int temp = 0;
            for (int j = i; j < n; j++) {
                temp += a[j];
                subA.add(temp);
            }
        }
        Collections.sort(subA, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        m = Integer.parseInt(br.readLine());
        b = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < m; i++) {
            int temp = 0;
            for (int j = i; j < m; j++) {
                temp += b[j];
                subB.add(temp);
            }
        }
        Collections.sort(subB, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

//        System.out.println(subA);
        int res = 0;
        for (int a:subA
             ) {
            if (a >= T) {
                break;
            }
            int remain = T-a;
            int start = 0;
            int end = subB.size()-1;
            while (start<=end) {
                int mid = (start+end)/2;
                if (subB.get(mid) < remain) {
                    start = mid + 1;
                } else if (subB.get(mid) > remain) {
                    end = mid;
                } else {
                    while (mid>=0 && subB.get(mid)==remain) {
                        res++;
                        mid--;
                    }
                    break;
                }
            }
        }
        System.out.println(res);


    }
}
