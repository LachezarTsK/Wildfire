import java.util.LinkedList;

public class Solution {

  /*
  Input data: '0' designates land that does not spread fire.
              '1' designates trees on fire.
              '2' designates trees not on fire.
  */
  public int[][] matrix;
  public int[][] moves = {
    {-1, 0}, // up
    {1, 0}, // down
    {0, -1}, // left
    {0, 1} // right
  };

  /*
  By the problem design on binarysearch.com, we have to work
  around the given method 'public int solve(int[][] matrix)' so that the code
  can be run on the website. Even though the name 'solve' does not make
  a lot of sense, it is left as it is, so that the code can be run directly
  on the website, without any modifications.
  */
  public int solve(int[][] matrix) {
    this.matrix = matrix;
    return bfs_numberOfDaysToSpreadTheFire();
  }

  /*
  Breadth First Search: find number of days to spread the fire over all the trees in the area.
  @return Number of days, as described, if it is possible to spread the fire over all trees.
          Otherwise, it returns '-1'.
  */
  public int bfs_numberOfDaysToSpreadTheFire() {

    LinkedList<Point> queue = find_coordinates_treesOnFire();
    int trees_notOnFire = find_numberOfTrees_notOnFire();
    int days = 0;

    while (!queue.isEmpty()) {

      int treesOnFire = queue.size();

      while (treesOnFire-- > 0) {

        Point current = queue.removeFirst();
        int r = current.row;
        int c = current.column;

        for (int i = 0; i < moves.length; i++) {
          int new_r = r + moves[i][0];
          int new_c = c + moves[i][1];

          if (isInMatrix(new_r, new_c) && matrix[new_r][new_c] == 1) {
            queue.add(new Point(new_r, new_c));
            matrix[new_r][new_c] = 2;
            trees_notOnFire--;
          }
        }
      }

      days++;
    }
    return trees_notOnFire > 0 ? -1 : days > 0 ? days - 1 : 0;
  }

  public boolean isInMatrix(int row, int column) {
    if (row < 0 || column < 0 || row > matrix.length - 1 || column > matrix[0].length - 1) {
      return false;
    }
    return true;
  }

  public int find_numberOfTrees_notOnFire() {

    int trees_notOnFire = 0;
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        if (matrix[r][c] == 1) {
          trees_notOnFire++;
        }
      }
    }
    return trees_notOnFire;
  }

  public LinkedList<Point> find_coordinates_treesOnFire() {

    LinkedList<Point> treesOnFire = new LinkedList<Point>();
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        if (matrix[r][c] == 2) {
          treesOnFire.add(new Point(r, c));
        }
      }
    }
    return treesOnFire;
  }
}

class Point {
  int row;
  int column;

  public Point(int row, int column) {
    this.row = row;
    this.column = column;
  }
}
