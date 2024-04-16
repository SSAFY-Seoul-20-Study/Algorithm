import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int d = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] arr = new int[n + 2];

        arr[0] = 0;
        arr[n + 1] = d;
        
        for(int i = 1; i < n + 1; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        int start = 0;
        int end = d;
        int ans = d;
        
        Arrays.sort(arr);

        while(start <= end) {
            int mid = (start + end) / 2; // 최소거리

            int sum = 0;
            int now = 0;

            for(int i = 1; i < n + 2; i++) {
                if(arr[i] - arr[now] < mid) sum++;
                else now = i;
            }
            if(sum <= m) {
                ans = mid;
                start = mid + 1;
            }
            else {
                end = mid - 1;
            }
        }

        System.out.println(ans);

    }

}
