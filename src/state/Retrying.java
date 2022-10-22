package state;

import interfaces.DefaultArcadeGameState;
import interfaces.GameState;
import machine.MachineState;

import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

public class Retrying extends DefaultArcadeGameState {
    //seconds to retry, reset every coin inserted
    protected int counter = 3;

    public Retrying(MachineState machineState, Consumer<GameState> setState) {
        super(machineState, setState);
    }

    public void runCountDown() {
        while(this.counter > 0) {
            if(this.machineState.canPlay()) {
                System.out.println(currentThread().getName() + " Playing again");
                new Playing(machineState, this.setState).play();
                return;
            }

            this.counter--;
            System.out.println(currentThread().getName() + " Waiting for coin insertion...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        this.setState.accept(GameState.IDLING);
        System.out.println(currentThread().getName() + " See you later!");
    }

    @Override
    public void insertACoin() {
        super.insertACoin();
        this.counter = 3;
    }
}
