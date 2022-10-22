package machine;

import interfaces.GameState;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

public class MachineState {
    protected AtomicInteger coin = new AtomicInteger(0);

    protected int coinsPerPlay = 1;

    public int getCoin() {
        return coin.get();
    }

    public MachineState decrementCoin(int decCoins) {
        int coin = this.coin.updateAndGet((cur) -> cur - decCoins);

        System.out.println(currentThread().getName() + " ### Current coins: " + coin);

        return this;
    }

    public MachineState incrementCoin(int incCoins) {
        int coin = this.coin.updateAndGet((cur) -> cur + incCoins);

        System.out.println(currentThread().getName() + " ### Current coins: " + coin);

        return this;
    }

    public int getCoinsPerPlay() {
        return coinsPerPlay;
    }

    public MachineState setCoinsPerPlay(int coinsPerPlay) {
        this.coinsPerPlay = coinsPerPlay;

        return this;
    }

    public boolean canPlay() {
        return this.getCoin() >= this.getCoinsPerPlay();
    }
}
