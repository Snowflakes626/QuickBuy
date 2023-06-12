package forRound;

public class test {
    public static void main(String[] args) {
        char[][] grid = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = '0';
            }
        }
        grid[0][0] = '1';
        grid[0][1] = '1';
        grid[4][1] = '1';
        int i = numIslands(grid);
        System.out.println("i = " + i);

    }

    static boolean[][] isV;
    public static int numIslands(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        isV = new boolean[row][col];
        int count = 0;
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++){
                if(grid[i][j] == '1' && isV[i][j] == false){
                    DFS(grid, i, j);
                    count++;
                }
                else
                    continue;
            }
        return count;
    }

    public static void DFS(char[][] grid, int i, int j){
        isV[i][j] = true;
        if(i - 1 >= 0 && grid[i - 1][j] == '1' &&isV[i - 1][j] == false)
            DFS(grid, i - 1, j);
        if(i + 1 < grid.length && grid[i + 1][j] == '1' && isV[i + 1][j] == false)
            DFS(grid, i + 1, j);
        if(j - 1 >= 0 && grid[i][j - 1] == '1' && isV[i][j - 1] == false)
            DFS(grid, i, j - 1);
        if(j + 1 < grid[0].length && grid[i][j + 1] == '1' && isV[i][j + 1] == false)
            DFS(grid, i, j + 1);
    }
}
