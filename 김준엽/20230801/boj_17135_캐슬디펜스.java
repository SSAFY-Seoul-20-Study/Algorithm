import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
public class Main {
    public static int N;
    public static int M;
    public static int D;
    static int [][] board;
    static int [][] copyBoard;
    static int answer= 0;
    static int max = Integer.MIN_VALUE;
    static Queue<int[]> queue = new LinkedList<int[]>();
    static int[] dx = {0,-1,0};
    static int[] dy = {-1,0,1};
    public static List<List<Integer>> getCombinations(int[] arr, int r) {
        List<List<Integer>> combinations = new ArrayList<>();
        List<Integer> currentCombination = new ArrayList<>();
        backtracking(arr, 0, r, currentCombination, combinations);
        return combinations;
    }

    public static void backtracking(int[] arr, int start, int r, List<Integer> currentCombination, List<List<Integer>> combinations) {
        if (r == 0) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = start; i <= arr.length - r; i++) {
            currentCombination.add(arr[i]);
            backtracking(arr, i + 1, r - 1, currentCombination, combinations);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
    public static void downEnemy(){
        for(int i = N-1; i>=1; i--){
            for(int j=0; j<M; j++){
                board[i][j] = board[i-1][j];
            }
        }
        for(int j = 0; j<M; j++)board[0][j] = 0;
    }
    public static boolean checkEnemy(){
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(board[i][j] == 1) return true;
            }
        }
        return false;
    }
    public static void attack(List<Integer> archer){
        Queue<int[]> killQueue = new LinkedList<int[]>();
        for(int i = 0; i<3; i++){
            int x = N;
            int y = archer.get(i);
            queue.offer(new int[]{x,y});
            boolean [][] visited = new boolean[N+1][M];
            int degree = 0;
            while(!queue.isEmpty()){
                if(degree == D){
                    queue.clear();
                    break;
                }
                int size = queue.size();
                Loop : for(int j = 0; j<size; j++){
                    int[] cur = queue.poll();
                    for(int k = 0; k<3; k++) {
                        int nx = cur[0] + dx[k];
                        int ny = cur[1] + dy[k];

                        if (0 > nx || 0 > ny || ny >= M || visited[nx][ny]) continue;

                        if (board[nx][ny] == 1) {
                            killQueue.add(new int[]{nx, ny});
                            queue.clear();
                            break Loop;
                        }

                        visited[nx][ny] = true;
                        queue.add(new int[]{nx, ny});
                    }
                }
                degree++;
            }
        }
        while(!killQueue.isEmpty()) {
            int[] cur = killQueue.poll();

            if(board[cur[0]][cur[1]] == 1) {
                board[cur[0]][cur[1]] = 0;
                answer += 1;
            }
        }

    }

    public static int simulation(List<Integer> archer){
        while(checkEnemy()){
            attack(archer);
            downEnemy();
        }
        return 0;
    }
    static void init(){
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                board[i][j] = copyBoard[i][j];
            }
        }
        answer = 0;
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer sb = new StringTokenizer(br.readLine());
        N = Integer.parseInt(sb.nextToken());
        M = Integer.parseInt(sb.nextToken());
        D = Integer.parseInt(sb.nextToken());
        board = new int[N+1][M];
        copyBoard = new int[N+1][M];
        for(int i = 0; i<N; i ++){
            sb = new StringTokenizer(br.readLine());
            for(int j = 0; j<M; j++){
                board[i][j] = Integer.parseInt(sb.nextToken());
                copyBoard[i][j] = board[i][j];
            }
        }
        int []arr = new int [M];
        for(int i=0; i<M;  i++){
            arr[i] = i;
        }
        List<List<Integer>> result = getCombinations(arr, 3);


        for(int i=0; i<result.size(); i++){
            simulation(result.get(i));
            max = Math.max(max, answer);
            init();
        }
        System.out.println(max);
    }
}
//참고 https://kwangkyun-world.tistory.com/entry/Java%EC%9E%90%EB%B0%94-%EB%B0%B1%EC%A4%80-17135-%EC%BA%90%EC%8A%AC-%EB%94%94%ED%8E%9C%EC%8A%A4?category=1007890