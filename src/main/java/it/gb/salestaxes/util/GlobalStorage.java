package it.gb.salestaxes.util;

import java.util.ArrayList;
import java.util.HashMap;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.daoImpl.ProductsDAOImpl;

public class GlobalStorage {
	static ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl();
	
	// single instance
	private static final GlobalStorage instance = new GlobalStorage();
	private static final ArrayList<Integer> toCart = new ArrayList<>();
	private static final ArrayList<ProductsBean> productsList = productsDAOImpl.findProductsData();
	private static final HashMap<Integer, Integer> rowMapIdprod = new HashMap<Integer,Integer>();
	private static final HashMap<Integer, Integer> prodIdMapCounter = new HashMap<Integer,Integer>();
	private static int columnElements = 0;

	public static HashMap<Integer, Integer> getProdidmapcounter() {
		return prodIdMapCounter;
	}
	
	
	public static int getColumnElements() {
		return columnElements;
	}

	public static void setColumnElements(int columnElements) {
		GlobalStorage.columnElements = columnElements;
	}

	public static HashMap<Integer, Integer> getRowmapidprod() {
		return rowMapIdprod;
	}

	public static ArrayList<Integer> getTocart() {
		return toCart;
	}	

	public static ArrayList<ProductsBean> getProductslist() {
		return productsList;
	}

	// private constructor
	private GlobalStorage() {
	}
	
	// retrieve instance
	public static GlobalStorage getInstance() {
		return instance;
	}
	
	// additive methods
	public void addToCart(int idProd) {
		toCart.add(idProd);
	}
	
	public void removeFromCart(int idProd) {
		toCart.remove(Integer.valueOf(idProd));
	}
	
	public void addToRowMapIdProd(int keyRow, int valueIdProd) {
		rowMapIdprod.put(keyRow, valueIdProd);
	}
	
	public void addToProdIdMapCounter(int keyIdProd, int valueCounter) {
		prodIdMapCounter.put(keyIdProd, valueCounter);
	}
	
	public void removeFromProdIdMapCounter(int key) {
		prodIdMapCounter.remove(key);
	}
}
