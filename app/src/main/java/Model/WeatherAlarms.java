package Model;

public class WeatherAlarms {
    String AlarmTitle;
    String AlarmBody;
    String Sourser;
    public WeatherAlarms(){}
    public WeatherAlarms(String alarmTitle,String alarmBody, String sourser)
    {
        this.AlarmBody = alarmBody;
        this.AlarmTitle = alarmTitle;
        this.Sourser = sourser;
    }

    public String getAlarmBody() {
        return AlarmBody;
    }

    public String getAlarmTitle() {
        return AlarmTitle;
    }

    public String getSourser() {
        return Sourser;
    }

    public void setAlarmBody(String alarmBody) {
        AlarmBody = alarmBody;
    }

    public void setAlarmTitle(String alarmTitle) {
        AlarmTitle = alarmTitle;
    }

    public void setSourser(String sourser) {
        Sourser = sourser;
    }
}
