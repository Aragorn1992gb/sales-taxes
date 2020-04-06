package it.gb.salestaxes.test;

import static org.junit.Assert.assertNotNull;

import org.junit.*;

import it.gb.salestaxes.daoImpl.WeatherDAOImpl;


public class WeatherDAOImplTest {
	WeatherDAOImpl weatherDAOImpl = new WeatherDAOImpl(); // Class tested
	
    @Test
    public void canReadFile() {
        assertNotNull("Read ok",weatherDAOImpl.findWeatherData());
    }
}