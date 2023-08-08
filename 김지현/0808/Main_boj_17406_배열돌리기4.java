import java.util.*;
import java.io.*;

// 36872KB	296ms
public class Main_boj_17406_배열돌리기4 {
    static int N, M,K, res=Integer.MAX_VALUE;
    static int[][] arr;
    static int[][] copy;
    static int[][] ro; // 입력받은 회전 연산
    static int[] orders; // 회전 연산 순서
    static boolean[] visited; // 순열을 위함.

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("res/input_boj_17406.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr = new int[N][M];
        ro = new int[K][3];

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
//		for(int[] a:arr) System.out.println(Arrays.toString(a));

        // 회전 연산 입력받기
        for(int i=0; i<K; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<3; j++){
                ro[i][j] = Integer.parseInt(st.nextToken());
            }
        }
//        for(int[] a : ro) System.out.println(Arrays.toString(a));
        // 회전 연산 순서 순열 (K! : 최대 6!)
        orders = new int[K];
        visited = new boolean[K];
        dfs(0);

        // 회전 연산 수행
        // 행의 합 계산하기 -> 최솟값 res에 넣기

        System.out.println(res);
    }
    static void rotationArr(){
        // 배열 복사하기
        copy = new int[N][M];
        for(int i=0; i<N; i++){
            copy[i] = Arrays.copyOf(arr[i], M);
        }
//        System.out.println(Arrays.toString(orders));
        for(int k=0; k<K; k++){
            int r = ro[orders[k]][0] - 1;
            int c = ro[orders[k]][1] - 1;
            int S = ro[orders[k]][2];

            for(int s=1; s<=S; s++){
                // 오른쪽으로 이동
                int rightTemp = copy[r-s][c+s];
                for(int j = c+s; j>c-s; j--){
                    copy[r-s][j] = copy[r-s][j-1];
                }
                // 아래로 이동
                int downTemp = copy[r+s][c+s];
                for(int i=r+s; i>r-s; i--){
                    copy[i][c+s] = copy[i-1][c+s];
                }
                copy[r-s+1][c+s] = rightTemp;

                // 왼쪽으로 이동
                int leftTemp = copy[r+s][c-s];
                for(int j=c-s; j<c+s; j++){
                    copy[r+s][j] = copy[r+s][j+1];
                }
                copy[r+s][c+s-1] = downTemp;

                // 위로 이동
                for(int i=r-s; i<r+s; i++){
                    copy[i][c-s] = copy[i+1][c-s];
                }
                copy[r+s-1][c-s] = leftTemp;
            }
        }
        res = Math.min(res, calRowSum(copy));
    }

    // 행의 합 구하기
    static int calRowSum(int[][] arr){
        int min_sum = Integer.MAX_VALUE;
        for(int i=0; i<N; i++){
            int temp_sum = 0;
            for(int j=0; j<M; j++){
                temp_sum += arr[i][j];
            }
            min_sum = Math.min(min_sum, temp_sum);
        }
        return min_sum;
    }

    // 회전 연산 순서 정하기
    static void dfs(int cnt){
        if(cnt == K){
            rotationArr();
            return;
        }
        for(int i=0; i<K; i++){
            if(visited[i]) continue;

            visited[i] = true;
            orders[cnt] = i;
            dfs(cnt+1);
            visited[i] = false;
        }
    }
}
