package it.gb.salestaxes.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.*;

import it.gb.salestaxes.bean.FootballBean;
import it.gb.salestaxes.bean.WeatherBean;
import it.gb.salestaxes.windows.SecondWindow;


public class SecondWindowTest {
	SecondWindow secondWindow = new SecondWindow(); // Class tested
	
//    @Test
//    public void getDayMinTR() {
//    	ArrayList<WeatherBean> wbList = new ArrayList();
//    	WeatherBean wb = new WeatherBean();
//    	
//    	wb.setDy(1);
//    	wb.setMxT(20);
//    	wb.setMnT(10);
//    	wbList.add(0, wb);
//    	
//    	wb.setDy(2);
//    	wb.setMxT(20);
//    	wb.setMnT(15);
//    	wbList.add(1, wb);
//    	
//        assertEquals(2, secondWindow.getDayMinTR(wbList), "Must be the minimum range");
//    }
//    
//    @Test
//    public void getTeamMinGD() {
//    	ArrayList<FootballBean> fbList = new ArrayList();
//    	FootballBean fb = new FootballBean();
//    	
//    	fb.setTeam("Juventus");
//    	fb.setF(60);
//    	fb.setA(22);
//    	fbList.add(0, fb);
//    	
//    	fb.setTeam("Lecce");
//    	fb.setF(45);
//    	fb.setA(32);
//    	fbList.add(1, fb);
//    	
//        assertEquals("Lecce", secondWindow.getTeamMinGD(fbList), "Must be the minimum goal difference");
//    }
}