package pt.isec.pa.tinypac.model.data;

import java.io.Serializable;

/**
 * Represents a ranking entry with a player's name, points, and seconds.
 * Implements the Serializable interface to allow for object serialization.
 *
 * This class is used to store and retrieve ranking information.
 * Each ranking entry includes the player's name, points earned, and seconds taken.
 *
 * Example usage:
 * ```
 * Ranking ranking = new Ranking("John", 100, 30);
 * int points = ranking.getPoints();
 * String name = ranking.getName();
 * int seconds = ranking.getSeconds();
 * ```
 *
 * Note: This class assumes that the points and seconds are non-negative integers.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class Ranking implements Serializable {
    private int points;
    private String name;
    private int seconds;

    /**
     * Constructs a new Ranking object with the specified name, points, and seconds.
     *
     * @param name the player's name
     * @param points the points earned
     * @param seconds the seconds taken
     */
    public Ranking(String name, int points, int seconds){
        this.name = name;
        this.points = points;
        this.seconds = seconds;
    }

    /**
     * Gets the points earned in the ranking entry.
     *
     * @return the points earned
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the player's name in the ranking entry.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the seconds taken in the ranking entry.
     *
     * @return the seconds taken
     */
    public int getSeconds() {
        return seconds;
    }
}
