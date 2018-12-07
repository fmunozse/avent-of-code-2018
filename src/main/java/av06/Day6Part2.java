package av06;

import util.FileIO;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day6Part2 {

    private class Area {
        String id;
        int distance;

        public Area(String id, int distance) {
            this.id = id;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Area{" +
                    "id='" + id + '\'' +
                    ", distance=" + distance +
                    '}';
        }
    }

    private class Point {
        int x, y;
        String id;
        List<Area> distances = new LinkedList<>();
        int totalDistances = 0;

        Point(int x, int y, String id) {
            this.x = x;
            this.y = y;
            this.id = id;
        }
    }

    public int part2(List<String> input, int size, int areaLimit) {

        // Init table
        Point[][] table = new Point[size][size];
        List<Point> inputPoints = new LinkedList<>();
        for (int i = 0; i < input.size(); i++) {
            String[] xy = input.get(i).split(", ");
            int x = Integer.valueOf(xy[1]);
            int y = Integer.valueOf(xy[0]);
            Point point = new Point(x, y, Character.toString((char) (i + 'A')));
            table[x][y] = point;
            inputPoints.add(point);
        }

        //calculate areas and total distance
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                List<Area> areas = new LinkedList<>();

                int cntDistances = 0;
                for (Point point : inputPoints) {
                    int distance = calculateManhattanDistance(i, j, point.x, point.y);
                    cntDistances += distance;
                    Area area = new Area(point.id, distance);
                    areas.add(area);
                }
                if (table[i][j] == null) table[i][j] = new Point(i, j, null);
                table[i][j].distances = areas;
                table[i][j].totalDistances += cntDistances;
            }
        }
        System.out.println(printTable(table));

        System.out.println(printTableMinAreas(table, areaLimit));

        //Calculate num of points with limit
        int cnt = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j].totalDistances < areaLimit) {
                    if (table[i][j].id != null) {
                        System.out.printf("Detected %s with totalarea: %s %n" , table[i][j].id , table[i][j].distances);
                    }
                }
                cnt += table[i][j].totalDistances < areaLimit ? 1 : 0;
            }
        }

        System.out.println(cnt);
        return cnt;
    }


    private int calculateManhattanDistance(int x0, int y0, int x1, int y1) {
        return Math.abs(x1 - x0) + Math.abs(y1 - y0);
    }

    private String printTable(Point[][] table) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                sb.append(table[i][j].id == null ? "." : table[i][j].id);
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    private String printTableMinAreas(Point[][] table, int areaLimit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {

                if (table[i][j].id != null) {
                    sb.append(table[i][j].id);
                } else {
                    sb.append(table[i][j].totalDistances < areaLimit ? "#" : ".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        Day6Part2 d = new Day6Part2();
        System.out.println(d.part2(FileIO.getFileAsList("src/main/java/av06/input.txt"), 400, 10000));
    }
}
