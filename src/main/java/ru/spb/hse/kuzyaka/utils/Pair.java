package ru.spb.hse.kuzyaka.utils;

/**
 * Represents comparable pair with lexicographical order
 * @param <X> type of first element of a pair, must be comparable
 * @param <Y> type of second element of a pair, must be comparable
 */
public class Pair<X extends Comparable<? super X>, Y extends Comparable<? super Y>>
        implements Comparable<Pair<X, Y>> {
    private X x;
    private Y y;

    /**
     * Constructs a pair
     * @param x first element of a pair
     * @param y second element of a pair
     */
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns first element of a pair
     * @return first element of a pair
     */
    public X getX() {
        return x;
    }

    /**
     * Returns second element of a pair
     * @return second element of a pair
     */
    public Y getY() {
        return y;
    }

    /**
     * Compares this pair to another pair with same types of elements
     * @param o other pair
     * @return result of comparison between second elements of pairs if first element of pairs are equal
     * else result of comparison between first elements of pairs
     */
    @Override
    public int compareTo(Pair<X, Y> o) {
        int x = getX().compareTo(o.getX());
        if (x == 0) {
            return getY().compareTo(o.getY());
        }
        return x;
    }
}
