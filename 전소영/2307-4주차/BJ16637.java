import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.IOException;

// 괄호 추가하기

public class BJ16637 {

    static int n;
    static int ans = Integer.MIN_VALUE;
    static ArrayList<Integer> numbers = new ArrayList<>();
    static ArrayList<Character> operators = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        String line = br.readLine();
        for(int i = 0; i < n; i++) {
            if(i % 2 == 0) {
                numbers.add(line.charAt(i) - '0');
            }
            else {
                operators.add(line.charAt(i));
            }
        }

        dfs(numbers.get(0), 0);
        System.out.println(ans);
    }

    private static void dfs(int result, int index) {

        if(index == numbers.size() - 1) {
            ans = Integer.max(ans, result);
            return;
        }

        // (a ? b) ? c 연산인 경우, (a ? b) 계산하기
        int calculated = calculate(result, numbers.get(index + 1), operators.get(index));
        dfs(calculated, index + 1);

        // a ? (b ? c) 연산인 경우, a ? (b ? c) 계산하기
        if(index + 2 < numbers.size()) {
            calculated = calculate(result, calculate(numbers.get(index + 1), numbers.get(index + 2), operators.get(index + 1)), operators.get(index));
            dfs(calculated, index + 2);
        }
    }

    private static int calculate(int a, int b, char op) {

        switch(op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
        }
        
        return 0;
    }

    
}
