package it.gb.salestaxes.daoImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import it.gb.salestaxes.bean.FootballBean;
import it.gb.salestaxes.dao.FootballDAO;

public class FootballDAOImpl implements FootballDAO{

	@Override
	public ArrayList<FootballBean> findFootballData() {
		String[] lines  = null;
		String filename = "football.dat";
		try {
			// Get Resources path
			ClassLoader classL = new WeatherDAOImpl().getClass().getClassLoader();
			// Get rows of the file
			lines = Files.readAllLines(new File(classL.getResource(filename).getFile()).toPath()).toArray(new String[0]);
		} catch (IOException e) {
			System.out.println("Error in read file operation");
			e.printStackTrace();
		}
		
		ArrayList<FootballBean> fbList = new ArrayList();
		String[] column = null;
		
		for(int i=1; i<lines.length; i++) {
			if(!lines[i].contentEquals("") && !lines[i].contains("--")) {
			
				FootballBean fbObj = new FootballBean();
				column = lines[i].trim().split("\\s+");
				fbObj.setTeam(column[1]);
				fbObj.setF(Integer.parseInt(column[6].replaceAll("[^\\d.]", "")));
				fbObj.setA(Integer.parseInt(column[8].replaceAll("[^\\d.]", "")));
				fbList.add(fbObj);
			}
		}
		return fbList;
	}

}
