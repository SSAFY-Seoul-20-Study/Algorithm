class Solution {
    public int solution(int[][] board, int[][] skill) {

        int n = board.length;
        int m = board[0].length;
        int[][] sum = new int[n + 1][m + 1];

        for(int i = 0; i < skill.length; i++) {
            int cmd = skill[i][0] == 1 ? -1 : 1;
            int x1 = skill[i][1];
            int y1 = skill[i][2];
            int x2 = skill[i][3];
            int y2 = skill[i][4];
            int v = skill[i][5];

            sum[x1][y1] += cmd * v;
            sum[x2 + 1][y2 + 1] += cmd * v;
            sum[x1][y2 + 1] -= cmd * v;
            sum[x2 + 1][y1] -= cmd * v;
        }


        for(int i = 0; i < n; i++) {
            for(int j = 1; j < m; j++) {
                 sum[i][j] += sum[i][j - 1];
            }
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j < m; j++) {
                 sum[i][j] += sum[i - 1][j];
            }
        }

        int ans = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] + sum[i][j] > 0) ans++; 
            }
        } 

        return ans;
    }
}
