package Model;

public class WeatherInfo {
    double temp;
    int hum;
    double felt;
    double high;
    double low;
    String weatherCon;
    String weatherConDes;
    int pressure;
    //String id;
    public WeatherInfo(){}
    public WeatherInfo(double temp, int hum, double felt, double high, double low, String weatherCon,String weatherConDes,int pressure)
    {
        this.high = high;
        this.hum = hum;
        this.low = low;
        this.pressure = pressure;
        this.temp = temp;
        this.felt = felt;
        this.weatherCon = weatherCon;
        this.weatherConDes = weatherConDes;
    }


    public double getFelt() {
        return felt;
    }

    public double getHigh() {
        return high;
    }

    public int getHum() {
        return hum;
    }

    public double getLow() {
        return low;
    }

    public int getPressure() {
        return pressure;
    }

    public double getTemp() {
        return temp;
    }

    public String getWeatherCon() {
        return weatherCon;
    }

    public String getWeatherConDes() {
        return weatherConDes;
    }

    public void setFelt(double felt) {
        this.felt = felt;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setHum(int hum) {
        this.hum = hum;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setWeatherCon(String weatherCon) {
        this.weatherCon = weatherCon;
    }

    public void setWeatherConDes(String weatherConDes) {
        this.weatherConDes = weatherConDes;
    }
}
