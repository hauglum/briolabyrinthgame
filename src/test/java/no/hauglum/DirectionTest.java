package no.hauglum;

import org.junit.jupiter.api.Assertions;


class DirectionTest {

    @org.junit.jupiter.api.Test
    void getVale() {
        Assertions.assertEquals(1, Direction.NORMAL.getVale());
        Assertions.assertEquals(-1, Direction.INVERTED.getVale());
    }
}