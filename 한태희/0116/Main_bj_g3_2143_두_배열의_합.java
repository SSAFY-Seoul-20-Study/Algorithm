import java.util.*;
import java.io.*;

public class Main_bj_g3_2143_두_배열의_합 {
	static long T;
	static int n, m;
	static long[] A, B;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Long.parseLong(br.readLine());
		
		n = Integer.parseInt(br.readLine());
		A = new long[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<n;i++){
		    A[i] = Long.parseLong(st.nextToken());
		}
		
		m = Integer.parseInt(br.readLine());
		B = new long[m];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<m;i++){
		    B[i] = Long.parseLong(st.nextToken());
		}
		
		HashMap<Long, Long> AMap = new HashMap<>();
		HashMap<Long, Long> BMap = new HashMap<>();
		
		for(int i=0;i<n;i++){
		    long sum = 0;
		    for(int j=i;j<n;j++){
		        sum += A[j];
		        AMap.put(sum, AMap.getOrDefault(sum, (long)0)+1);
		    }
		}

		for(int i=0;i<m;i++){
		    long sum = 0;
		    for(int j=i;j<m;j++){
		        sum += B[j];
		        BMap.put(sum, BMap.getOrDefault(sum, (long)0)+1);
		    }
		}

		long ans = 0;

		for(long key : AMap.keySet()){
		    if(BMap.containsKey(T-key)){
		        ans += AMap.get(key) * BMap.get(T-key);
		    }
		}

		System.out.println(ans);

		br.close();
	}

}