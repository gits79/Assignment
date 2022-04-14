public class MatrixChain {

    public static void main(String[] args) {
        int[][] A = new int[501][501];
        int[][] C = new int[1001][1001];
        int[] d = new int[500];
        int n;
        int INF = Integer.MAX_VALUE / 1000;


        for (int i = 1; i <= n; i++) {
            C[i][i] = 0;
        }

        for (int len = 1; len <= n - 1; len++) {
            for (int i = 1; i <= n - len; i++) {
                int j = i + len;
                C[i][j] = INF;
                for (int k = i; k <= j - 1; k++) {
                    int temp = C[i][k] + C[k + 1][j] + d[i - 1] * d[k] * d[j];
                    if (temp < C[i][j])
                        C[i][j] = temp;
                }
            }
        }
        for(int i =1;i<=n;i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(C[i][j]+"    ");
            }
            System.out.println();
        }
        System.out.println("최종 해: "+C[1][n]);


    }


}
