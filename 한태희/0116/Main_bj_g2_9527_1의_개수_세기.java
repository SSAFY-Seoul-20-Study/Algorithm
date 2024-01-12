import java.util.*;
import java.io.*;
import java.math.*;

public class Main_bj_g2_9527_1의_개수_세기 {
	static BigInteger two_pow[];

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		BigInteger A = new BigInteger(st.nextToken());
		BigInteger B = new BigInteger(st.nextToken());

		two_pow = new BigInteger[100];
		two_pow[0] = new BigInteger("1");
		for(int i=1;i<100;i++) {
			two_pow[i] = two_pow[i-1].multiply(new BigInteger("2"));
		}
		
		BigInteger fx_b = fx(B);
		BigInteger fx_a = fx(A.subtract(BigInteger.ONE));

		System.out.println(fx_b.subtract(fx_a));

		br.close();
	}

	public static BigInteger fx(BigInteger num) {
		num = num.add(BigInteger.ONE);

		int bitLength = 0;
		BigInteger temp = new BigInteger(num.toString());
		while (temp.compareTo(BigInteger.ZERO) > 0) {
			bitLength++;
			temp = temp.divide(BigInteger.valueOf(2));
		}
		
		BigInteger ret = new BigInteger("0");
		for(int i=1;i<=bitLength;i++) {
			BigInteger k = two_pow[i];
			BigInteger kp = two_pow[i-1];
			BigInteger a =	num.divide(k);
			BigInteger b = num.mod(k);
			ret = ret.add(a.multiply(kp));
			if(b.subtract(kp).compareTo(BigInteger.ZERO) > 0){
				ret = ret.add(b.subtract(kp));
			}
		}

		return ret;
	}

}