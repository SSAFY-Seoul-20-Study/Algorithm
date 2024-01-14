import java.io.*;
import java.util.*;

public class Main {
    static long T,count;
    static int N;
    static int [] a,b;
    static ArrayList<Long> aSum = new ArrayList<>();
    static ArrayList<Long> bSum = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        T = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        a = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
        //aSum
        for (int i = 0; i < N; i++) {
            long tmp = 0;
            for (int j = i; j < N; j++) {
                tmp += a[j];
                aSum.add(tmp);
            }
        }


        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        b = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }
        //bSum
        for (int i = 0; i < N; i++) {
            long tmp = 0;
            for (int j = i; j < N; j++) {
                tmp += b[j];
                bSum.add(tmp);
            }
        }

        Collections.sort(aSum);
        Collections.sort(bSum);
        int aIndex = 0;
        int bIndex = bSum.size() - 1;
        while(true){
            long ans = aSum.get(aIndex) + bSum.get(bIndex);
            if(ans < T) aIndex++;
            else if(ans > T) bIndex--;
            else{
                long aNum = aSum.get(aIndex);
                long bNum = bSum.get(bIndex);
                long aCnt = 0, bCnt = 0;
                while(aNum == aSum.get(aIndex)){
                    aIndex++;
                    aCnt++;
                    if(aIndex == aSum.size()) break;
                }
                while(bNum == bSum.get(bIndex)){
                    bIndex--;
                    bCnt++;
                    if(bIndex == -1) break;
                }
                count += aCnt * bCnt;
            }
            if(aIndex == aSum.size() || bIndex == -1) break;
        }
        System.out.println(count);
    }
}