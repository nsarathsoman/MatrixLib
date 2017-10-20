package com.matlib;

public class Matrices {

    private static Matrix augmentMatrixAWithB(Matrix a, Matrix b) {
        if(a.rows() != b.rows()) {
            return null;
        }

        double[][] augMat = new double[a.rows()][a.cols() + b.cols()];
        for (int i = 0; i < a.rows(); i++) {

            for (int j = 0; j < a.cols(); j++) {
                augMat[i][j] = a.element(i, j);
            }

            for (int j = a.cols(); j < a.cols() + b.cols(); j++) {
                augMat[i][j] = b.element(i, j);
            }
        }

        return new Matrix(augMat);
    }

    private static Matrix augmentMatrixWithIdentityMatrix(Matrix matrix) {
        if(matrix.rows() != matrix.cols()) {
            return null;
        }

        double[][] augMat = new double[matrix.rows()][matrix.cols() + matrix.rows()];
        for (int i = 0; i < matrix.rows(); i++) {

            for (int j = 0; j < matrix.cols(); j++) {
                augMat[i][j] = matrix.element(i, j);
            }

            for(int j = matrix.cols(); j < matrix.rows() + matrix.cols(); j++) {
                if(i == (j - matrix.cols())) {
                    augMat[i][j] = 1;
                } else {
                    augMat[i][j] = 0;
                }
            }
        }

        return new Matrix(augMat);
    }

    public static Matrix findInverse(Matrix matrix) {
        Matrix augMatrix = Matrices.augmentMatrixWithIdentityMatrix(matrix);
        return findInverseFromAugmentedMatrix(augMatrix);
    }

    public static Matrix findInverseFromAugmentedMatrix(Matrix augmentedMatrix) {

        doGaussJordanElimination(augmentedMatrix);

        double[][] inverse = new double[augmentedMatrix.rows()][augmentedMatrix.rows()];
        for(int i = 0; i < augmentedMatrix.rows(); i++) {
            for (int j = augmentedMatrix.rows(), z = 0; j < augmentedMatrix.cols(); j++, z++) {
                inverse[i][z] = augmentedMatrix.element(i, j);
            }
        }

        return new Matrix(inverse);
    }

    private static void doGaussJordanElimination(Matrix augmentedMatrix) {

        for (int k = 0; k < augmentedMatrix.rows(); k++) {
            double coeff = augmentedMatrix.element(k, k);
            if (0 == coeff) {
                int newIndexOfK = reorderEquation(augmentedMatrix, k);
                if(newIndexOfK < 0) {
                    throw new RuntimeException("No swappable rows found");
                }
                coeff = augmentedMatrix.element(k, k);
            }

            for (int j = 0; j < augmentedMatrix.cols(); j++) {
                augmentedMatrix.element(k, j, augmentedMatrix.element(k, j) / coeff );
            }
            for (int i = 0; i < augmentedMatrix.rows(); i++) {
                if (k == i) continue;
                for (int j = k + 1; j < augmentedMatrix.cols(); j++) {
                    double elem = augmentedMatrix.element(i, j) - (augmentedMatrix.element(k, j) * augmentedMatrix.element(i, k));
                    augmentedMatrix.element(i, j, elem);
                }
            }
        }
    }

    private static int reorderEquation(Matrix augmentedMatrix, int k) {
        int newIndexOfK = -1;
        for(int i = k + 1; i < augmentedMatrix.rows(); i++) {
            if(augmentedMatrix.element(i, i) != 0) {
                newIndexOfK = i;
                swapRows(augmentedMatrix, k, newIndexOfK);
                break;
            }
        }

        return newIndexOfK;
    }

    private static void swapRows(Matrix matrix, int k, int newIndexOfK) {
        for(int i = 0; i < matrix.cols(); i++) {
            matrix.element(k, i, matrix.element(k, i) + matrix.element(newIndexOfK, i));
            matrix.element(newIndexOfK, i, matrix.element(k, i) - matrix.element(newIndexOfK, i));
            matrix.element(k, i, matrix.element(k, i) - matrix.element(newIndexOfK, i));
        }
    }

    public static void dumpToConsole(Matrix matrix) {
        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.cols(); j++) {
                if (j == matrix.rows()) {
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
