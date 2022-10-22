import machine.ArcadeMachine;
import machine.MachineState;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ArcadeMachine arcadeMachine = new ArcadeMachine(
                new MachineState()
        );

        for (int i = 0; i < 2; i++) {
            arcadeMachine.insertACoin();
        }

        List<Callable<Integer>> callables = Arrays.asList(
                () -> {
                    arcadeMachine.play(0);
                    return 0;
                },
                () -> {
                    arcadeMachine.play(1);
                    return 1;
                },
                () -> {
                    System.out.println("Insert a coin");
                    arcadeMachine.insertACoin();

                    return 2;
                }
        );
        ExecutorService executorService = Executors.newWorkStealingPool(3);
        executorService.invokeAll(callables).stream().map(future -> {
            try {
                return future.get();
            }
            catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).collect(Collectors.toList());

        System.out.println("Last states: " + arcadeMachine.getStates());
    }
}