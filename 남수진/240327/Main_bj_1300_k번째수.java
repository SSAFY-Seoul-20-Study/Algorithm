import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception{

        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());

        int start = 1;
        int end = k;

        int answer = 0;
        while(start <= end) {
            int mid = (start + end) / 2;
            int cnt = 0;

            for(int i = 1; i < N + 1; i++)
                cnt+= Math.min(mid / i, N);

            if(cnt >= k) {
                end = mid - 1;
                answer = mid;
            } else {
                start = mid + 1;
            }
        }

        System.out.println(answer);
    }
}
