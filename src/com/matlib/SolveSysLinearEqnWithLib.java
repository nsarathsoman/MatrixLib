package com.matlib;

import java.util.Scanner;

/**
 * Created by sarath on 25/08/17.
 */
public class SolveSysLinearEqnWithLib {
    public static void main(String[] args) {

        double m[][];
        double c[][];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Solution of linear equations");
        System.out.print("Number of unknows: ");
        int n = scanner.nextInt();
        if (n <= 0) {
            System.out.println("number of unknows cannot be zero or lesser");
            return;
        }
        m = new double[n][n];
        c = new double[n][1];
        for (int i = 0; i < n; i++) {
            System.out.printf("Enter the coefficients of equation %d \n", i + 1);
            for (int j = 0; j < n; j++) {
                m[i][j] = scanner.nextDouble();
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.printf("Constant of equation %d : ", i + 1);
            c[i][1] = scanner.nextDouble();
        }

//        printMatrix(m);

        double solution[] = findSolution(m);

        printMatrix(m);


    }

    private static void printMatrix(double[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length + 1; j++) {
                if (j == m.length) {
                    System.out.printf("  |  %f ", m[i][j]);
                } else {
                    System.out.printf("  %f  ", m[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static double[] findSolution(double[][] m) {
        for (int k = 0; k < m.length; k++) {
            printMatrix(m);
            double coeff = m[k][k];
            if (0 == coeff) {
//                System.out.printf("Coeffient of cell [%d][%d] is zero \n", k, k);
                int newIndexOfK = reorderEquation(m, k);
                if(newIndexOfK < 0) {
                    System.out.println("No swappable equations found");
                    break;
                }
                System.out.printf("swapped row %d with %d\n", k, newIndexOfK);
                coeff = m[k][k];
            }

            for (int j = 0; j < m.length + 1; j++) {
                m[k][j] = m[k][j] / coeff;
                if(j == m.length) System.out.println(m[k][j]);
            }
            for (int i = 0; i < m.length; i++) {
                if (k == i) continue;
                for (int j = k +1 ; j < m.length + 1; j++) {
                    m[i][j] = m[i][j] - (m[k][j] * m[i][k]);
                }
            }
        }
        return null;
    }

    private static int reorderEquation(double[][] m, int k) {
        int newIndexOfK = -1;
        for(int i = k + 1; i < m.length; i++) {
            if(m[i][i] != 0) {
                newIndexOfK = i;
                swapRows(m, k, newIndexOfK);
                break;
            }
        }

        return newIndexOfK;
    }

    private static void swapRows(double[][] m, int k, int newIndexOfK) {
        for(int i = 0; i < m.length + 1; i++) {
            m[k][i] = m[k][i] + m[newIndexOfK][i];
            m[newIndexOfK][i] = m[k][i] - m[newIndexOfK][i];
            m[k][i] = m[k][i] - m[newIndexOfK][i];
        }
    }
}
