package it.gb.salestaxes.util;

import java.util.HashMap;

import javax.swing.ImageIcon;

public final class UtilConstants {
	public static final String PRODUCTS_FILENAME = "products.csv";
	public static final String PRODUCTS_FILE_CHARSET = "UTF-8";
	public static final char PRODUCTS_FILE_DELIMITER = ';';
	
	public static final String CART_ICO = "cart21.jpg";
	public static final String REMOVE_ICO = "delete21.png";
	public static final String DESCRIPTION_ICO = "lente21.png";
	
	public static final String NATION = "Italy";
	
	public static final String OK = "Your order has confirmed";
	public static final String KO = "There are some problems with your order. Please try again";
	
	// mapping column to show with his type
	public static HashMap<Integer, Class> columnTypes = new HashMap<Integer, Class>() {
		{
			put(0,String.class);
			put(1,String.class);
			put(2,Integer.class);
			put(3,Double.class);
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
			put(2,String.class);
			put(3,Integer.class);
			put(4,String.class);
			put(5,Integer.class);
			put(6,Double.class);
			put(7,String.class);
			put(8,ImageIcon.class);
			put(9,ImageIcon.class);
		}
	};
	
	// mapping column to show with his type
	public static HashMap<Integer, Class> columnTypesCheckout = new HashMap<Integer, Class>() {
		{
			put(0,String.class);
			put(1,Double.class);
			put(2,String.class);
			put(3,String.class);
			put(4,String.class);
		}
	};
	
	public static int OTHER_COLUMNS_CART = 1;
	
}
