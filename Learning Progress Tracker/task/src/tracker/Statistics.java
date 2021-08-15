package tracker;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Double.NaN;
import static java.util.stream.Collectors.joining;

public class Statistics {
    private Set<String> javaStudents = new LinkedHashSet<>();
    private Set<String> dsaStudents = new LinkedHashSet<>();
    private Set<String> dbStudents = new LinkedHashSet<>();
    private Set<String> springStudents = new LinkedHashSet<>();
    private List<Integer> javaTasks = new ArrayList<>();
    private List<Integer> dsaTasks = new ArrayList<>();
    private List<Integer> dbTasks = new ArrayList<>();
    private List<Integer> springTasks = new ArrayList<>();

    public void addJavaStudents(String student) {
        this.javaStudents.add(student);
    }

    public void addDsaStudents(String student) {
        this.dsaStudents.add(student);
    }

    public void addDbStudents(String student) {
        this.dbStudents.add(student);
    }

    public void addSpringStudents(String student) {
        this.springStudents.add(student);
    }

    public void addTasks(String id, int jT, int dsaT, int dbT, int sT) {
        if (jT != 0) {
            this.javaTasks.add(jT);
            addJavaStudents(id);
        }
        if (dsaT != 0) {
            this.dsaTasks.add(dsaT);
            addDsaStudents(id);
        }
        if (dbT != 0) {
            this.dbTasks.add(dbT);
            addDbStudents(id);
        }
        if (sT != 0) {
            this.springTasks.add(sT);
            addSpringStudents(id);
        }
    }

    String mostPopular = "n/a";
    String leastPopular = "n/a";

    public void printStats() {
        if (javaStudents.size() == 0 && dsaStudents.size() == 0 && dbStudents.size() == 0 && springStudents.size() == 0) {
            mostPopular = "n/a";
            leastPopular = "n/a";
        } else {
            Map<String, Integer> studentsInCourse = new LinkedHashMap<>();
            studentsInCourse.put("Java", javaStudents.size());
            studentsInCourse.put("DSA", dsaStudents.size());
            studentsInCourse.put("Databases", dbStudents.size());
            studentsInCourse.put("Spring", springStudents.size());
            Integer maxSize = studentsInCourse.entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue)).get().getValue();
            Integer minSize = studentsInCourse.entrySet()
                    .stream()
                    .min(Comparator.comparing(Map.Entry::getValue)).get().getValue();
            Map<String, Integer> mostPopularMap = studentsInCourse.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
//                            .thenComparing(Map.Entry.comparingByKey()))
                    .filter(x -> maxSize.equals(x.getValue()))
                    .collect(Collectors.toMap
                            (Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            Map<String, Integer> leastPopularMap = studentsInCourse.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
//                            .thenComparing(Map.Entry.comparingByKey()))
                    .filter(x -> x.getValue().equals(0) && !mostPopularMap.containsKey(x.getKey()))
                    .collect(Collectors.toMap
                            (Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            mostPopular = mostPopularMap.entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .collect(joining(" "));
            leastPopular = leastPopularMap.entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .collect(joining(" "));
            if (mostPopular.isEmpty()) mostPopular = "n/a";
            if (leastPopular.isEmpty()) leastPopular = "n/a";
        }
        System.out.printf("Most popular: %s%n", mostPopular);
        System.out.printf("Least popular: %s%n", leastPopular);

        if (javaTasks.size() == 0 && dsaTasks.size() == 0 && dbTasks.size() == 0 && springTasks.size() == 0) {
            mostPopular = "n/a";
            leastPopular = "n/a";
        } else {

            Map<String, Integer> activitiesInCourse = new LinkedHashMap<>();
            activitiesInCourse.put("Java", javaTasks.size());
            activitiesInCourse.put("DSA", dsaTasks.size());
            activitiesInCourse.put("Databases", dbTasks.size());
            activitiesInCourse.put("Spring", springTasks.size());
            Integer maxAct = activitiesInCourse.entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue)).get().getValue();
            Integer minAct = activitiesInCourse.entrySet()
                    .stream()
                    .min(Comparator.comparing(Map.Entry::getValue)).get().getValue();
            Map<String, Integer> mostPopularAct = activitiesInCourse.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey()))
                    .filter(x -> maxAct.equals(x.getValue()))
                    .collect(Collectors.toMap
                            (Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            Map<String, Integer> leastPopularAct = activitiesInCourse.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey()))
                    .filter(x -> minAct.equals(x.getValue()) && !mostPopularAct.containsKey(x.getKey()))
                    .collect(Collectors.toMap
                            (Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            mostPopular = mostPopularAct.entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .collect(joining(" "));
            leastPopular = leastPopularAct.entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .collect(joining(" "));
            if (mostPopular.isEmpty()) mostPopular = "n/a";
            if (leastPopular.isEmpty()) leastPopular = "n/a";
        }
        System.out.printf("Highest activity: %s%n", mostPopular);
        System.out.printf("Lowest activity: %s%n", leastPopular);

        if (javaTasks.size() == 0 && dsaTasks.size() == 0 && dbTasks.size() == 0 && springTasks.size() == 0) {
            mostPopular = "n/a";
            leastPopular = "n/a";
        } else {

            Map<String, Double> hardestInCourse = new LinkedHashMap<>();
            hardestInCourse.put("Java", javaTasks.stream().mapToDouble(a -> a).sum() / (double) javaTasks.size());
            hardestInCourse.put("DSA", dsaTasks.stream().mapToDouble(a -> a).sum() / (double) dsaTasks.size());
            hardestInCourse.put("Databases", dbTasks.stream().mapToDouble(a -> a).sum() / (double) dbTasks.size());
            hardestInCourse.put("Spring", springTasks.stream().mapToDouble(a -> a).sum() / (double) springTasks.size());

            Double maxHardest = hardestInCourse.entrySet()
                    .stream()
                    .filter(x -> !x.getValue().equals(NaN))
                    .max(Comparator.comparing(Map.Entry::getValue))
                    .get().getValue();
            Double minHardest = hardestInCourse.entrySet()
                    .stream()
                    .filter(x -> !x.getValue().equals(NaN))
                    .min(Comparator.comparing(Map.Entry::getValue)).get().getValue();

            Map<String, Double> easiestInCourseMap = hardestInCourse.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey()))
                    .filter(x -> maxHardest.equals(x.getValue()))
                    .collect(Collectors.toMap
                            (Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            Map<String, Double> hardestInCourseMap = hardestInCourse.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey()))
                    .filter(x -> minHardest.equals(x.getValue()) && !easiestInCourseMap.containsKey(x.getKey()))
                    .collect(Collectors.toMap
                            (Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            mostPopular = easiestInCourseMap.entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .collect(joining(" "));
            leastPopular = hardestInCourseMap.entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .collect(joining(" "));
            if (mostPopular.isEmpty()) mostPopular = "n/a";
            if (leastPopular.isEmpty()) leastPopular = "n/a";
        }
        System.out.printf("Easiest course: %s%n", mostPopular);
        System.out.printf("Hardest course: %s%n", leastPopular);

    }
}
