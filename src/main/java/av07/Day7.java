package av07;

import av04.Day4Part2;
import av06.Day6;
import util.FileIO;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 {

    private Set<Node> nodes = new LinkedHashSet<>();

    public String part1 (List<String> input) {
        //Parse
        input.stream().forEach(this::parseLine);
        System.out.printf("Nodes: %s %n", printNodes(nodes));

        Set<Node> nodesToWork = findAllPendingChildrenNodesWithParentCompleted();

        int cntNodeCompleted = 1;
        while(nodesToWork.size() != 0) {
            Node nodeCompleted = findNodeToComplete(nodesToWork).get();
            nodeCompleted.nodeCompleted = cntNodeCompleted++;

            nodesToWork = findAllPendingChildrenNodesWithParentCompleted();
            System.out.printf("Completed %s -> nodes to work: %s %n", nodeCompleted.id, nodesToWork);
        }

        //Get stream
        String s = nodes.stream().sorted(Comparator.comparingInt(value -> value.nodeCompleted)).map(node -> node.id).collect(Collectors.joining());
        return s;
    }



        private String printNodes (Set<Node> nodes) {
        return nodes.stream().map(Node::toString).collect(Collectors.joining("\n"));
    }

    private Optional<Node> findNodeById(String id) {
        return nodes.stream().filter(node -> id.equals(node.id)).findAny();
    }

    private void parseLine(String line) {
        Matcher matcherLine = Pattern.compile("^Step ([A-Za-z]+) must be finished before step ([A-Za-z]+) can begin.$").matcher(line);
        if (matcherLine.find()) {
            String node1 = matcherLine.group(1);
            String node2 = matcherLine.group(2);

            Node nodeParent = findNodeById(node1).orElse( new Node(node1));
            Node nodeChildren = findNodeById(node2).orElse( new Node(node2));
            nodeChildren.parents.add(nodeParent.id);

            nodes.add(nodeParent);
            nodes.add(nodeChildren);
        }
    }

    private Collection<? extends Node> findChildrenNodesFromParent(Node node) {
        return null;
    }

    private Optional<Node> findNodeToComplete(Set<Node> nodesToWork) {
        //Order by id
        Set<Node> nodesSort = nodesToWork.stream().sorted(Comparator.comparing(node -> node.id)).collect(Collectors.toCollection(LinkedHashSet::new));

        //Get firt node which all parents has completed
        return nodesSort.stream().filter(this::nodeHasAllParentCompleted).findFirst();
    }

    private boolean nodeHasAllParentCompleted(Node node) {
        return ! (node.parents.stream().map(this::findNodeById).filter(node1 -> node1.get().nodeCompleted == null).count() > 0);
    }

    private Set<Node> findAllPendingChildrenNodesWithParentCompleted() {
        return nodes.stream()
                .filter(node -> node.nodeCompleted == null)  //Pending to complete
                .filter(this::nodeHasAllParentCompleted)
                .collect(Collectors.toSet());

    }

    private class Node {
        String id;
        Set<String> parents = new LinkedHashSet<>();
        Integer nodeCompleted = null;
        public Node(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id='" + id + '\'' +
                    ", parents=" + parents +
                    ", nodeCompleted=" + nodeCompleted +
                    '}';
        }
    }


    public static void main(String[] args) {
        Day7 d = new Day7();
        System.out.println(d.part1(FileIO.getFileAsList("src/main/java/av07/input.txt")));
    }
}
