import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int d,n,m;
    static List<Integer> array;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        d = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        array = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            array.add(Integer.parseInt(br.readLine()));
        }
        array.add(0);
        array.add(d);
        Collections.sort(array);

        int left = 0;
        int right = d;
        int result = 0;

        while (left<=right) {
            int mid = (left+right)/2;
            int now = 0;
            int sum = 0;
            for (int i = 1; i < array.size(); i++) {
                if (array.get(i) - array.get(now) < mid) {
                    // 생각한거보다 짧음. 얘는 없애도 돼
                    sum++;
                }
                else now=i;
            }
            if (sum > m) {
                // 너무 없앰. 조금 줄여
                right = mid - 1;
            } else {
                result = mid;
                left = mid + 1;
            }
        }

        System.out.println(result);
    }

}
