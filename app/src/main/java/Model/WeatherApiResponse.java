package Model;

public class WeatherApiResponse {
    double temp;
    int hum;
    double felt;
    double high;
    double low;
    String weatherCon;
    String weatherConDes;
    int pressure;
    String id;
    WeatherInfo weatherInfo;
    public WeatherInfo getWeatherInfo()
    {
        if (weatherInfo != null) {
            return new WeatherInfo(temp, hum, felt, high, low, weatherCon, weatherConDes, pressure);
        }
        else return weatherInfo;
    }
    public void setWeatherInfo( WeatherInfo weatherInfo)
    {
        this.weatherInfo = weatherInfo;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setWeatherConDes(String weatherConDes) {
        this.weatherConDes = weatherConDes;
    }

    public void setWeatherCon(String weatherCon) {
        this.weatherCon = weatherCon;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setHum(int hum) {
        this.hum = hum;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setFelt(double felt) {
        this.felt = felt;
    }
}

