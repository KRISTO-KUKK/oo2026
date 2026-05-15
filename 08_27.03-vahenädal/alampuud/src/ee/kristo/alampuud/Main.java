package ee.kristo.alampuud;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<CompetitionMember> members = List.of(
                new Runner("Kati", "EST", 11.92),
                new Jumper("Marek", "LAT", 7.81),
                new Judge("Liina", "FIN", "peakohtunik"),
                new Timekeeper("Oskar", "EST", "elektrooniline ajavott")
        );

        for (CompetitionMember member : members) {
            System.out.println(member.introduction());
        }
    }
}

interface CompetitionMember {
    String introduction();
}

abstract class Person implements CompetitionMember {
    private final String name;
    private final String country;

    protected Person(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}

abstract class Athlete extends Person {
    private final double bestResult;

    protected Athlete(String name, String country, double bestResult) {
        super(name, country);
        this.bestResult = bestResult;
    }

    protected double getBestResult() {
        return bestResult;
    }

    protected abstract String eventName();
}

class Runner extends Athlete {
    Runner(String name, String country, double bestResult) {
        super(name, country, bestResult);
    }

    @Override
    public String introduction() {
        return getName() + " (" + getCountry() + ") jookseb ala " + eventName()
                + ", parim tulemus " + getBestResult() + " s.";
    }

    @Override
    protected String eventName() {
        return "100 m";
    }
}

class Jumper extends Athlete {
    Jumper(String name, String country, double bestResult) {
        super(name, country, bestResult);
    }

    @Override
    public String introduction() {
        return getName() + " (" + getCountry() + ") voistleb alal " + eventName()
                + ", parim tulemus " + getBestResult() + " m.";
    }

    @Override
    protected String eventName() {
        return "kaugushupe";
    }
}

abstract class Official extends Person {
    private final String responsibility;

    protected Official(String name, String country, String responsibility) {
        super(name, country);
        this.responsibility = responsibility;
    }

    protected String getResponsibility() {
        return responsibility;
    }
}

class Judge extends Official {
    Judge(String name, String country, String responsibility) {
        super(name, country, responsibility);
    }

    @Override
    public String introduction() {
        return getName() + " (" + getCountry() + ") on kohtunik: " + getResponsibility() + ".";
    }
}

class Timekeeper extends Official {
    Timekeeper(String name, String country, String responsibility) {
        super(name, country, responsibility);
    }

    @Override
    public String introduction() {
        return getName() + " (" + getCountry() + ") vastutab aja eest: " + getResponsibility() + ".";
    }
}
