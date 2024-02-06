package com.instance.mancala2;

public enum PenaltyAmount {
    HALF(0.5),
    WHOLE(1.0),
    TWO_THIRDS(2.0 / 3.0);

    private final double fraction;

    PenaltyAmount(double fraction) {
        this.fraction = fraction;
    }

    public double getFraction() {
        return fraction;
    }
}
