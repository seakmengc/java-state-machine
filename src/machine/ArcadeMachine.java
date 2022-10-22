package machine;

import interfaces.DefaultArcadeGameState;
import interfaces.GameState;
import interfaces.IArcadeGameState;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

public class ArcadeMachine {
    protected MachineState machineState;

    protected List<GameState> states;

    public ArcadeMachine(MachineState machineState) {
        this.machineState = machineState;

        this.states = new ArrayList<>();
        this.states.add(GameState.IDLING);
    }

    public void insertACoin() {
        createStateMachineHandler(0).insertACoin();
    }

    public void play(int player) {
        if(player >= this.states.size()) {
            this.states.add(GameState.IDLING);
        }

        createStateMachineHandler(player).play();
    }

    public void die(int player) {
        createStateMachineHandler(player).die();
    }

    public List<GameState> getStates() {
        return states;
    }

    public Consumer<GameState> setStateConsumer(int player) {
        return (GameState gameState) -> {
            this.states.set(player, gameState);
            System.out.println(currentThread().getName() + " ### Player " + (player + 1) + "'s State: " + gameState.name());
        };
    }

    private IArcadeGameState createStateMachineHandler(int player) {
        try {
            Class<DefaultArcadeGameState> stateClass = this.states.get(player).getStateClass();

            Constructor<DefaultArcadeGameState> constructor
                    = (Constructor<DefaultArcadeGameState>) stateClass.getConstructors()[0];

            return constructor.newInstance(this.machineState, this.setStateConsumer(player));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
