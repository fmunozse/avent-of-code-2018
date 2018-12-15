package av13;

import lombok.Data;
import util.FileIO;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day13 {

    public String part1(List<String> input) {
        int maxX = 152;
        int maxY = 152;
        List<Cart> carts = new LinkedList<>();

        String[][] table = new String[maxY][maxX];
        loadTable(input, carts, table);
        printTable(table, carts);

        for (int i = 0; i < 500; i++) {
            //carts.stream().forEach(cart -> cart.tick(table, carts));
            carts.stream().sorted(Comparator.comparingInt(Cart::getY).thenComparingInt(Cart::getX)).forEach(cart -> {
                cart.tick(table, carts);
                System.out.println("cart = " + cart);
            });
            printTable(table, carts);
        }

        return null;
    }

    private void loadTable(List<String> input, List<Cart> carts, String[][] table) {
        int cntY = 0;
        for (String line : input) {
            char[] chars = line.toCharArray();
            for (int x = 0; x < chars.length; x++) {
                if (chars[x] == '<' || chars[x] == '>') {
                    Cart cart = new Cart(Character.toString(chars[x]), x, cntY);
                    carts.add(cart);
                    table[cntY][x] = Character.toString('-');
                } else if (chars[x] == '^' || chars[x] == 'v') {
                    Cart cart = new Cart(Character.toString(chars[x]), x, cntY);
                    carts.add(cart);
                    table[cntY][x] = Character.toString('|');
                } else {
                    table[cntY][x] = Character.toString(chars[x]);
                }
            }
            cntY++;
        }
    }

    private void printTable(String[][] table, List<Cart> carts) {
        Map<String, Cart> cartMap = carts.stream().collect(Collectors.toMap(Cart::keyPosition, o -> o, (cart, cart2) -> {
            System.out.println("cart = " + cart);
            throw new RuntimeException("stopped in " + cart);
        }));
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < table.length; y++) {
            for (int x = 0; x < table[y].length; x++) {
                if (cartMap.containsKey(x + "_" + y)) {
                    sb.append(cartMap.get(x + "_" + y).direction);
                } else {
                    sb.append(table[y][x] != null ? table[y][x] : " ");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);

    }

    public static void main(String[] args) {
        Day13 d = new Day13();
        //d.part1(FileIO.getFileAsList("src/main/java/av13/test.txt"));
        d.part1(FileIO.getFileAsList("src/main/java/av13/input.txt"));
        //d.part1(FileIO.getFileAsList("src/main/java/av10/input.txt"), 200000);

    }


    @Data
    private class Cart {
        String direction;
        int x, y;
        int intersection = 0;

        public Cart(String direction, int x, int y) {
            this.direction = direction;
            this.x = x;
            this.y = y;
        }

        public boolean isInPosition(int x, int y) {
            return this.x == x && this.y == y;
        }

        public String keyPosition() {
            return this.x + "_" + this.y;
        }

        private void nextIntersection() {
            int module = intersection % 3;
            switch (module) {
                case 0:
                    //1º time (right)
                    if ("^".equals(direction)) {
                        direction = "<";
                    } else if ("<".equals(direction)) {
                        direction = "v";
                    } else if (">".equals(direction)) {
                        direction = "^";
                    } else if ("v".equals(direction)) {
                        direction = ">";
                    }
                    break;
                case 1:
                    //same
                    break;
                case 2:
                    //3 time (left)
                    if ("^".equals(direction)) {
                        direction = ">";
                    } else if ("<".equals(direction)) {
                        direction = "^";
                    } else if (">".equals(direction)) {
                        direction = "v";
                    } else if ("v".equals(direction)) {
                        direction = "<";
                    }
                    break;
            }
            intersection++;

        }

        private void collition(List<Cart> carts) {
            carts.stream().forEach(cart -> {
                if (cart != this && cart.x == this.x && cart.y == this.y) {
                    System.out.println("cart = " + cart);
                    throw new RuntimeException("stopped in " + cart);
                }
            });
        }

        public void tick(String[][] table, List<Cart> carts) {
            if (">".equals(this.direction)) {
                this.x++;
                if ("\\".equals(table[this.y][this.x])) {
                    this.direction = "v";
                } else if ("/".equals(table[this.y][this.x])) {
                    this.direction = "^";
                } else if ("+".equals(table[this.y][this.x])) {
                    nextIntersection();
                }

            } else if ("<".equals(this.direction)) {
                this.x--;
                if ("\\".equals(table[this.y][this.x])) {
                    this.direction = "^";
                } else if ("/".equals(table[this.y][this.x])) {
                    this.direction = "v";
                } else if ("+".equals(table[this.y][this.x])) {
                    nextIntersection();
                }

            } else if ("v".equals(this.direction)) {
                this.y++;
                if ("\\".equals(table[this.y][this.x])) {
                    this.direction = ">";
                } else if ("/".equals(table[this.y][this.x])) {
                    this.direction = "<";
                } else if ("+".equals(table[this.y][this.x])) {
                    nextIntersection();
                }

            } else if ("^".equals(this.direction)) {
                this.y--;
                if ("\\".equals(table[this.y][this.x])) {
                    this.direction = "<";
                } else if ("/".equals(table[this.y][this.x])) {
                    this.direction = ">";
                } else if ("+".equals(table[this.y][this.x])) {
                    nextIntersection();
                }
            }
            collition(carts);
        }
    }
}
