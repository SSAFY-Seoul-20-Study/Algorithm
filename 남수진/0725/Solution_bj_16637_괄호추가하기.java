import java.io.*;
import java.util.*;

public class Solution_bj_16637_괄호추가하기{

	static int max = Integer.MIN_VALUE;
	static int N;
	static String str;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		str = br.readLine();
		recursion(0, str.charAt(0) - '0');
		
		System.out.println(max);
		
	}

	public static void recursion(int index, int num) {
		
		if(index >= N - 1) {
			max = Math.max(max, num);
			return;
		}

		// 괄호가 없는 경우
		
		int calc1 = calc(num, str.charAt(index + 2) - '0', str.charAt(index+1));
		recursion(index + 2, calc1);
		
    //괄호가 있는 경우
		if(index + 4 < N) {
			int calc2 = calc(str.charAt(index + 2) - '0', str.charAt(index + 4) - '0', str.charAt(index+3));

			recursion(index+4, calc(num, calc2, str.charAt(index + 1)));
		}
		
	}
	
	public static int calc(int a, int b, char c) {
		switch (c){
		case '+':
			return a+b;
		case '-':
			return a-b;
		case '*':
			return a * b;
		}
		return 0;
	}
}
