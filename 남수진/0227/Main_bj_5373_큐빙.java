import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static char[][][] cube;
    static char[] color = {'w', 'y', 'r', 'o', 'g', 'b'};
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for(int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());

            cube = new char[6][3][3];
            initCube();

            for(int i = 0; i < n; i++) {
                String input = st.nextToken();
                int dir = input.charAt(1) == '+' ? 1 : 0;
                turn(input.charAt(0), dir);
            }

            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    sb.append(cube[0][j][2 - i]);
                }
                sb.append("\n");
            }

        }

        System.out.println(sb.toString());
    }

    static void initCube() {
        for(int n = 0; n < 6; n++) {
            for(int i = 0; i < 3; i++){
                for(int j = 0; j< 3; j++){
                    cube[n][i][j] = color[n];
                }
            }
        }
    }

    static void turn(char target, int dir) {
        switch (target) {
            //U = 0, D = 1, F = 2, B = 3, L = 4, R = 5
            case 'U':
                rotate(0, 4, 2, 5, 3, dir);
                break;
            case 'D':
                rotate(1, 3, 5, 2, 4, dir);
                break;
            case 'F':
                rotate(2, 0, 4, 1, 5, dir);
                break;
            case 'B':
                rotate(3, 5, 1, 4, 0, dir);
                break;
            case 'L':
                rotate(4, 2, 0, 3, 1, dir);
                break;
            case 'R':
                rotate(5, 1, 3, 0, 2, dir);
                break;
        }
    }

    static void rotate(int f, int u, int l, int d, int r, int clk) {
        char[][] tmp = new char[3][3];
        char[] tmp2 = new char[3];

        if(clk == 1) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    tmp[i][j] = cube[f][2 - j][i];
                }
            }

            for (int i = 0; i < 3; i++) tmp2[i] = cube[u][i][0];
            for (int i = 0; i < 3; i++) cube[u][i][0] = cube[l][0][2 - i];
            for (int i = 0; i < 3; i++) cube[l][0][2 - i] = cube[d][2][i];
            for (int i = 0; i < 3; i++) cube[d][2][i] = cube[r][2 - i][2];
            for (int i = 0; i < 3; i++) cube[r][2 - i][2] = tmp2[i];
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    tmp[i][j] = cube[f][j][2 - i];
                }
            }

            for (int i = 0; i < 3; i++) tmp2[i] = cube[u][i][0];
            for (int i = 0; i < 3; i++) cube[u][i][0] = cube[r][2 - i][2];
            for (int i = 0; i < 3; i++) cube[r][2 - i][2] = cube[d][2][i];
            for (int i = 0; i < 3; i++) cube[d][2][i] = cube[l][0][2 - i];
            for (int i = 0; i < 3; i++) cube[l][0][2 - i] = tmp2[i];
        }
        cube[f] = tmp;
    }

}
