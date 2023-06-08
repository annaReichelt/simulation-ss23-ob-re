package src.RouteData;

public class Time implements Comparable<Time>{
    
    public int hour;
    public int minute;
    public int second;

    public Time(String time) {
        time = time.replace("\"", "");
        String[] timeArray = time.split(":");
        hour = Integer.parseInt(timeArray[0]);
        minute = Integer.parseInt(timeArray[1]);
        second = Integer.parseInt(timeArray[2]);
    }

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int compareTo(Time other) {
        if (this.hour < other.hour) {
            return -1;
        } else if (this.hour > other.hour) {
            return 1;
        } else {
            if (this.minute < other.minute) {
                return -1;
            } else if (this.minute > other.minute) {
                return 1;
            } else {
                if (this.second < other.second) {
                    return -1;
                } else if (this.second > other.second) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public String toString() {
        return hour + ":" + minute + ":" + second;
    }

}
