package mineGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {

    private Timer timer;
    private int countdownTime; // 카운트다운 시간 (초 단위)
    private boolean isTimeExceeded;

    public GameTimer(int countdownTimeInSeconds) {
        this.countdownTime = countdownTimeInSeconds;
        this.isTimeExceeded = false;
    }

    public void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            private int timeRemaining = countdownTime;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining > 0) {
                    System.out.println("Time remaining: " + timeRemaining + " seconds");
                    timeRemaining--;
                } else {
                    timer.stop();
                    isTimeExceeded = true;
                    onTimerExceeded();
                }
            }
        });
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public boolean isTimeWithinLimit() {
        return !isTimeExceeded;
    }

    private void onTimerExceeded() {
        // 타이머가 초과되었을 때 실행할 메서드
        System.out.println("Timer exceeded!");
        performActionOnTimerExceeded();
    }

    private void performActionOnTimerExceeded() {
        // 타이머가 초과되었을 때 수행할 작업을 여기에 작성
        System.out.println("Performing action because timer has exceeded.");
    }

    public boolean isRunning() {
        return timer.isRunning();
    }
}
