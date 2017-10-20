import com.matlib.Matrices;
import com.matlib.Matrix;

import java.util.Scanner;

public class InverseWithLib {
    public static void main(String[] args) {
        double m[][];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Solution of linear equations");
        System.out.print("Number of unknows: ");
        int n = scanner.nextInt();
        if (n <= 0) {
            System.out.println("number of unknows cannot be zero or lesser");
            return;
        }
        m = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.out.printf("Enter the coefficients of equation %d \n", i + 1);
            for (int j = 0; j < n; j++) {
                m[i][j] = scanner.nextDouble();
            }
        }

        Matrix matrix = new Matrix(m);
        Matrices.dumpToConsole(matrix);
        System.out.println("Finding solution................");

        Matrix inverse = matrix.inverse();
        System.out.println("Inverse................");
        Matrices.dumpToConsole(inverse);

    }
}
