package it.gb.salestaxes.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.daoImpl.ProductsDAOImpl;

public class GlobalStorage {
	static ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl();

	// single instance
	private static final GlobalStorage instance = new GlobalStorage();
//	private static final ArrayList<Integer> toCart = new ArrayList<>();
	private static final ArrayList<ProductsBean> productsList = new ArrayList<ProductsBean>();
	private static final ArrayList<ProductsBean> productsCheckoutList = new ArrayList<ProductsBean>();

	public static ArrayList<ProductsBean> getProductscheckoutlist() {
		return productsCheckoutList;
	}

	private static final HashMap<Integer, Integer> rowMapIdprod = new HashMap<Integer, Integer>();
	private static final HashMap<Integer, Integer> prodIdMapCounter = new HashMap<Integer, Integer>();
	private static int columnElements = 0;

	private static List<String> lines = new ArrayList<String>();
	// private static Iterator<Entry<Integer,Integer>> itRowMapIdprod =
	// rowMapIdprod.entrySet().iterator();

	public static List<String> getLines() {
		return lines;
	}

	public static void setLines(List<String> lines) {
		GlobalStorage.lines = lines;
	}

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

//	public static ArrayList<Integer> getTocart() {
//		return toCart;
//	}	

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
//	public void addToCart(int idProd) {
//		toCart.add(idProd);
//	}

//	public void removeFromCart(int idProd) {
//		toCart.remove(Integer.valueOf(idProd));
//	}

	public void addToRowMapIdProd(int keyRow, int valueIdProd) {
		rowMapIdprod.put(keyRow, valueIdProd);
	}

	public void addToProdIdMapCounter(int keyIdProd, int valueCounter) {
		prodIdMapCounter.put(keyIdProd, valueCounter);
	}

	public void removeFromProdIdMapCounter(int key) {
		prodIdMapCounter.remove(key);
		// productsCheckoutList.remove(getRowmapidprod().get(key));
		// rowMapIdprod.remove(key);
		// shiftRow(key);
	}

	public Boolean containsKeyProdIdMapCounter(int key) {
		return prodIdMapCounter.containsKey(key);
	}

	public void removeFromRowMapIdprod(int key) {
		rowMapIdprod.remove(key);
	}

	public void shiftRow(Integer removedRow) {
		Iterator<Entry<Integer, Integer>> itRowMapIdprod = rowMapIdprod.entrySet().iterator();
		Integer entryKey = null;
		while (itRowMapIdprod.hasNext()) {
			entryKey = itRowMapIdprod.next().getKey();
			if (entryKey > removedRow) {
				rowMapIdprod.put(entryKey - 1, rowMapIdprod.get(entryKey));
				// rowMapIdprod.remove(entryKey);
			}
		}
		rowMapIdprod.remove(entryKey);
	}

	public ProductsBean getProductById(int productId) {
		for (ProductsBean product : productsList) {
			if (product.getIdProd() == productId) {
				return product;
			}
		}
		return null;
	}

	public void generateProductsCheckoutist() {
		productsCheckoutList.clear();
		Iterator<Entry<Integer, Integer>> itRowMapIdprod = rowMapIdprod.entrySet().iterator();
		// HashMap<Integer, Integer> rowMapId = getRowmapidprod();
		Integer entryKey = null;
		while (itRowMapIdprod.hasNext()) {
			ProductsBean product = new ProductsBean();
			entryKey = itRowMapIdprod.next().getKey();
			product = getProductById(rowMapIdprod.get(entryKey));
			productsCheckoutList.add(product);
		}
	}

	public void addToProductsCheckoutList(ProductsBean product) {
		productsCheckoutList.add(product);
	}

	public void addLine(String line) {
		lines.add(line);
	}

	public void updateUnits(int index, int units) {
		productsList.get(index).setUnits(units);
	}

	public void clearAll() {
		lines.clear();
		prodIdMapCounter.clear();
		productsCheckoutList.clear();
		productsList.clear();
		rowMapIdprod.clear();
	}

	public void initializeProductList() {
		ArrayList<ProductsBean> productsNewList = productsDAOImpl.findProductsData();
		productsList.clear();
		Iterator itProductsNewList = productsNewList.iterator();
		while (itProductsNewList.hasNext()) {
			productsList.add((ProductsBean) itProductsNewList.next());
		}

		// productsList = productsDAOImpl.findProductsData();
	}
}
