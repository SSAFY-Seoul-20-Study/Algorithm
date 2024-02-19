import java.util.*;
import java.io.*;

public class Main_bj_g3_14658_하늘에서_별똥별이_빗발친다 {

	static int N, M, L, K;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		int[][] cords = new int[K][];
		Set<Integer> xSet = new HashSet<>();
		Set<Integer> ySet = new HashSet<>();
		for(int k=0;k<K;k++) {
			st = new StringTokenizer(br.readLine());
			cords[k] = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
			xSet.add(cords[k][0]);
			ySet.add(cords[k][1]);
		}

		int answer = Integer.MAX_VALUE;

		for(int x: xSet){
			for(int y: ySet){
				int leftStars = K;
				for(int k=0;k<K;k++){
					if(x <= cords[k][0] && cords[k][0] <= x + L && y <= cords[k][1] && cords[k][1] <= y + L){
						leftStars--;
					}
				}
				answer = Math.min(answer, leftStars);
			}
		}

		System.out.println(answer);

		br.close();
	}

}