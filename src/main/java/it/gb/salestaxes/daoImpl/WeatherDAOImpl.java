package it.gb.salestaxes.daoImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import it.gb.salestaxes.bean.WeatherBean;
import it.gb.salestaxes.dao.WeatherDAO;

public class WeatherDAOImpl implements WeatherDAO{

	@Override
	public ArrayList<WeatherBean> findWeatherData() {
		String[] lines  = null;
		String filename = "weather.dat";
		try {
			// Get Resources path
			ClassLoader classL = new WeatherDAOImpl().getClass().getClassLoader();
			// Get rows of the file
			lines = Files.readAllLines(new File(classL.getResource(filename).getFile()).toPath()).toArray(new String[0]);

			//			lines = Files.readAllLines(new File(path+"/"+UtilConstants.RESOURCES+"/"+filename).toPath()).toArray(new String[0]);
		} catch (IOException e) {
			System.out.println("Error in read file operation");
			e.printStackTrace();
		}
		
		ArrayList<WeatherBean> wbList = new ArrayList();
		String[] column = null;
		
		for(int i=1; i<lines.length-1; i++) {
			if(!lines[i].contentEquals("")) {
			
				WeatherBean wbObj = new WeatherBean();
				column = lines[i].trim().split("\\s+");
				/*
				 * Set bean attributes respecting type: it is important to delete 
				 * impurities that damage the consistency of the data: regular expression
				 * in "replaceAll" method allows to get only numeric (and .) values to parse in Int
				 */
				wbObj.setDy(Integer.parseInt(column[0].replaceAll("[^\\d.]", "")));
				wbObj.setMxT(Integer.parseInt(column[1].replaceAll("[^\\d.]", "")));
				wbObj.setMnT(Integer.parseInt(column[2].replaceAll("[^\\d.]", "")));
				wbList.add(wbObj);
			}
		}
		return wbList;
	}

}
