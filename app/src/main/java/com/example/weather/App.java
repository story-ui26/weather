package com.example.weather;

import java.util.List;

public class App {
    private String time;
    private String date;
    private String xx;
    private String zt;
    private data shuju;
    private forecast prediction;
    private CityInfo csinfo;

    public void setCityInfo(App.CityInfo cityInfo) {
        csinfo = cityInfo;
    }

    public void setForecast(App.forecast forecast) {
        this.prediction = forecast;
    }

    public App.forecast getForecast() {
        return prediction;
    }


    public App.CityInfo getCityInfo() {
        return csinfo;
    }

    public void setData(App.data data) {
        this.shuju = data;
    }

    public App.data getData() {
        return shuju;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.xx = message;
    }

    public void setStatus(String status) {
        this.zt = status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return xx;
    }

    public String getStatus() {
        return zt;
    }

    public static class forecast {
        public String date;
        public String ymd;
        public String zhou;
        public String up;
        public String high;
        public String low;
        public String down;
        public String aqi;
        public String wd;
        public String wp;
        public String type;
        public String notice;

        public void setYmd(String ymd) {
            this.ymd = ymd;
        }

        public void setWeek(String week) {
            this.zhou = week;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setSunset(String sunset) {
            this.down = sunset;
        }

        public void setSunrise(String sunrise) {
            this.up = sunrise;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public void setFx(String fx) {
            this.wd = fx;
        }

        public void setFl(String fl) {
            this.wp = fl;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public String getAqi() {
            return aqi;
        }

        public String getFl() {
            return wp;
        }

        public String getFx() {
            return wd;
        }

        public String getHigh() {
            return high;
        }

        public String getLow() {
            return low;
        }

        public String getSunrise() {
            return up;
        }

        public String getNotice() {
            return notice;
        }

        public String getSunset() {
            return down;
        }

        public String getType() {
            return type;
        }

        public String getWeek() {
            return zhou;
        }

        public String getYmd() {
            return ymd;
        }

    }

    public static class data {
        private String shidu;
        private String pm25;
        private String pm10;
        private String quality;
        private String wendu;
        private String cold;
        public yesterday yesterday;
        private List<forecast> Forecast;

        public void setYesterday(yesterday yesterday) {
            this.yesterday = yesterday;
        }

        public yesterday getYesterday() {
            return yesterday;
        }

        public void setForecast(List<App.forecast> forecast) {
            Forecast = forecast;
        }

        public void setGanmao(String ganmao) {
            this.cold = ganmao;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getShidu() {
            return shidu;
        }

        public String getPm25() {
            return pm25;
        }

        public String getQuality() {
            return quality;
        }

        public String getGanmao() {
            return cold;
        }

        public String getPm10() {
            return pm10;
        }

        public String getWendu() {
            return wendu;
        }

        public List<App.forecast> getForecast() {
            return Forecast;
        }
    }
    public static class yesterday{
        public String riqi;
        public String ymd;
        public String zhou;
        public String up;
        public String high;
        public String low;
        public String down;
        public String aqi;
        public String wd;
        public String wp;
        public String type;
        public String notice;

        public void setDate(String date) {
            this.riqi = date;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public void setFl(String fl) {
            this.wp = fl;
        }

        public void setFx(String fx) {
            this.wd = fx;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public void setSunrise(String sunrise) {
            this.up = sunrise;
        }

        public void setSunset(String sunset) {
            this.down = sunset;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setWeek(String week) {
            this.zhou = week;
        }

        public void setYmd(String ymd) {
            this.ymd = ymd;
        }

        public String getDate() {
            return riqi;
        }

        public String getAqi() {
            return aqi;
        }

        public String getFl() {
            return wp;
        }

        public String getFx() {
            return wd;
        }

        public String getHigh() {
            return high;
        }

        public String getLow() {
            return low;
        }

        public String getSunrise() {
            return up;
        }

        public String getNotice() {
            return notice;
        }

        public String getSunset() {
            return down;
        }

        public String getType() {
            return type;
        }

        public String getWeek() {
            return zhou;
        }

        public String getYmd() {
            return ymd;
        }
    }
    public static class CityInfo{
        private String city;
        private String cityId;
        private String parent;
        private String gengxing;
        public void setCity(String city) {
            this.city = city;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public void setUpdateTime(String updateTime) {
            this.gengxing = updateTime;
        }

        public String getCity() {
            return city;
        }

        public String getCityId() {
            return cityId;
        }

        public String getParent() {
            return parent;
        }

        public String getUpdateTime() {
            return gengxing;
        }
    }
}


