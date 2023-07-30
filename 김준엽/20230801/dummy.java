package homework;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
public class Main {
    public static int N;
    public static int M;
    public static int D;
    public static boolean [] check;
    static int [][] board;
    static int [][] copyBoard;
    static int answer= 0;
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
        List<int[]> shoot = new ArrayList<>();
        for(int a = 0; a<3; a++) {
            int pos_x = N;
            int pos_y = archer.get(a);
            int[] min_pos = new int[2];
            int min = Integer.MAX_VALUE;
            for (int i = N-1; i >= 0; i--) {
                for (int j = M-1; j >= 0; j--) {
                    int distance = Math.abs(i - pos_x) + Math.abs(j - pos_y);
                    if(distance >D) continue;
                    if (board[i][j] == 1 && distance <= min) {
                        min = distance;
                        min_pos[0] = i;
                        min_pos[1] = j;
                    }
                }
            }
            if(min != Integer.MAX_VALUE) shoot.add(min_pos);
        }
        //실제로 공격 시작 (중복 된 적 체크)
        for(int i =0; i<shoot.size(); i++){
            System.out.print(Arrays.toString(shoot.get(i)));
            int [] pos = shoot.get(i);
            if(board[pos[0]][pos[1]] == 1){
                board[pos[0]][pos[1]] = 0;
                answer ++;
            }
        }
        System.out.println();

    }
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
    public static int simulation(List<Integer> archer){
        while(checkEnemy()){
            attack(archer);
            downEnemy();
        }
        System.out.println(answer);
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
        board = new int[N][M];
        copyBoard = new int[N][M];
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
            for(int j=0; j<N; j++) System.out.println(Arrays.toString(board[j]));
            simulation(result.get(i));
            System.out.println(result.get(i));
            System.out.println();
            init();

        }
    }
}
