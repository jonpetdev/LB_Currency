package jonas.model;

public class Holidays {
    private Integer year;
    private Integer month;
    private Integer day;
    private String holidayName;

    public Holidays(){}

    public Holidays(Integer year, Integer month, Integer day, String holidayName) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.holidayName = holidayName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }
}
