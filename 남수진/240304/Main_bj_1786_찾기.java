import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    static int[] table;
    static int cnt;
    static StringBuilder sb;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String T = br.readLine();
        String P = br.readLine();

        table = new int[P.length()];

        make(P);
        KMP(T, P);



        System.out.println(cnt);
        System.out.println(sb.toString());
    }

    static void KMP(String T, String P){

        int tLen = T.length();
        int pLen = P.length();
        int idx = 0;
        sb = new StringBuilder();

        for(int i=0; i< tLen; i++) {
            while(idx>0 && T.charAt(i) != P.charAt(idx)) {
                idx = table[idx-1];
            }

            if(T.charAt(i) == P.charAt(idx)) {
                if(idx == pLen-1) {
                    sb.append((i-idx+1)+" ");
                    cnt++;
                    idx = table[idx];
                }else {
                    idx +=1;
                }
            }
        }

    }

    static void make(String P) {
        int idx = 0;
        for(int i=1; i<P.length(); i++) {
            while(idx>0 && P.charAt(i) != P.charAt(idx)) {
                idx = table[idx-1];
            }
            if(P.charAt(i) == P.charAt(idx)) {
                table[i] = ++idx;
            }
        }
    }
}
