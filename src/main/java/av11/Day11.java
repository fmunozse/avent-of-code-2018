package av11;


import lombok.Data;
import lombok.RequiredArgsConstructor;

public class Day11 {

    private static final int GRID_SIZE = 300;

    public String part1(int input) {

        Point[][] table = new Point[301][301];


        for (int y = 1; y <= 300; y++) {
            for (int x = 1; x <= 300; x++) {
                Point p = new Point(x + 10, x, y);
                int powerlevel = p.id * y;
                powerlevel += input;
                powerlevel = powerlevel * p.id;
                String sPowerLevel = String.valueOf(powerlevel);
                int i = 0;
                if (sPowerLevel.length() >= 3) {
                    i = Integer.valueOf(Character.valueOf(sPowerLevel.charAt(sPowerLevel.length() - 3)).toString());
                }
                i = i - 5;
                p.powerlevel = i;
                table[y][x] = p;
            }
        }


        StringBuilder sb = new StringBuilder();
        Point max = new Point(0, 0, 0);
        for (int y = 300; y > 0; y--) {
            for (int x = 1; x <= 300; x++) {
                sb.append(String.format("%3d", table[y][x].powerlevel));
                calculate3x3(y, x, table);
                if (table[y][x].totalgrid3x3 > max.totalgrid3x3) {
                    max = table[y][x];
                }
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
        System.out.println("max = " + max);


        return null;
    }


    public String part2(int input) {

        Point[][] table = new Point[301][301];


        for (int y = 1; y <= 300; y++) {
            for (int x = 1; x <= 300; x++) {
                Point p = new Point(x + 10, x, y);
                int powerlevel = p.id * y;
                powerlevel += input;
                powerlevel = powerlevel * p.id;
                String sPowerLevel = String.valueOf(powerlevel);
                int i = 0;
                if (sPowerLevel.length() >= 3) {
                    i = Integer.valueOf(Character.valueOf(sPowerLevel.charAt(sPowerLevel.length() - 3)).toString());
                }
                i = i - 5;
                p.powerlevel = i;
                table[y][x] = p;
            }
        }

//
//        StringBuilder sb = new StringBuilder();
//        Point max = new Point(0, 0, 0);
//        for (int y = 300; y > 0; y--) {
//            for (int x = 1; x <= 300; x++) {
//                sb.append(String.format("%3d", table[y][x].powerlevel));
//
//                for (int cuadrade = 1; cuadrade < 100 ; cuadrade++) {
//                    int grid = calculateNxN(y,x,table,cuadrade);
//                    if (grid  > max.totalgrid ) {
//                        max = table[y][x];
//                        max.totalgrid = grid;
//                        max.maxCuadra = cuadrade;
//                    }
//                }
//            }
//            sb.append("\n");
//        }

        int maxSum = Integer.MIN_VALUE;
        int cornerX = 0;
        int cornerY = 0;
        int sqSize = 0;
        int sum;
        for (int size = 1; size <= GRID_SIZE; size++) {
            for (int y = 1; y <= GRID_SIZE - size; y++) {
                for (int x = 1; x <= GRID_SIZE - size; x++) {
                    sum = gridSum(table, x, y, size);
                    if (sum > maxSum) {
                        maxSum = sum;
                        cornerX = x;
                        cornerY = y;
                        sqSize = size;
                    }
                }
            }
        }

        System.out.printf("x: %d, y: %d, size: %d", cornerX, cornerY, sqSize);

        //System.out.println(sb.toString());
        //System.out.println("max = " + max);
        return null;
    }

    public int gridSum(Point grid[][], int topLeftX, int topLeftY, int size) {
        int result = 0;

        for (int y = topLeftY + size - 1; y >= topLeftY; y--) {
            for (int x = topLeftX + size - 1; x >= topLeftX; x--) {
                result += grid[x][y].powerlevel;
            }
        }

        return result;
    }

    void calculate3x3(int y, int x, Point[][] table) {
        for (int y1 = y; (y1 > (y - 3)) && y1 > 0; y1--) {
            for (int x1 = x; (x1 < (x + 3)) && x1 < table[y].length; x1++) {
                table[y][x].totalgrid3x3 += table[y1][x1].powerlevel;
            }
        }
    }



    int calculateNxN(int y, int x, Point[][] table, int n) {
        int cnt = 0;
        for (int y1 = y; (y1 > (y - n)) && y1 > 0; y1--) {
            for (int x1 = x; (x1 < (x + n)) && x1 < table[y].length; x1++) {
                cnt += table[y1][x1].powerlevel;
            }
        }
        return cnt;
    }


    @Data
    @RequiredArgsConstructor
    class Point {
        final Integer id, x, y;
        int powerlevel;
        int totalgrid3x3 = 0;
        int totalgrid = 0;
        int maxCuadra = 0;
    }

    public static void main(String[] args) {
        Day11 d = new Day11();
        d.part2(9306);
        //d.part1(57);
        //d.part1(71);
    }

}
