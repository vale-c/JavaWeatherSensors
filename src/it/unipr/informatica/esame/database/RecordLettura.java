package it.unipr.informatica.esame.database;

import it.unipr.informatica.esame.SensorManager;
import it.unipr.informatica.esame.modello.Lettura;

public class RecordLettura implements Lettura {	
	int id;
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
