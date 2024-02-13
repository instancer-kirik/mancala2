package com.instance.mancala2;

public enum CheatPhrase {
    DEFAULT_PHRASE("Cheater"),
    CAP("Cap"),
    BS("BS"),
    PRESS_X_TO_DOUBT("X Doubt"),
    I_CALL_SHENANIGANS("I Call Shenanigans!"),
    SOMETHINGS_FISHY("Something's Fishy..."),
    THATS_SUS("That's Sus!"),
    NOT_SO_FAST("Not So Fast!"),
    THINK_AGAIN("Think Again!"),
    GOTCHA("Gotcha!"),
    THIS_AINT_IT_CHIEF("This Ain't It, Chief"),
    HIGH_KEY_HACKER("High-Key Hacker?"),
    SMOOTH_BRAIN_MOVE("Smooth Brain Move"),
    CAUGHT_IN_4K("Caught in 4K"),
    DOIN_A_BIT_TOO_MUCH("Doin' a Bit Too Much"),
    FORSOOTH_A_BAMBOOZLER("Forsooth, A Bamboozler"),
    TARRY_NOT_WITH_FALSERIES("Tarry Not with Falseries"),
    PRITHEE_ABSTAIN_FROM_SHENANIGANS("Prithee, Abstain from Shenanigans"),
    THOU_HAST_BEGUILED_ME("Thou Hast Beguil'd Me!"),
    FIE_ON_THY_TREACHERY("Fie on Thy Treachery!"),
    ART_THOU_A_MOONCALF("Art Thou a Mooncalf?"),
    NICE_TRY("Nice Try!"),
    CAUGHT("CAUGHT");
    //("Steal Stone"),
    //DOUBLE_SCORE("Double Score");

    private final String phrase;

    CheatPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public String toString() {
        return phrase;
    }
}
