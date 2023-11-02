class Solution {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        int[][] sum = new int[board.length + 1][board[0].length + 1];
        
        for(int i = 0; i < skill.length; i++) {
            int r1 = skill[i][1], c1 = skill[i][2];
            int r2 = skill[i][3], c2 = skill[i][4];
            
            int degree = skill[i][5];
            if(skill[i][0] == 1) degree = -degree;
            
            sum[r1][c1] += degree;
            sum[r2 + 1][c1] += -degree;
            sum[r1][c2 + 1] += -degree;
            sum[r2 + 1][c2 + 1] += degree;
        }
        
        for(int i = 0; i< board.length; i++){
            int tmp = 0;
            for(int j = 0; j < board[i].length; j++){
                tmp += sum[i][j];
                sum[i][j] = tmp;
            }
        }
        
        for(int i=0; i< board[0].length; i++){
            int tmp = 0;
            for(int j=0; j< board.length; j++){
                tmp += sum[j][i];
                sum[j][i] = tmp;
            }
        }
        
        for(int i=0; i< board.length; i++){
            for(int j=0; j< board[i].length; j++){
                if(sum[i][j] + board[i][j] > 0 ) answer++;
            }
        }  
        
        return answer;
    }
}
