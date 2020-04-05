package ru.spb.hse.kuzyaka.utils;

public class Pair<X extends Comparable<? super X>, Y extends Comparable<? super Y>>
        implements Comparable<Pair<X, Y>> {
    private X x;
    private Y y;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    @Override
    public int compareTo(Pair<X, Y> o) {
        int x = getX().compareTo(o.getX());
        if (x == 0) {
            return getY().compareTo(o.getY());
        }
        return x;
    }
}
