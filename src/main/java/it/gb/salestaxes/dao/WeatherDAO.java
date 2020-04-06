package it.gb.salestaxes.dao;

import java.util.ArrayList;

import it.gb.salestaxes.bean.WeatherBean;

public interface WeatherDAO {
	public abstract ArrayList<WeatherBean> findWeatherData();
}
