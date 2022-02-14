package by.issoft.domain.accessory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MyThread extends Thread{
    protected boolean needRun = true;
    protected boolean paused = false;

    public MyThread() {
        super();
    }

    public MyThread(Runnable runnable) {
        super(runnable);
    }

    public void finish() {
        log.info("finished {}", this);
        needRun = false;
    }

    private synchronized void waitUntilResume() throws InterruptedException {
        this.wait();
    }

    protected void checkIfPausedAndWait() throws InterruptedException {
        while (paused) {
            waitUntilResume();
        }
    }

    public void pause() {
        log.info("paused {}", this);
        paused = true;
    }

    public synchronized void continueExecution() {
        log.info("continue execution {}", this);
        paused = false;
        this.notify();
    }
}
