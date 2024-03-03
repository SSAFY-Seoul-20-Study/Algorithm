import java.util.*;
import java.io.*;

public class Main_bj_g4_9252_LCS_2 {

	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String str1 = br.readLine();
		String str2 = br.readLine();
		int N = str1.length();
		int M = str2.length();
		
		short[][] arr = new short[N+1][M+1];
		for(int i=0; i<N+1; i++) {
			for(int j=0;j<M+1;j++) {
				if(i==0 || j==0) {
					arr[i][j] = 0;
					continue;
				}

				if(str1.charAt(i-1) == str2.charAt(j-1)) {
					arr[i][j] = (short) (arr[i-1][j-1] + 1);
				}
				else{
					arr[i][j] = arr[i-1][j] > arr[i][j-1] ? arr[i-1][j] : arr[i][j-1];
				}
			}
		}

		System.out.println(arr[N][M]);

		StringBuilder lcs = new StringBuilder();
		int i = N, j = M;
		while (i > 0 && j > 0) {
		    if (str1.charAt(i-1) == str2.charAt(j-1)) {
		        lcs.append(str1.charAt(i-1));
		        i--;
		        j--;
		    } else if (arr[i-1][j] > arr[i][j-1]) {
		        i--;
		    } else {
		        j--;
		    }
		}
		System.out.println(lcs.reverse().toString());
		br.close();
	}

}