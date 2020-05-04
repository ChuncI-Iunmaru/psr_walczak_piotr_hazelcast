package data;

import java.io.Serializable;

public class Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String species;
    private String feedType;
    private Double feedPerDay;

    public Animal() {
    }

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Double getFeedPerDay() {
        return feedPerDay;
    }

    public void setFeedPerDay(Double feedPerDay) {
        this.feedPerDay = feedPerDay;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    @Override
    public String toString() {
        return "Zwierzę '" + name + "' gatunku " + species + " potrzebuje dziennie " + feedPerDay + "kg karmy " + feedType;
    }
}
