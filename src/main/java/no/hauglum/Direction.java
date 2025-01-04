package no.hauglum;

public enum Direction {
    NORMAL(1), INVERTED(-1);

    private final int i;

    Direction(int i) {
        this.i = i;
    }

    public int getVale() {
        return i;
    }
}
