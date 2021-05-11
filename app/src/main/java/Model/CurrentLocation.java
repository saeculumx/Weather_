package Model;

public class CurrentLocation {
    float lat;
    float lon;
    String cityName;
    String disName;
    int id;
    public CurrentLocation(){}
    public CurrentLocation(float lat, float lon, String cityName, String disName, int id) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.cityName = cityName;
        this.disName = disName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDisName() {
        return disName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
