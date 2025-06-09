import java.util.Scanner;
/*
    I can't tell you how

    I knew - but I did know that I had crossed

    The border. Everything I loved was lost

    But no aorta could report regret.

    A sun of rubber was convulsed and set;

    And blood-black nothingness began to spin

    A system of cells interlinked within

    Cells interlinked within cells interlinked

    Within one stem. And dreadfully distinct

    Against the dark, a tall white fountain played.
*/
class Cell {
  int element;
  Cell right, down, left, top;

  Cell() {
    this(0);
  }

  Cell(int element) {
    this.element = element;
    this.right = this.down = this.left = this.top = null;
  }
}

class Matrix {
  private Cell start;
  private int rows, cols;

  Matrix(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    buildMatrix();
  }

  void buildMatrix() {
    Cell[][] matrix = new Cell[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        matrix[i][j] = new Cell();
      }
    }

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (j < cols - 1)
          matrix[i][j].right = matrix[i][j + 1];
        if (i < rows - 1)
          matrix[i][j].down = matrix[i + 1][j];
        if (j > 0)
          matrix[i][j].left = matrix[i][j - 1];
        if (i > 0)
          matrix[i][j].top = matrix[i - 1][j];
      }
    }

    start = matrix[0][0];
  }

  void fill(java.util.Scanner sc) {
    Cell row = start;
    for (int i = 0; i < rows; i++) {
      Cell col = row;
      for (int j = 0; j < cols; j++) {
        col.element = sc.nextInt();
        col = col.right;
      }
      row = row.down;
    }
  }

  void printMainDiagonal() {
    Cell current = start;
    while (current != null) {
      System.out.print(current.element + " ");
      if (current.right != null && current.down != null)
        current = current.right.down;
      else
        break;
    }
    System.out.println();
  }

  void printSecondaryDiagonal() {
    if (rows != cols) {
      System.out.println(); 
      return;
    }

    Cell current = start;
    while (current.right != null)
    current = current.right;

    for (int i = 0; i < rows && current != null; i++) {
      System.out.print(current.element + " ");
      if (current.left != null && current.down != null)
        current = current.left.down;
      else
        break;
    }

    System.out.println();
  } 

  Matrix sum(Matrix other) {
    Matrix result = new Matrix(rows, cols);

    Cell rRow = result.start;
    Cell aRow = this.start;
    Cell bRow = other.start;

    for (int i = 0; i < rows; i++) {
      Cell rCol = rRow, aCol = aRow, bCol = bRow;
      for (int j = 0; j < cols; j++) {
        rCol.element = aCol.element + bCol.element;
        rCol = rCol.right;
        aCol = aCol.right;
        bCol = bCol.right;
      }
      rRow = rRow.down;
      aRow = aRow.down;
      bRow = bRow.down;
    }

    return result;
  }

  public Matrix multiply(Matrix other) {
    Matrix result = new Matrix(this.rows, other.cols);

    Cell resRow = result.start;
    Cell aRow = this.start;

    for (int i = 0; i < this.rows; i++) {
      Cell resCol = resRow;
      for (int j = 0; j < other.cols; j++) {
        int sum = 0;

        Cell a = aRow;
        Cell b = other.start;
        for (int k = 0; k < this.cols; k++) {
          Cell bTemp = b;
          for (int t = 0; t < j; t++) bTemp = bTemp.right;
          sum += a.element * bTemp.element;
          a = a.right;
          b = b.down;
        }

        resCol.element = sum;
        resCol = resCol.right;
      }
      aRow = aRow.down;
      resRow = resRow.down;
    }

    return result;
  }

  void print() {
    Cell row = start;
    for (int i = 0; i < rows; i++) {
      Cell col = row;
      for (int j = 0; j < cols; j++) {
        System.out.print(col.element + " ");
        col = col.right;
      }
      System.out.println();
      row = row.down;
    }
  }
}

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int testCases = sc.nextInt();

    for (int t = 0; t < testCases; t++) {
      int r1 = sc.nextInt();
      int c1 = sc.nextInt();
      Matrix m1 = new Matrix(r1, c1);
      m1.fill(sc);

      int r2 = sc.nextInt();
      int c2 = sc.nextInt();
      Matrix m2 = new Matrix(r2, c2);
      m2.fill(sc);

      m1.printMainDiagonal();
      m1.printSecondaryDiagonal();

      Matrix sum = m1.sum(m2);
      sum.print();

      Matrix product = m1.multiply(m2);
      product.print();
    }

    sc.close();
  }
}
