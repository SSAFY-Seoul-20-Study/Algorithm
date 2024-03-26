import java.util.*;
import java.io.*;


public class Main_bj_g1_1300_k번째_수 {
    public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int K = Integer.parseInt(br.readLine());

		int left = 1;
		int right = K;

		while(left <= right){
			int mid = (left + right) / 2;
			int cnt = 0;
			for(int i=1;i<=N;i++){
				cnt += Math.min(mid/i, N);
			}
			if(cnt < K){
				left = mid + 1;
			}else{
				right = mid - 1;
			}
		}
		System.out.println(left);
		
	}

}