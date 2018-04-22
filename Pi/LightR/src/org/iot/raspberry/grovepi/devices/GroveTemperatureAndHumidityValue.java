package org.iot.raspberry.grovepi.devices;

public class GroveTemperatureAndHumidityValue {

  private final double temperature;
  //private final double humidity;

  public GroveTemperatureAndHumidityValue(double temperature) {
    this.temperature = temperature;
    //this.humidity = humidity;
  }

  public double getTemperature() {
    return temperature;
  }


  @Override
  public String toString() {
    double values = temperature;
    
    String temp = String.valueOf(values);
    
    return temp;
  }

}
