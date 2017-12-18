/*
 * Made By : Hassan Nawaz
 * All Rights Reserved
 */
package fb.helper;

/**
 *
 * @author hassan
 */
public class Time {

    private int hour;
    private int minute;
    private int second;

    public Time() {
        this(0, 0, 0);
    }

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        //this.hour = hour;
        if (hour >= 0 && hour < 24) {
            this.hour = hour;
        } else {
            this.hour = 0;
        }
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        if (minute >= 0 && minute < 60) {
            this.minute = minute;
        } else {
            this.minute = 0;
        }
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        if ( second >= 0 && second < 60) {
            this.second = second;
        } else {
            this.second = 0;
        }
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

}
