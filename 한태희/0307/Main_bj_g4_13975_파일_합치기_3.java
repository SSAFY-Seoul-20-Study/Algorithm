import java.util.*;
import java.io.*;

public class Main_bj_g4_13975_파일_합치기_3 {
    public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for(int tc = 1; tc <= T; tc++) {

			int K = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());

			PriorityQueue<Long> pq = new PriorityQueue<>();

			for(int k=0;k<K;k++) {
				pq.add(Long.parseLong(st.nextToken()));
			}

			Long answer = 0L;

			while(pq.size() > 1) {
				long sum = pq.poll() + pq.poll();
				answer += sum;
				pq.add(sum);
			}

			sb.append(answer).append("\n");
		}

		System.out.print(sb);

		br.close();
	}

	

}