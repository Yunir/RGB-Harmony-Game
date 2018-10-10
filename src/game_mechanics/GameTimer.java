package game_mechanics;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameTimer extends AnimationTimer {

    private Label vTimer;
    private long timestamp;
    private long time = 0;
    private long fraction = 0;
    private boolean isStarted;

    public GameTimer(Label vTimer) {
        this.vTimer = vTimer;
    }

    @Override
    public void start() {
        // current time adjusted by remaining time from last run
        timestamp = System.currentTimeMillis() - fraction;
        isStarted = true;
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        // save leftover time not handled with the last update
        fraction = System.currentTimeMillis() - timestamp;
        isStarted = false;
    }

    @Override
    public void handle(long now) {

        long newTime = System.currentTimeMillis();
        if (timestamp + 1000 <= newTime) {
            long deltaT = (newTime - timestamp) / 1000;
            time += deltaT;
            timestamp += 1000 * deltaT;
            vTimer.setText(getFormattedTime(time));
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    private String getFormattedTime(long time) {
        int hours, minutes, seconds;
        hours   = (int) (time / 3600);
        minutes = (int) ((time % 3600) / 60);
        seconds = (int) (time % 60);
        if (hours > 0)
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return String.format("%02d:%02d", minutes, seconds);
    }
}
