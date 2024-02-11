package com.instance.mancala2;

public enum ActionType {
    PLACE_STONE,
    GRAB_STONES,
    CAPTURE_STONES,
    STORE_STONE,
    CAST_SUMMON,
    SUMMON_STONE,
    ACCUSE,
    GET_ACCUSED,
    PENALIZED,
    UNSLEEVE_STONE,
    CHEAT_ACTION,
    END_TURN
}
//summon stone should have a cast action, that would give an extra stone on the next turn to user hand, a cheat on the next turn, unless pocketed
