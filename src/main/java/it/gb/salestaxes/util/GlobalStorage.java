package it.gb.salestaxes.util;

import java.util.ArrayList;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.daoImpl.ProductsDAOImpl;

public class GlobalStorage {
	static ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl();
	
	// unic instance
	private static final GlobalStorage instance = new GlobalStorage();
	public static final ArrayList<Integer> toCart = new ArrayList<>();
	public static final ArrayList<ProductsBean> productsList = productsDAOImpl.findProductsData();
	


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
		toCart.remove(idProd);
	}

//	public static void setProductsList(ArrayList<ProductsBean> productsList) {
//		GlobalStorage.productsList = productsDAOImpl.findProductsData();
//	}
	
}
