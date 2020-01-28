package it.unipr.informatica.modello;

import it.unipr.informatica.esame.SensorManager;

public class RecordLettura implements Lettura {	
	public int tipo;

	public RecordLettura() {}

	public int getTipo() {
		return tipo;
	}

	public int getTmpReading() {
		return SensorManager.readTemperature();
	}	
	
	public int getHumReading() {
		return SensorManager.readHumidity();
	}

}
