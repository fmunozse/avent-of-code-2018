package av12;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day12 {


    public static final int GENERATIONS = 21;
    public static final int INIT_LEFT = 10;
    public static final int INIT_RIGHT = 500;

    public int part1(String input, List<String> lMatcher) {
        List<String> initialState = input.chars()
                .mapToObj(e -> (char) e)
                .map(Object::toString)
                .collect(Collectors.toList());
        System.out.println("initialState = " + initialState);

        Map<String, String> matchers = lMatcher.stream()
                .collect(Collectors.toMap(o -> o.split(" => ")[0], o -> o.split(" => ")[1]));
        System.out.println("matchers = " + matchers);

        //initial for left
        for (int i = 0; i < INIT_LEFT; i++) {
            initialState.add(0, ".");
        }
        for (int i = 0; i < INIT_RIGHT; i++) {
            initialState.add(".");
        }

        System.out.println("initialState = " + initialState);

        List<String>[] generations = new List[GENERATIONS];

        generations[0] = initialState;
        for (int i = 1; i < GENERATIONS; i++) {
            generations[i] = new LinkedList<>();
            for (int j = 0; j < initialState.size(); j++) {
                generations[i].add(".");
            }

            //Iterate in stream
            for (int j = 2; j < generations[i - 1].size() - 2; j++) {
                String sMatch = getKeyToMatch(generations, i - 1, generations[i], j);
                if (matchers.containsKey(sMatch)) {
                    generations[i].set(j, matchers.get(sMatch));
                }
            }
        }

        Arrays.stream(generations).forEach(strings -> {
            strings.forEach(System.out::print);
            System.out.println("");
        });


        List<String> lastGeneration = generations[GENERATIONS - 1];
        int cnt = 0;
        int index = (INIT_LEFT * -1);
        for (int i = 0; i < lastGeneration.size(); i++) {
            cnt += lastGeneration.get(i).equals("#") ? index : 0;
            //System.out.println("index " + index + ", contains: " + lastGeneration.get(i));
            index ++;
        }
        System.out.println("cnt = " + cnt);

        long total = cnt + (42 * (50000000000L - GENERATIONS + 1));
        System.out.println("final total  = " + total );

        return 0;
    }

    private String getKeyToMatch(List<String>[] generations, int generation, List<String> stream, int positionStream) {
        StringBuilder sb = new StringBuilder();
        sb.append(generations[generation].get(positionStream - 2));
        sb.append(generations[generation].get(positionStream - 1));
        sb.append(generations[generation].get(positionStream));
        sb.append(generations[generation].get(positionStream + 1));
        sb.append(generations[generation].get(positionStream + 2));
        return sb.toString();
    }


}
