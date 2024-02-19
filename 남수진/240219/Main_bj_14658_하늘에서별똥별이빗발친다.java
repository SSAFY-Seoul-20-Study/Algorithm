import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_bj_14658_하늘에서별똥별이빗발친다 {

    static int N, M, L, K;
    static int[][] star;
    static int ans = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        star = new int[K][2];

        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());

            star[i][0] = Integer.parseInt(st.nextToken());
            star[i][1] = Integer.parseInt(st.nextToken());

        }

        for(int i = 0; i < K; i++){
            for(int j = 0; j < K; j++) {
                int cnt = 0;

                for(int t = 0; t < K; t++) {
                    if(star[i][0] <= star[t][0] && star[t][0] <= star[i][0] + L
                        && star[j][1] <= star[t][1] && star[t][1] <= star[j][1] + L)
                        cnt++;
                }

                ans = Integer.max(ans, cnt);
            }
        }


        System.out.println(K - ans);
    }

}
