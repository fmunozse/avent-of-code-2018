package av07;

import util.FileIO;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7Part2 {

    private Set<Node> nodes = new LinkedHashSet<>();

    private int baseSeconds ;

    public int part2(List<String> input, int numWorkers, int baseSeconds) {
        this.baseSeconds = baseSeconds;

        //Parse
        input.stream().forEach(this::parseLine);
        System.out.printf("Nodes: %s %n", printNodes(nodes));


        int seconds = 0;
        int cntNodeCompleted = 1;
        int freeWorkers = numWorkers;

        Set<Node> nodesWithWorker = new LinkedHashSet<>();

        boolean happenSomeThing = true;
        do {
            happenSomeThing = false;
            Set<Node> nodesToWork = findAllPendingChildrenNodesWithParentCompletedAndNotWorking();

            System.out.printf("Second %s, nodes to work: %s %n", seconds, nodesToWork);

            while (freeWorkers > 0 && nodesToWork.size() > 0) {
                Node nodeWorking = findNodeToComplete(nodesToWork).get();
                nodeWorking.working = true;
                nodesWithWorker.add(nodeWorking);
                freeWorkers--;
                System.out.printf("Assign node %s. Free workers %n", nodeWorking, freeWorkers);
                nodesToWork.remove(nodeWorking);
                happenSomeThing = true;
            }

            Set<Node> nodesToRemove = new LinkedHashSet<>();
            for (Node nodeWorking : nodesWithWorker) {
                if (--nodeWorking.secondCost == 0) {
                    nodeWorking.nodeCompleted = cntNodeCompleted++;
                    nodesToRemove.add(nodeWorking);
                    freeWorkers++;
                    System.out.printf("Completed %s %n", nodeWorking.id);
                }
                happenSomeThing = true;
            }
            for (Node nodeCompleted : nodesToRemove) {
                nodesWithWorker.remove(nodeCompleted);
            }

            seconds++;
        } while (happenSomeThing);


        return --seconds;
    }


    private String printNodes(Set<Node> nodes) {
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

            Node nodeParent = findNodeById(node1).orElse(new Node(node1));
            Node nodeChildren = findNodeById(node2).orElse(new Node(node2));
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
        return !(node.parents.stream().map(this::findNodeById).filter(node1 -> node1.get().nodeCompleted == null).count() > 0);
    }

    private Set<Node> findAllPendingChildrenNodesWithParentCompletedAndNotWorking() {
        return nodes.stream()
                .filter(node -> !node.working)
                .filter(node -> node.nodeCompleted == null)  //Pending to complete
                .filter(this::nodeHasAllParentCompleted)
                .collect(Collectors.toSet());

    }

    private class Node {
        String id;
        int secondCost = 0;
        Set<String> parents = new LinkedHashSet<>();
        Integer nodeCompleted = null;
        boolean working = false;

        public Node(String id) {
            this.id = id;
            secondCost = (id.charAt(0) - 'A' + 1) + baseSeconds;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id='" + id + '\'' +
                    ", secondCost='" + secondCost + '\'' +
                    ", parents=" + parents +
                    ", nodeCompleted=" + nodeCompleted +
                    '}';
        }
    }


    public static void main(String[] args) {
        Day7Part2 d = new Day7Part2();

        System.out.println(d.part2(FileIO.getFileAsList("src/main/java/av07/input.txt"), 5, 60 ));
    }
}
