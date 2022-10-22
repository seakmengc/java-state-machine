package state;

import interfaces.DefaultArcadeGameState;
import interfaces.GameState;
import machine.MachineState;

import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

public class Playing extends DefaultArcadeGameState {
    public Playing(MachineState machineState, Consumer<GameState> setState) {
        super(machineState, setState);
    }

    @Override
    public void play() {
        if(this.machineState.getCoin() < this.machineState.getCoinsPerPlay()) {
            System.out.println(currentThread().getName() + " ----------------");
            System.out.println(currentThread().getName() + " Not enough coin!");
            System.out.println(currentThread().getName() + " ----------------");
            return;
        }

        this.machineState.decrementCoin(this.machineState.getCoinsPerPlay());
        this.setState.accept(GameState.PLAYING);
        this.simulatePlaying();
    }

    public void simulatePlaying() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (Math.random() > 0.8) {
                break;
            }
        }

        this.die();
    }

    @Override
    public void die() {
        this.setState.accept(GameState.RETRYING);

        new Retrying(this.machineState, this.setState).runCountDown();
    }
}
