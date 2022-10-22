package state;

import interfaces.DefaultArcadeGameState;
import interfaces.GameState;
import machine.MachineState;

import java.util.function.Consumer;

public class Idling extends DefaultArcadeGameState {
    public Idling(MachineState machineState, Consumer<GameState> setState) {
        super(machineState, setState);
    }
}
