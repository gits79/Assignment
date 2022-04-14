import java.util.ArrayList;
import java.util.Scanner;

public class MatrixChain {

    static void calc(int[][] A, int[][] B, int[] d, int idx) {
        int[][] temp = new int[0][0];
        int a_row = A.length;
        int a_col = A[0].length;

        int b_row = B.length;
        int b_col = B[0].length;

        if (a_col == b_row) {
            d[idx] = a_row;
            d[idx + 1] = a_col;

        } else {
            System.out.println("행렬 계산 순서가 잘못되었습니다");
        }

    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] C = new int[1001][1001];
        int[] d = new int[500];
        int INF = Integer.MAX_VALUE / 10;

        ArrayList<int[][]> arrMat = new ArrayList<>();

        System.out.printf("곱셉할 행렬의 갯수 입력: ");
        int n = scanner.nextInt();

        int idx = 0;
        while (idx < n) {
            System.out.println((idx + 1) + "번째 배열의 크기를 입력하시오");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            int[][] a = new int[row][col];
            // 행렬을 저장하는 어레이 리스트에 행렬을 넣음
            arrMat.add(a);
            idx++;
        }

        for (int i = 0; i < arrMat.size(); i++) {
            // 어레이리스트에서 행렬 두개를 가져옴
            int[][] mat1 = arrMat.get(i);
            // 행렬 두개를 가져올때 마지막의 경우 그냥 i+1하면 사이즈를 벗어나기 때문에
            // 곱셈 계산가능한 임의의 행렬을 만들어 계산
            if (i< arrMat.size()-1) {
                int[][] mat2 = arrMat.get(i + 1);
                calc(mat1, mat2, d, i);
            }
            else if (i== arrMat.size()-1) {
                int[][] mat2 = new int[mat1[0].length][mat1.length];
                calc(mat1, mat2, d, i);
            }
        }





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
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(C[i][j] + "    ");
            }
            System.out.println();
        }
        System.out.println("\n 최종 해: " + C[1][n]);


    }


}