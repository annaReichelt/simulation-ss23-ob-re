package src.RouteData;

public class Week {
    
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    public Week(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public Week(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {
        this.monday = monday.replace("\"", "").equals("1");
        this.tuesday = tuesday.replace("\"", "").equals("1");
        this.wednesday = wednesday.replace("\"", "").equals("1");
        this.thursday = thursday.replace("\"", "").equals("1");
        this.friday = friday.replace("\"", "").equals("1");
        this.saturday = saturday.replace("\"", "").equals("1");
        this.sunday = sunday.replace("\"", "").equals("1");
    }

    public boolean getMonday() {
        return monday;
    }

    public boolean getTuesday() {
        return tuesday;
    }

    public boolean getWednesday() {
        return wednesday;
    }

    public boolean getThursday() {
        return thursday;
    }

    public boolean getFriday() {
        return friday;
    }   

    public boolean getSaturday() {
        return saturday;
    }

    public boolean getSunday() {
        return sunday;
    }

    public boolean isActive(Time time) {
        int hour = time.getHour();
        int day = (int)Math.floor(hour / 24);
        return isActive(day);
    }

    public boolean isActive(int day) {
        day = day % 7;
        switch(day) {
            case 0:
                return monday;
            case 1:
                return tuesday;
            case 2:
                return wednesday;
            case 3:
                return thursday;
            case 4:
                return friday;
            case 5:
                return saturday;
            case 6:
                return sunday;
            default:
                return false;
        }
    } 

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(monday) {
            sb.append("Monday ");
        }
        if(tuesday) {
            sb.append("Tuesday ");
        }
        if(wednesday) {
            sb.append("Wednesday ");
        }
        if(thursday) {
            sb.append("Thursday ");
        }
        if(friday) {
            sb.append("Friday ");
        }
        if(saturday) {
            sb.append("Saturday ");
        }
        if(sunday) {
            sb.append("Sunday ");
        }
        return sb.toString();
    }

}
