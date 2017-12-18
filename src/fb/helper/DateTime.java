package fb.helper;

/**
 *
 * @author hassan
 */
public class DateTime {

    Date d;
    Time t;

    public DateTime(Date d, Time t) {
        this.d = d;
        this.t = t;
    }

    public DateTime() {
        d = new Date(1, 1, 2017);
        t = new Time(0,0,0);
    }

    public Date getDate() {
        return d;
    }

    public void setDate(Date d) {
        this.d = d;
    }

    public Time getTime() {
        return t;
    }

    public void setTime(Time t) {
        this.t = t;
    }

    @Override
    public String toString() {
        // yet to be impletmented
        return "DateTime{" + "d=" + d + ", t=" + t + '}';
    }
    

    
    
}
