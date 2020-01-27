package it.unipr.informatica.esame;
/*
 * Class for a simple computer based weather station that reports
 * temperature (in Celsius) and Humidity values. 
 * The station is attached to a pair of sensors that reports the temperature.
 *
 * This class is implements Runnable so that it can be embedded in a Thread
 * which runs the periodic sensing.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import it.unipr.informatica.activity.Active;
import it.unipr.informatica.esame.database.RecordLettura;

public class WeatherStation extends Active implements Runnable {
	int tmpReading;  // actual tmpSensor reading
    int humReading;  // actual humSensor reading
    
    RecordLettura lettura; // lettura
    
	WeatherStation(int size) {
		super(size);
		lettura = new RecordLettura();
	}

    ResourceBundle bundle = ResourceBundle.getBundle("it.unipr.informatica.esame.configuration");
    
    String it = bundle.getString("periodo_temperatura"); // periodo sensore temperatura
    String iu = bundle.getString("periodo_umidita"); // periodo sensore temperatura

    int intervalloTemp = Integer.parseInt(it); 
    int intervalloHum = Integer.parseInt(iu);
    
    protected String databaseURL;

  @Override
	public synchronized void run() { 
    while(true) {
            try{
                Thread.sleep(intervalloTemp);
            } catch (Exception e) {}    // ignore exceptions

            tmpReading = lettura.getTmpReading();
            System.out.printf("Temperature is: %d°C %n", tmpReading);
            
            try{
                Thread.sleep(intervalloHum);
            } catch (Exception e) {}    // ignore exceptions
            
            humReading = lettura.getHumReading();
            System.out.printf("Humidity is: %d %n", humReading);           
            
            try {	
							String databaseDriver = bundle.getString("database.driver");

							Class.forName(databaseDriver);

							databaseURL = bundle.getString("database.url");

						} catch (Throwable throwable) {
							throwable.printStackTrace();
						}
				try(
				Connection connection = DriverManager.getConnection(databaseURL);
				PreparedStatement statement = connection.prepareStatement("INSERT INTO LETTURA(TIPO,VALORE) VALUES (0, ?) ", Statement.RETURN_GENERATED_KEYS);
			){

			 statement.setInt(1, lettura.tipo); // SET TIPO
			 statement.setDouble(1, tmpReading); // TEMPERATURE READING
		
				statement.executeUpdate();

			try (
					ResultSet resultSet = statement.getGeneratedKeys();
				) {		
					resultSet.next();
					
				resultSet.getInt(1);
					
				} catch (SQLException exception) {
					exception.printStackTrace();
					throw exception;
				}
			} catch (SQLException exception) {
				//VUOTO
			}		  

			try(
				Connection connection = DriverManager.getConnection(databaseURL);
				PreparedStatement statement = connection.prepareStatement("INSERT INTO LETTURA(TIPO,VALORE) VALUES (1, ?) ", Statement.RETURN_GENERATED_KEYS);
			){		 
				 statement.setInt(1, lettura.tipo); // SET TIPO
				 statement.setDouble(1, humReading); // HUMIDITY READING

				statement.executeUpdate();	 

			try (
					ResultSet resultSet = statement.getGeneratedKeys();
				) {		
					resultSet.next();
						
				resultSet.getInt(1);
				
				} catch (SQLException exception) {
					exception.printStackTrace();
					throw exception;
				}
			} catch (SQLException exception) {
				//VUOTO
	
			}
    }	 
	}

    public static void main(String[] args) { 
	
		WeatherStation ws = new WeatherStation(10);
		
		for (int i=1; i < 11; i++) {
			Thread tmpThread = new Thread(ws);      
			tmpThread.start();
			System.out.println("Iniziato Thread # " + i);
		}
	}
}
    


        
        
