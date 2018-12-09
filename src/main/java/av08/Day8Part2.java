package av08;

import util.FileIO;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day8Part2 {

    private List<Integer> input;

    public Day8Part2(String input) {
        this.input = Arrays.stream(input.split(" ")).map(Integer::valueOf).collect(Collectors.toList());
    }


    public int part2() {
        Iterator<Integer> inputIterator = input.iterator();
        Node rootNode = processNode(0, inputIterator);

        System.out.println(input);

        StringBuilder sb = new StringBuilder();
        printTree(rootNode, sb, " ");
        System.out.println(sb.toString());

        System.out.println(rootNode.totalMetaData);
        return rootNode.totalMetaData;
    }

    private void printTree (Node node, StringBuilder sb, String indent) {
        sb.append(node);
        for (Node childrenNode : node.children) {
            printTree(childrenNode, sb.append("\n").append(indent), indent + "  ");
        }
    }

    private int sumMetaDatas (Node node, Integer total) {
        total = node.totalMetaData;
        for (Node childrenNode : node.children) {
            total += sumMetaDatas(childrenNode, total);
        }
        return total;
    }

    private Node processNode(int cnt, Iterator<Integer> inputIterator) {
        String id = Character.toString((char) ('A' + cnt));

        int numChildren = inputIterator.next();
        int numMetadata = inputIterator.next();

        List<Node> children = new LinkedList<>();
        //children call
        for (int i = 0; i < numChildren; i++) {
            Node node = processNode(++cnt, inputIterator);
            children.add(node);
        }

        List<Integer> metadatas = new LinkedList<>();
        int total = 0;

        for (int i = 0; i < numMetadata; i++) {
            int metadata = inputIterator.next();
            if (numChildren == 0) {
                metadatas.add(metadata);
                total += metadata;
            } else {
                Node childrenNode = metadata > children.size() ? null : children.get(--metadata);
                total += childrenNode != null ? childrenNode.totalMetaData : 0;
            }
        }

        return new Node(id,children,metadatas,total);
    }

    public static void main(String[] args) {
        Day8Part2 d = new Day8Part2(FileIO.getFileAsList("src/main/java/av08/input.txt").get(0));
        System.out.println(d.part2());
    }



    private class Node {
        String id;
        List<Node> children = new LinkedList<>();
        List<Integer> metadatas = new LinkedList<>();
        int totalMetaData = 0;

        public Node(String id, List<Node> children, List<Integer> metadatas, int totalMetaData) {
            this.id = id;
            this.children = children;
            this.metadatas = metadatas;
            this.totalMetaData = totalMetaData;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id='" + id + '\'' +
                    ", children=" + children +
                    ", metadatas=" + metadatas +
                    ", totalMetaData=" + totalMetaData +
                    '}';
        }
    }


}
