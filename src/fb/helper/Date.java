/*
 * Made By : Hassan Nawaz
 * All Rights Reserved
 */
package fb.helper;

/**
 *
 * @author hassan
 */
public class Date {

    private int day;
    private int month;
    private int year;
    private final int DaySet[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Date() {
        day = 1;
        month = 1;
        year = 2017;
    }

    public Date(int day, int month, int year) {
        setDate(day, month, year);
    }

    public final void setDate(int day, int month, int year) {
        setDay(day);
        setMonth(month);
        setYear(year);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (getMonthMaxDays() >= day && day > 0) {
            this.day = day;
        } else {
            this.day = 1;
        }
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {

        if (month >= 1 && month <= 12) {
            this.month = month;
            setDay(day);
        } else {
            this.month = 1;
        }

        
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        if (year >= 1800 && year <= 2050) {
            this.year = year;
        } else {
            this.year = 2017;
        }

    }

    @Override
    public String toString() {
        return "Date{" + "day=" + day + ", month=" + month + ", year=" + year + '}';
    }

    private int getMonthMaxDays() {
        if((year % 2) == 0 && month == 2) {
            return 29;
        } else {
            return DaySet[month];
        }
        
    }

}
