package it.unipr.informatica.esame;

public class SensorManager {	
	public static int readTemperature() {
		return (int)Math.ceil(200 * Math.random()) - 100;
	}
	public static int readHumidity() {
		return (int)Math.ceil(100 * Math.random());		
	}
}