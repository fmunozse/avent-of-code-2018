package av13;

import lombok.Data;
import util.FileIO;

import java.util.*;
import java.util.stream.Collectors;

public class Day13Part2 {

    public String part1(List<String> input) {
        int maxX = 152;
        int maxY = 152;
        List<Cart> carts = new LinkedList<>();

        String[][] table = new String[maxY][maxX];
        loadTable(input, carts, table);
        printTable(table, carts);

        for (int i = 0; i < 100000; i++) {

            List<Cart> sortCars =  carts.stream().sorted(Comparator.comparingInt(Cart::getY).thenComparingInt(Cart::getX)).collect(Collectors.toList()) ;
            carts = new LinkedList<>();
            for (Cart cart : sortCars) {
                if(cart.hasCrash) {
                    continue;
                }
                //System.out.println("cart = " + cart);
                cart.tick(table);
                Cart collitionCart = collition(sortCars, cart);
                if (collitionCart != null) {
                    cart.hasCrash = true;
                    collitionCart.hasCrash = true;
                    carts.remove(collitionCart);
                    carts.remove(cart);
                    continue;
                }
                carts.add(cart);
            }

            //printTable(table, carts);
            if(carts.size() != sortCars.size()) {
                System.out.println("Removed cart in iteration " + i + ", " + carts.size() + " - " + sortCars.size());
                carts.forEach(cart -> System.out.println("    carts.cart = " + cart));
                sortCars.forEach(cart -> System.out.println("    sortCars.cart = " + cart));
            }
            if (carts.size() == 1) {
                System.out.println("carts = " + carts);
                break;
            }

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

    private Cart collition(List<Cart> carts, Cart currentCart) {
        for (Cart cart : carts) {
            if (cart != currentCart && cart.x == currentCart.x && cart.y == currentCart.y) {
                System.out.println("Collition cart = " + cart + ",and currentCart:" + currentCart);
                return cart;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        Day13Part2 d = new Day13Part2();
        //d.part1(FileIO.getFileAsList("src/main/java/av13/test.txt"));
        //d.part1(FileIO.getFileAsList("src/main/java/av13/testPart2.txt"));
        d.part1(FileIO.getFileAsList("src/main/java/av13/input.txt"));
        //d.part1(FileIO.getFileAsList("src/main/java/av10/input.txt"), 200000);

    }


    @Data
    private class Cart {
        int id;
        String direction;
        int x, y;
        int intersection = 0;
        boolean hasCrash;

        public Cart(String direction, int x, int y) {
            this.direction = direction;
            this.x = x;
            this.y = y;
            Random random = new Random();
            id =  random.nextInt();
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


        public void tick(String[][] table) {
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
        }
    }
}
