package me.cuiyijie.nongmo;

/**
 * @author cyj976655@gmail.com
 * @date 2021/4/19 22:51
 */
public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int max = solution.getMax();
        System.out.println("max:" + max);
    }

    static class Solution {
        int MAX = 101;
        int[][] cachedResult = new int[MAX][MAX];

        public int getMax() {

            int[][] D = new int[MAX][MAX];//存储数字三角形
            D[0] = new int[]{7};
            D[1] = new int[]{3, 8};
            D[2] = new int[]{8, 1, 0};
            D[3] = new int[]{2, 7, 4, 4};
            D[4] = new int[]{4, 5, 2, 6, 5};
            int n = 5;//n表示层数
            int i = 0;
            int j = 0;
            int maxSum = getMaxSum(D, n, i, j);
            System.out.println("calcu num: " + calcuNum);
            return maxSum;
        }

        int calcuNum = 0;

        public int getMaxSum(int[][] D, int n, int i, int j) {
            if (cachedResult[i][j] != 0) {
                return cachedResult[i][j];
            }
            calcuNum ++ ;
            if (i == n) {
                return D[i][j];
            }
            int x = getMaxSum(D, n, i + 1, j);
            int y = getMaxSum(D, n, i + 1, j + 1);
            cachedResult[i][j] = Math.max(x, y) + D[i][j];
            return cachedResult[i][j];
        }
    }
}
