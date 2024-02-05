package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class StatisticsDisplay implements Observer, DisplayElement{
        private float avg_Temperature;
        private float avg_Humidity;
        private float avg_Pressure;
        private Subject weatherData;
        private ArrayList<Float> temperatureList = new ArrayList<>();
        private ArrayList<Float> humidityList = new ArrayList<>();
        private ArrayList<Float> pressureList = new ArrayList<>();

    public StatisticsDisplay(Subject weatherData) {
            this.weatherData = weatherData;
        }

        @Override
        public String display() {
            String html = "";
            html += String.format("<div style=\"background-image: " +
                    "url(/images/sky.webp); " +
                    "height: 400px; " +
                    "width: 647.2px;" +
                    "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                    "\">");
            html += "<section>";
            html += String.format("<label>Avg: %s</label><br />", avg_Temperature);
            html += String.format("<label>Humidity: %s</label><br />", avg_Humidity);
            html += String.format("<label>Pressure: %s</label>", avg_Pressure);
            html += "</section>";
            html += "</div>";
            return html;
        }

        @Override
        public String name() {
            return "Statistics Display";
        }

        @Override
        public String id() {
            return "statistics";
        }

        //caluate the average of the temperature, humidity and pressure
        @Override
        public void update(float temperature, float humidity, float pressure) {
            temperatureList.add(temperature);
            humidityList.add(humidity);
            pressureList.add(pressure);
            float sum_Temperature = 0;
            float sum_Humidity = 0;
            float sum_Pressure = 0;
            for (float temp : temperatureList) {
                sum_Temperature += temp;
            }
            for (float hum : humidityList) {
                sum_Humidity += hum;
            }
            for (float pres : pressureList) {
                sum_Pressure += pres;
            }
            avg_Temperature = sum_Temperature / temperatureList.size();
            avg_Humidity = sum_Humidity / humidityList.size();
            avg_Pressure = sum_Pressure / pressureList.size();
        }

        public void subscribe() {
            weatherData.registerObserver(this);
        }

        public void unsubscribe() {
            weatherData.removeObserver(this);
        }
}
