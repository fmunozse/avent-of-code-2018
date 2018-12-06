package av04;

import util.FileIO;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {

    private class Period {
        int init;
        int end;
        int total;

        public Period(int init, int end, int total) {
            this.init = init;
            this.end = end;
            this.total = total;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "init=" + init +
                    ", end=" + end +
                    ", total=" + total +
                    '}';
        }
    }

    private class Track {
        String monthDay;
        String idGuard;
        int[] minutee = new int[60];

        long totalSeconds = 0;
        List<Period> periods = new LinkedList<>();

        String getKey() {
            return this.monthDay + "_" + this.idGuard;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 60; i++) {
                sb.append(String.format(" %2d",minutee[i]));
                //sb.append(minutee[i] == 0 ? "." : minutee[i]);
            }
            return String.format("%5s : %4s :: (%2s) %s - %s", monthDay , idGuard, totalSeconds, sb.toString(),periods);
        }
    }

    public int chooseGuard(List<String> tracks) {
        List<String> tracksOrder = tracks.stream().sorted().collect(Collectors.toList());
        Map<String, Track> all = parse(tracksOrder);

        //Prepare all tracks por Guard and take the guard with max minutes asleep
        Map<String, Long> mapGuardPerTotalMinutes = new LinkedHashMap<>();
        Map<String, List<Track>> mapGuardWithAllTracks = new LinkedHashMap<>();
        all.values().stream().forEach(track -> {
            mapGuardPerTotalMinutes.put(track.idGuard, mapGuardPerTotalMinutes.getOrDefault(track.idGuard, 0L) + track.totalSeconds);
            List<Track> l = mapGuardWithAllTracks.getOrDefault(track.idGuard, new LinkedList<>());
            l.add(track);
            mapGuardWithAllTracks.put(track.idGuard, l);
        });

        //Get idGuard most asleep
        String idGuardMaxAsleep = Collections.max(mapGuardPerTotalMinutes.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();

        System.out.println("\n\n");
        mapGuardWithAllTracks.get(idGuardMaxAsleep).stream().forEach(System.out::println);


        //Track merged
        Track trackMerged = new Track();
        trackMerged.idGuard = idGuardMaxAsleep;
        for (Track track : mapGuardWithAllTracks.get(idGuardMaxAsleep)) {
            for (int i = 0; i < track.minutee.length; i++) {
                if (track.minutee[i] > 0) {
                    trackMerged.minutee[i]++;
                }
            }
        }

        //Max minute
        int pos = 0;
        int max = 0;
        for (int i = 0; i < trackMerged.minutee.length; i++) {
            if (trackMerged.minutee[i] > max) {
                pos = i;
                max = trackMerged.minutee[i];
            }
        }

        System.out.println(trackMerged);
        System.out.printf("Max: %s, minute: %s %n", max, pos);

        return pos * Integer.valueOf(idGuardMaxAsleep);
    }

    private Map<String, Track> parse(List<String> tracksOrder) {
        Map<String, Track> all = new LinkedHashMap<>();

        Track track = new Track();

        int startSeconds = 0;
        for (String trackLine : tracksOrder) {
            System.out.println("trackLine: " + trackLine);
            Matcher matcherGuard = Pattern.compile("\\[.*\\] Guard #(.+) begins shift$").matcher(trackLine);
            if (matcherGuard.find()) {
                String guard = matcherGuard.group(1);

                track = all.getOrDefault(guard, new Track());
                track.idGuard = guard;
            }

            Matcher matcherFallsAsLeep = Pattern.compile("\\[.{4}-(.{2}-.{2}) .{2}:(.{2})\\] falls asleep$").matcher(trackLine);
            if (matcherFallsAsLeep.find()) {
                String monthDay = matcherFallsAsLeep.group(1);
                startSeconds = Integer.valueOf(matcherFallsAsLeep.group(2));
                track.monthDay = monthDay;
            }

            Matcher matcherWakeUp = Pattern.compile("\\[.{4}-(.{2}-.{2}) .{2}:(.{2})\\] wakes up$").matcher(trackLine);
            if (matcherWakeUp.find()) {
                int endSeconds = Integer.valueOf(matcherWakeUp.group(2));
                for (int i = startSeconds; i < endSeconds; i++) {
                    track.minutee[i] = 1;
                }
                track.totalSeconds = Arrays.stream(track.minutee).filter(value -> value > 0).count();
                track.periods.add(new Period(startSeconds, endSeconds , endSeconds - startSeconds));

                all.put(track.getKey(), track);
            }
        }

        all.values().forEach(System.out::println);

        return all;
    }

    public String toString(int[] minutee) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            sb.append(minutee[i] == 0 ? "." : minutee[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Day4 d = new Day4();
        System.out.println(d.chooseGuard(FileIO.getFileAsList("src/main/java/av04/inputPart01.txt")));
    }

}
