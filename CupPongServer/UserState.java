/**
 * Used to determine the player's state to prevent wasting time
 * registering/logging in twice
 */

public enum UserState {
    NOTCONNECTED, LOBBY, GAME
}
