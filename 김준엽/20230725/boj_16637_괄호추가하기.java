import java.util.*;
import java.io.*;
//기호의 순서는 바뀌면 안된다
//최대가 음수일수도 있다는 점
class Main{
    static int N;
    static String [] sign;
    static int [] num;
    static boolean [] check;
    static int answer = Integer.MIN_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        String data = br.readLine();
//        StringTokenizer st  = new StringTokenizer(br.readLine(), " ");
        sign = new String[N/2];
        num = new int[N/2 + 1];
        check = new boolean[N/2];
        int indexS = 0;
        int indexN = 0;
        for(int i = 0; i <N; i++){
            String tmp = Character.toString(data.charAt(i));
            if(tmp.equals("+") || tmp.equals("-") || tmp.equals("*")){
                sign[indexS++] = tmp;
            }
            else num[indexN++] = Integer.parseInt(tmp);
        }
        backtracking(0,num[0]);
        System.out.println(answer);
        br.close();
    }
    public static void backtracking(int depth, int result){
        if(depth >= N/2){
            answer = Math.max(answer, result);
            return;
        }
        int res1 =Cal(result, num[depth+1], sign[depth]);
        backtracking(depth + 1, res1);
        if(depth + 1 < N/2){
            int res2 = Cal(num[depth+1],num[depth+2],sign[depth+1] );
            backtracking(depth+2, Cal(result, res2,sign[depth]));
        }
    }
    public static int Cal(int a, int b, String flag){
        if(flag.equals("+")) return a+b;
        else if(flag.equals("-")) return a-b;
        else return a*b;
    }
}