package tracker;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    Statistics st = new Statistics();
    private List<String> commands = new ArrayList<>(Arrays.asList("exit", "add students", "back", "list",
            "add points", "find", "statistics", "notify"));
    private Scanner sc = new Scanner(System.in);
    private Map<String, Student> students = new LinkedHashMap();
    Map<String, String> courses = new HashMap<String, String>() {{
        put("java","Java");
        put("dsa","DSA");
        put("databases","Databases");
        put("spring","Spring");
    }};

    public String processInput() {
        while (true) {
            String userInput = sc.nextLine().strip();
            if (commands.contains(userInput.toLowerCase())) {
                return userInput;
            } else if (userInput.equals("")) {
                System.out.println("No input.");
            } else {
                System.out.println("Error: unknown command!");
            }
        }
    }

    public void addStudents() {
        int counter = 0;
        System.out.println("Enter student credentials or 'back' to return:");
        do {
                String[] input = sc.nextLine().strip().split("\\s+");
                if (input[0].equals("back")) break;
                if (!Checker.isCredsValid(input)) {
                    System.out.println("Incorrect credentials");
                    continue;
                }
                String email = input[input.length - 1];
                String name = input[0];
                String lastName = "";
                boolean isLastNameOk = true;
                for (int i = 1; i < input.length - 1; i++) {
                    if (!Checker.isNameValid(input[i])) {
                        isLastNameOk = false;
                        break;
                    }
                lastName += input[i];
                lastName += " ";
                }
                lastName = lastName.strip();
                if (!Checker.isNameValid(name)) {
                    System.out.println("Incorrect first name.");
                    continue;
                }
                if (!isLastNameOk) {
                    System.out.println("Incorrect last name.");
                    continue;
                }
                if (!Checker.isEmailValid(email)) {
                    System.out.println("Incorrect email.");
                    continue;
                }
                if (students.containsKey(Utils.calcHash(email))) {
                    System.out.println("This email is already taken.");
                    continue;
                }
                students.put(Utils.calcHash(email), new Student(name, lastName, email));
                counter++;
                System.out.println("The student has been added.");

        } while (true);
            System.out.printf("Total %s students have been added.%n", counter);
    }

    public void listStudents() {
        if (students.size() == 0) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students");
            students.forEach((k, v) -> System.out.println(k));
        }
    }

    public void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        do {
            String[] input = sc.nextLine().strip().split("\\s+");
            if (input[0].equals("back")) break;
            if (!String.join(" ", input).matches("[a-zA-Z0-9]+ \\d+ \\d+ \\d+ \\d+")) {
                System.out.println("Incorrect points format.");
                continue;
            }
            String id = input[0];
            String javaScore = input[1];
            String dsaScore = input[2];
            String dbScore = input[3];
            String springScore = input[4];
            if (!students.containsKey(id)) {
                System.out.printf("No student is found for id=%s.%n", id);
                continue;
            }
            Student student = students.get(id);
            st.addTasks(id, Integer.parseInt(javaScore), Integer.parseInt(dsaScore),
                    Integer.parseInt(dbScore), Integer.parseInt(springScore));
            student.setScores(javaScore, dsaScore, dbScore, springScore);
            System.out.println("Points updated.");
        } while (true);
    }

    public void findStudent() {
        System.out.println("Enter an id or 'back' to return:");
        do {
            String id = sc.nextLine();
            if (id.equals("back")) break;
            if (!students.containsKey(id)) {
                System.out.printf("No student is found for id=%s.%n", id);
                continue;
            }
            Student student = students.get(id);
            student.printScores();
        } while (true);
    }

    public void getStats() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        st.printStats();
        do {
            String course = sc.nextLine();
            if (course.equals("back")) break;
            if (!courses.containsKey(course.toLowerCase())) {
                System.out.println("Unknown course.");
                continue;
            }
            System.out.println(courses.get(course.toLowerCase()));
            System.out.println("id    points    completed");

            Map<String, Double> completions = students.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> e.getValue().getCompletion(course.toLowerCase())));
            Map<String, Double> sorted = completions.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey()))
                    .collect(Collectors.toMap
                            (Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            for (Map.Entry<String, Double> entry : sorted.entrySet()) {
                students.get(entry.getKey()).courseStats(course.toLowerCase());
            }

        } while (true);

    }

    public void sentNotification() {
        int counter = 0;
        for (Map.Entry<String, Student> entry : students.entrySet()) {
            if (students.get(entry.getKey()).notification()) {
                counter++;
            }
        }
        System.out.printf("Total %s students have been notified.", counter);
    }

    public void start() {
        System.out.println("Learning Progress Tracker");
        label:
        do {
            String command = processInput();
            switch (command) {
                case "exit":
                    System.out.println("Bye!");
                    break label;
                case "add students":
                    addStudents();
                    break;
                case "list":
                    listStudents();
                    break;
                case "add points":
                    addPoints();
                    break;
                case "find":
                    findStudent();
                    break;
                case "statistics":
                    getStats();
                    break;
                case "notify":
                    sentNotification();
                    break;
                case "back":
                    System.out.println("Enter 'exit' to exit the program");
                    break;
            }
        } while (true);
    }
}
