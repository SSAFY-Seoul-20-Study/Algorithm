import java.io.*;
import java.util.*;

public class Main {
    static Queue<Long> answer = new LinkedList<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        while(T-->0){
            int K = Integer.parseInt(br.readLine());

            StringTokenizer st = new StringTokenizer(br.readLine());

            Queue<Long> q = new PriorityQueue<>();
            for(int i = 0; i<K; i++)
                q.offer(Long.parseLong(st.nextToken()));

            fileCombine(q);
        }

        while(!answer.isEmpty())
            System.out.println(answer.poll());
    }

    static void fileCombine(Queue<Long> q){
        long sum = 0;

        while(q.size() > 1){
            long firstNum = q.poll();
            long secondNum = q.poll();

            sum += firstNum + secondNum;
            q.offer(firstNum + secondNum);
        }

        answer.offer(sum);
    }
}
