package interfaces;

import machine.MachineState;
import state.Playing;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultArcadeGameState implements IArcadeGameState {
    protected MachineState machineState;

    protected Consumer<GameState> setState;

    public DefaultArcadeGameState(MachineState machineState, Consumer<GameState> setState) {
        this.machineState = machineState;
        this.setState = setState;
    }

    @Override
    public void insertACoin() {
        this.machineState.incrementCoin(1);
    }

    public void play() {
        new Playing(this.machineState, this.setState).play();
    }

    @Override
    public void die() {
        throw new IllegalStateException();
    }
}
