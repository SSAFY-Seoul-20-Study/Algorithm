class Solution {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        int N = board.length;
        int M = board[0].length;
        int [][]copied = new int[N+1][M+1];
        for(int []s :skill){
            int damage = s[5];
            if(s[0] == 1){
                damage = -damage;
            }
            copied[s[1]][s[2]] += damage;
            copied[s[3] + 1][s[2]] += -damage;
            copied[s[1]][s[4]+1] += -damage;
            copied[s[3] + 1][s[4]+1] += damage;
        }
        for(int i=0; i<N+1; i++){
            for(int j=1; j<M+1; j++){
                copied[i][j] += copied[i][j-1];
            }
        }
        for(int j=0; j<M+1; j++){
            for(int i=1; i<N+1; i++){
                copied[i][j] += copied[i-1][j];
            }
        }
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                board[i][j] += copied[i][j];
                if(board[i][j] > 0) answer++;
            }
        }
        return answer;
    }
}