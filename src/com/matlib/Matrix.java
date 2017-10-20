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
        return Matrices.findInverse(this);
    }

}
