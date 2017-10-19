package com.matlib;

public class Matrix {

    private double m[][];

    private int rows;
    private int cols;

    public int rows() {
        return rows;
    }

    public void rows(int rows) {
        this.rows = rows;
    }

    public int cols() {
        return cols;
    }

    public void cols(int cols) {
        this.cols = cols;
    }

    public Matrix(double[][] m) {
        this.m = m;
        rows = m.length;
        if(rows > 0) {
            cols = m[0].length;
        }
    }

    public double element(int i, int j) {
        if(i >= rows) {
            throw new IndexOutOfBoundsException("Given index i: " + i + " is out of bound");
        }

        if(j >= cols) {
            throw new IndexOutOfBoundsException("Given index j: " + j + " is out of bound");
        }

        return m[i][j];
    }

    public Matrix element(int i, int j, double element) {
        if(i >= rows) {
            throw new IndexOutOfBoundsException("Given index i: " + i + " is out of bound");
        }

        if(j >= cols) {
            throw new IndexOutOfBoundsException("Given index j: " + j + " is out of bound");
        }

        m[i][j] = element;
        return this;
    }

    public Matrix inverse() {
        double[][] augmentedMatrix = getAugmentedMatrix();
        Matrix augMatrix = new Matrix(augmentedMatrix);
        return findInverse(augMatrix);
    }

    private double[][] getAugmentedMatrix() {
        if(rows != cols) {
            return null;
        }
        double[][] augmentedMatrix = new double[rows][cols + rows];

        augmentMatrixWithIdentityMatrix(this, augmentedMatrix);
        return augmentedMatrix;
    }

    private static void augmentMatrixWithIdentityMatrix(Matrix matrix, double[][] augmentedMatrix) {
        for (int i = 0; i < matrix.rows; i++) {

            for (int j = 0; j < matrix.cols; j++) {
                augmentedMatrix[i][j] = matrix.element(i, j);
            }

            for(int j = matrix.cols; j < matrix.rows + matrix.cols; j++) {
                if(i == (j - matrix.cols)) {
                    augmentedMatrix[i][j] = 1;
                } else {
                    augmentedMatrix[i][j] = 0;
                }
            }
        }
    }

    private static Matrix findInverse(Matrix augmentedMatrix) {

        doGaussJordanElimination(augmentedMatrix);

        double[][] inverse = new double[augmentedMatrix.rows][augmentedMatrix.rows];
        for(int i = 0; i < augmentedMatrix.rows; i++) {
            for (int j = augmentedMatrix.rows, z = 0; j < augmentedMatrix.cols; j++, z++) {
                inverse[i][z] = augmentedMatrix.element(i, j);
            }
        }

        return new Matrix(inverse);
    }

    private static void doGaussJordanElimination(Matrix augmentedMatrix) {

        for (int k = 0; k < augmentedMatrix.rows; k++) {
            double coeff = augmentedMatrix.element(k, k);
            if (0 == coeff) {
                int newIndexOfK = reorderEquation(augmentedMatrix, k);
                if(newIndexOfK < 0) {
                    throw new RuntimeException("No swappable rows found");
                }
                coeff = augmentedMatrix.element(k, k);
            }

            for (int j = 0; j < augmentedMatrix.cols; j++) {
                augmentedMatrix.element(k, j, augmentedMatrix.element(k, j) / coeff );
            }
            for (int i = 0; i < augmentedMatrix.rows; i++) {
                if (k == i) continue;
                for (int j = k + 1; j < augmentedMatrix.cols; j++) {
                    double elem = augmentedMatrix.element(i, j) - (augmentedMatrix.element(k, j) * augmentedMatrix.element(i, k));
                    augmentedMatrix.element(i, j, elem);
                }
            }
        }
    }

    private static int reorderEquation(Matrix augmentedMatrix, int k) {
        int newIndexOfK = -1;
        for(int i = k + 1; i < augmentedMatrix.rows; i++) {
            if(augmentedMatrix.element(i, i) != 0) {
                newIndexOfK = i;
                swapRows(augmentedMatrix, k, newIndexOfK);
                break;
            }
        }

        return newIndexOfK;
    }

    private static void swapRows(Matrix matrix, int k, int newIndexOfK) {
        for(int i = 0; i < matrix.cols; i++) {
            matrix.element(k, i, matrix.element(k, i) + matrix.element(newIndexOfK, i));
            matrix.element(newIndexOfK, i, matrix.element(k, i) - matrix.element(newIndexOfK, i));
            matrix.element(k, i, matrix.element(k, i) - matrix.element(newIndexOfK, i));
        }
    }

    public static void dumpToConsole(Matrix matrix) {
        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.cols; j++) {
                if (j == matrix.rows) {
                    System.out.printf("  |  %f ", matrix.element(i, j));
                } else {
                    System.out.printf("  %f  ", matrix.element(i, j));
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
