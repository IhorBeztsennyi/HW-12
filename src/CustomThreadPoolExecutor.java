import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@interface Repeat {
    int value();
}

class CustomThreadPoolExecutor extends ThreadPoolExecutor {
    public CustomThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @Override
    public void execute(Runnable command) {
        Class<? extends Runnable> runnableClass = command.getClass();
        Repeat numberOfRepetition = runnableClass.getAnnotation(Repeat.class);
        for (int i = 0; i < numberOfRepetition.value(); i++) {
            super.execute(command);
        }
    }
}