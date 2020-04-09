package it.gb.salestaxes.util;

import java.util.HashMap;

import javax.swing.ImageIcon;

public final class UtilConstants {
	public static final String PRODUCTS_FILENAME = "products.csv";
	public static final String PRODUCTS_FILE_CHARSET = "UTF-8";
	public static final char PRODUCTS_FILE_DELIMITER = ';';
	
	public static final String CART_ICO = "cart21.jpg";
	public static final String REMOVE_ICO = "delete21.png";
	
	// mapping column to show with his type
	public static HashMap<Integer, Class> columnTypes = new HashMap<Integer, Class>() {
		{
			put(0,String.class);
			put(1,String.class);
			put(2,Integer.class);
			put(3,Float.class);
			put(4,String.class);
			put(5,ImageIcon.class);
		}
	};
	
	public static int OTHER_COLUMNS = 1;
	
	
	// mapping column to show with his type
	public static HashMap<Integer, Class> columnTypesCart = new HashMap<Integer, Class>() {
		{
			put(0,String.class);
			put(1,String.class);
			put(2,Integer.class);
			put(3,Float.class);
			put(4,String.class);
			put(5,String.class);
			put(6,String.class);
			put(7,Integer.class);
			put(8,ImageIcon.class);
		}
	};
	
	public static int OTHER_COLUMNS_CART = 1;
	
}
