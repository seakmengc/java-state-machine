package interfaces;

import state.Idling;
import state.Playing;
import state.Retrying;

public enum GameState {
    IDLING(Idling.class),
    PLAYING(Playing.class),
    RETRYING(Retrying.class);


    Class<?> stateClass;

    GameState(Class<?> stateClass) {
        this.stateClass = stateClass;
    }

    public Class<DefaultArcadeGameState> getStateClass() {
        return (Class<DefaultArcadeGameState>) this.stateClass;
    }
}
