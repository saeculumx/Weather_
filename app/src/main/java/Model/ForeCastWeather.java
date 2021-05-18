package Model;

import View.ForeCastFragment;

public class ForeCastWeather {
    double high;
    double low;
    int dt;
    public ForeCastWeather(Double high, Double low)
    {
        this.high = high;
        this.low = low;
    }
    public ForeCastWeather(Double high, Double low,int dt)
    {
        this.high = high;
        this.low = low;
        this.dt = dt;
    }
    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }
    public double getCHigh()
    {
        return (high-273.15);
    }
    public double getCLow()
    {
        return (low-273.15);
    }
}
