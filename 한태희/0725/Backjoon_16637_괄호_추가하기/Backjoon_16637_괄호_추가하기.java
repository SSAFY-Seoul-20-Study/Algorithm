package algostudy;
import java.util.*;
import java.io.*;

public class Backjoon_16637_괄호_추가하기
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
	
		int N = Integer.parseInt(br.readLine());

		String formula = "+" + br.readLine();

		System.out.println(findMaxResult(formula, 0, 0));

	}

	static int findMaxResult(String formula, int cumul, int p){
		// + 2 - 3 * 6
		//operator1(+) operand1(2) operator2(-) operand2(3) operand3(2-3 = -1)
		if(p == formula.length())
			return cumul;

		char operator1 = formula.charAt(p);
		int operand1 = formula.charAt(p+1) - '0';

		int case1 = doMath(cumul, operator1, operand1);

		case1 = findMaxResult(formula, case1, p+2);

		if(p == formula.length() -2)
			return case1;
		
		char operator2 = formula.charAt(p+2);
		int operand2 = formula.charAt(p+3) - '0';

		int operand3 = doMath(operand1, operator2, operand2);

		int case2 =doMath(cumul, operator1, operand3);
		case2 = findMaxResult(formula, case2, p+4);


		if(case1 > case2)
			return case1;
		else
			return case2;
	}

	static int doMath(int a, char operator, int b){
		if(operator=='+')
			return  a+b;
		else if(operator=='-')
			return a-b;
		else if (operator=='*')
			return a*b;

		return -1;
	}
}