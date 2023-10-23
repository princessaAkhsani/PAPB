package zfr.mobile.slotgacor;

public class Wheel extends Thread {

    public int getCurrentIndex() {
        return currentIndex;
    }

    interface WheelListener {
        void newImage(int img);
    }

    private static int[] imgs = {R.drawable.slot_1, R.drawable.slot_2, R.drawable.slot_3, R.drawable.slot_4,
            R.drawable.slot_5, R.drawable.slot_6};
    public int currentIndex;
    private WheelListener wheelListener;
    private long frameDuration;
    private long startIn;
    private boolean isStarted;
    private boolean shouldStop;

    public Wheel(WheelListener wheelListener, long frameDuration, long startIn) {
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.startIn = startIn;
        currentIndex = 0;
        isStarted = false;
        shouldStop = false;
    }

    public void nextImg() {
        currentIndex++;

        if (currentIndex == imgs.length) {
            currentIndex = 0;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(startIn);
        } catch (InterruptedException e) {
        }

        isStarted = true;

        while (isStarted) {
            try {
                Thread.sleep(frameDuration);
            } catch (InterruptedException e) {
            }

            nextImg();

            if (wheelListener != null) {
                wheelListener.newImage(imgs[currentIndex]);
            }

            if (shouldStop) {
                isStarted = false;
            }
        }
    }

    public void stopWheel() {
        shouldStop = true;
    }

    public boolean isRunning() {
        return isStarted;
    }
}
