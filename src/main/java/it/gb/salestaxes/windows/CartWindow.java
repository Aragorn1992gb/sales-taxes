package it.gb.salestaxes.windows;

import java.util.ArrayList;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.util.GlobalStorage;

public class CartWindow {
	//static ArrayList<Integer> toCart = new ArrayList<>();
	static GlobalStorage gs = GlobalStorage.getInstance();
	public static void cartWindow() throws Exception {
		SecondWindow sw = new SecondWindow();
//		GlobalStorage gs = new GlobalStorage();

		for (Integer prodId : gs.toCart)
			System.out.println(prodId);
	}
}
