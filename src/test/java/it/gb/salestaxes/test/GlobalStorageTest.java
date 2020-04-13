package it.gb.salestaxes.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.daoImpl.ProductsDAOImpl;
import it.gb.salestaxes.util.GlobalStorage;

public class GlobalStorageTest {
	private static GlobalStorage gs = GlobalStorage.getInstance(); // Class tested
	private static ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl();
	private static ArrayList<ProductsBean> productsList = productsDAOImpl.findProductsData();
	static int idItemToAdd = 5;
	int listSize = 30;
	// get size of getToChart array after @BeforeAll
//	int startingSize = gs.getTocart().size();
//
//	@BeforeAll
//	public static void initialize() {
//		gs.addToCart(idItemToAdd);
//		gs.addToRowMapIdProd(1, productsList.get(0).getIdProd());
//		System.out.println("@BeforeEach initializing");
//	}
//	
//	@Test
//	public void canAddToCart() {
//		assertEquals(gs.getTocart().size(), startingSize);
//		System.out.println("@Test that something is added to cart");
//	}
//	
//	@Test
//	public void canReadCart() {
//		assertTrue(gs.getTocart().contains(idItemToAdd));
//		System.out.println("@Test verify that cart contains added product id");
//	}
	
	@Test
	public void canCheckProductsListElements() {
		assertEquals(gs.getProductslist().size(),listSize);
		System.out.println("@Test can get product list elements number");
	}
	
//	@Test
//	public void canRemoveFromCart() {
//		gs.removeFromCart(idItemToAdd);
//		assertEquals(gs.getTocart().size(), startingSize-1);
//		System.out.println("@Test can remove an element of toChar array");
//	}
	
	@Test
	public void canCheckRowMapIdProd() {
		assertEquals(gs.getRowmapidprod().get(1),productsList.get(0).getIdProd());
		assertNotEquals(gs.getRowmapidprod().get(1),productsList.get(0).getIdProd()+1);
		System.out.println("@Test verify that hashmap global variable map row with his idProduct");
	}

}
