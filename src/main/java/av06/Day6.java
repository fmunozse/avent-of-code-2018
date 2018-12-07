package av06;

import util.FileIO;

import java.util.*;

public class Day6 {

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
        Area areaMin;

        Point(int x, int y, String id) {
            this.x = x;
            this.y = y;
            this.id = id;
        }
    }

    public int part1(List<String> input, int size) {

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

        //calculate areas
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                List<Area> areas = new LinkedList<>();
                for (Point point : inputPoints) {
                    Area area = new Area(point.id, calculateManhattanDistance(i, j, point.x, point.y));
                    areas.add(area);
                }
                if (table[i][j] == null) table[i][j] = new Point(i, j, null);
                table[i][j].distances = areas;
            }
        }
        System.out.println(printTable(table));

        //Determine min Area per point:
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                Point point = table[i][j];
                point.areaMin = determinateAreaShorter(table[i][j].distances);
            }
        }
        System.out.println(printTableMinAreas(table));


        //Calculate total area por id
        Map<String, Integer> countPointsPerId = new LinkedHashMap<>();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                Point point = table[i][j];
                if (point.areaMin.id != ".") {
                    int count = countPointsPerId.getOrDefault(point.areaMin.id, 0);
                    countPointsPerId.put(point.areaMin.id, ++count);
                }
            }
        }
        System.out.println(countPointsPerId);

        //prune infinetes
        Map<String, Integer> removeInfinetes = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : countPointsPerId.entrySet()) {
            if (!isInfinete(table, entry.getKey())) {
                removeInfinetes.put(entry.getKey(), entry.getValue());
            }

        }
        System.out.println(removeInfinetes);

        int max = removeInfinetes.values().stream().mapToInt(value -> value).max().getAsInt();
        System.out.println(max);

        return max;

        //System.out.println(table[5][0].distances);
        //System.out.println(determinateAreaShorter(table[5][0].distances));

    }

    private boolean isInfinete(Point[][] table, String id) {
        for (int i = 0; i < table.length; i++) {
            Point p = table[0][i];
            if (p.areaMin.id.equals(id)) {
                return true;
            }
        }

        for (int i = 0; i < table.length; i++) {
            Point p = table[table.length-1][i];
            if (p.areaMin.id.equals(id)) {
                return true;
            }
        }

        for (int i = 0; i < table.length; i++) {
            Point p = table[i][0];
            if (p.areaMin.id.equals(id)) {
                return true;
            }
        }

        for (int i = 0; i < table.length; i++) {
            Point p = table[i][table.length-1];
            if (p.areaMin.id.equals(id)) {
                return true;
            }
        }

        return false;
    }

    private Area determinateAreaShorter(List<Area> areas) {
        Area minArea = areas.stream().min(Comparator.comparingInt(value -> value.distance)).get();

        //in case duplicate, means that are = distance
        if (areas.stream().filter(area -> area.distance == minArea.distance).count() > 1) {
            return new Area(".", 0);
        } else {
            return minArea;
        }
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


    private String printTableMinAreas(Point[][] table) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                sb.append(table[i][j].areaMin.id);
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        Day6 d = new Day6();
        System.out.println(d.part1(FileIO.getFileAsList("src/main/java/av06/input.txt"),400));
    }
}
