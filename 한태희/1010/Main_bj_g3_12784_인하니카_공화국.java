import java.util.*;
import java.io.*;

public class Main_bj_g3_12784_인하니카_공화국 {
	static int N, M;
	static boolean[] v;
	static List<int[]>[] neisArr;

	final static int INF = Integer.MAX_VALUE;
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			neisArr = new List[N+1];
			for(int i=1;i<=N;i++){
				neisArr[i] = new ArrayList<>();
			}

			for(int i=0;i<M;i++){
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				neisArr[x].add(new int[]{y, d});
				neisArr[y].add(new int[]{x, d});
			}

			v = new boolean[N+1];
			v[1] = true;

			int ans = search(1, INF);
			if(ans==INF){
				ans = 0;
			}
			
			sb.append(ans).append("\n");
		}

		System.out.print(sb);

		br.close();
	}

	static int search(int parent, int k){
		int l = 0;
		for(int[] info: neisArr[parent]){
			int child = info[0];
			if(v[child]) {continue;}
			int d = info[1];

			v[child] = true;
			l += search(child, d);
		}
		if(l==0){
			l = INF;
		}
		return Integer.min(k, l);
	}

}