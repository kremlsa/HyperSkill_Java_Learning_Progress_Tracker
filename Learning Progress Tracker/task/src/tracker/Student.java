package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class Student {
    private String name;
    private String lastName;
    private String email;
    private String id;
    private int javaScore =0;
    private int dsaScore =0;
    private int dbScore =0;
    private int springScore =0;
    Map<String, String> notifications = new LinkedHashMap<>();

    public int getJavaScore() {
        return javaScore;
    }

    public void setJavaScore(int javaScore) {
        this.javaScore += javaScore;
        if (this.javaScore >= 600 && !notifications.containsKey("Java")) {
            notifications.put("Java", "ready");
        }
    }

    public int getDsaScore() {
        return dsaScore;
    }

    public void setDsaScore(int dsaScore) {
        this.dsaScore += dsaScore;
        if (this.dsaScore >= 400 && !notifications.containsKey("DSA")) {
            notifications.put("DSA", "ready");
        }
    }

    public int getDbScore() {
        return dbScore;
    }

    public void setDbScore(int dbScore) {
        this.dbScore += dbScore;
        if (this.dbScore >= 480 && !notifications.containsKey("Databases")) {
            notifications.put("Databases", "ready");
        }
    }

    public int getSpringScore() {
        return springScore;
    }

    public void setSpringScore(int springScore) {
        this.springScore += springScore;
        if (this.springScore >= 550 && !notifications.containsKey("Spring")) {
            notifications.put("Spring", "ready");
        }
    }

    public String getId() {
        return id;
    }

    public Student(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.id = Utils.calcHash(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScores(String jS, String dS, String dbS, String sS) {
        setJavaScore(Integer.parseInt(jS));
        setDbScore(Integer.parseInt(dbS));
        setDsaScore(Integer.parseInt(dS));
        setSpringScore(Integer.parseInt(sS));
    }

    public void printScores() {
        System.out.printf("%s points: Java=%s; DSA=%s; Databases=%s; Spring=%s%n",
                this.id, this.javaScore, this.dsaScore, this.dbScore, this.springScore);
    }

    public String javaStats() {
        return new BigDecimal((double) this.javaScore * 100.0 / 600.0)
                .setScale(1, RoundingMode.HALF_UP)
                .toString();
    }

    public String dsaStats() {
        return new BigDecimal((double) this.dsaScore * 100.0 / 400.0)
                .setScale(1, RoundingMode.HALF_UP)
                .toString();
    }

    public String dbStats() {
        return new BigDecimal((double) this.dbScore * 100.0 / 480.0)
                .setScale(1, RoundingMode.HALF_UP)
                .toString();
    }

    public String springStats() {
        return new BigDecimal((double) this.springScore * 100.0 / 550.0)
                .setScale(1, RoundingMode.HALF_UP)
                .toString();
    }

    public Double getCompletion(String course) {
        switch (course) {
            case "java":
                return new BigDecimal((double) this.javaScore * 100.0 / 600.0)
                        .setScale(1, RoundingMode.HALF_UP)
                        .doubleValue();
            case "dsa":
                return new BigDecimal((double) this.dsaScore * 100.0 / 400.0)
                        .setScale(1, RoundingMode.HALF_UP)
                        .doubleValue();
            case "databases":
                return new BigDecimal((double) this.dbScore * 100.0 / 480.0)
                        .setScale(1, RoundingMode.HALF_UP)
                        .doubleValue();
            case "spring":
                return new BigDecimal((double) this.springScore * 100.0 / 550.0)
                        .setScale(1, RoundingMode.HALF_UP)
                        .doubleValue();
        }
        return 0.0;
    }

    public void courseStats(String course) {
        switch (course) {
            case "java":
                if (!javaStats().equals("0.0")) {
                    System.out.printf("%s %s        %s%%\n", this.id, this.javaScore, javaStats());
                }
                break;
            case "dsa":
                if (!dsaStats().equals("0.0")) {
                    System.out.printf("%s %s        %s%%\n", this.id, this.dsaScore, dsaStats());
                }
                break;
            case "databases":
                if (!dbStats().equals("0.0")) {
                    System.out.printf("%s %s        %s%%\n", this.id, this.dbScore, dbStats());
                }
                break;
            case "spring":
                if (!springStats().equals("0.0")) {
                    System.out.printf("%s %s        %s%%\n", this.id, this.springScore, springStats());
                }
                break;
        }
    }

    public boolean notification() {
        boolean isNotified = false;
        for (Map.Entry<String, String> entry : notifications.entrySet()) {
            if (entry.getValue().equals("ready")) {
                System.out.printf("To: %s\n" +
                        "Re: Your Learning Progress\n" +
                        "Hello, %s %s! You have accomplished our %s course!\n",
                        this.email, this.name, this.lastName, entry.getKey());
                notifications.put(entry.getKey(), "sent");
                isNotified = true;
            }
        }
        return isNotified;
    }
}
