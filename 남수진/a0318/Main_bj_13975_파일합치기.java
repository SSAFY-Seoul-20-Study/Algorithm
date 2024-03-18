package algo_study;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_13975_파일합치기 {

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        for(int tc = 1; tc <= T; tc++){

            PriorityQueue<Long> pq = new PriorityQueue<Long>();

            int K = Integer.parseInt(br.readLine());

            StringTokenizer st = new StringTokenizer(br.readLine());

            for(int i = 0; i < K; i++) {
                pq.offer(Long.parseLong(st.nextToken()));
            }

            Long sum = 0L;

            while(pq.size() > 1) {
                Long a= pq.poll();
                Long b= pq.poll();
                sum += a + b;
                pq.offer(a + b);
            }

            sb.append(sum).append("\n");
        }

        System.out.println(sb.toString());
    }
}
