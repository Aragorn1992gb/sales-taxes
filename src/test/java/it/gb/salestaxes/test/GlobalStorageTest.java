package it.gb.salestaxes.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.util.GlobalStorage;

public class GlobalStorageTest {
	private static GlobalStorage gs = GlobalStorage.getInstance(); // Class tested
	static int idItemToAdd = 5;
	int listSize = 30;
	static int row = 1;
	static int index = 0;
	static int counter = 15;
	static int rowToRemove = 4;
	static int elementToShift = 4;
	static int elementToRemove = 3;

	@BeforeAll
	public static void initialize() {
		gs.initializeProductList();
		gs.addToRowMapIdProd(row, gs.getProductslist().get(index).getIdProd());
		gs.addToRowMapIdProd(2, gs.getProductslist().get(1).getIdProd());
		gs.addToRowMapIdProd(3, gs.getProductslist().get(2).getIdProd());
		gs.addToRowMapIdProd(rowToRemove, gs.getProductslist().get(elementToRemove).getIdProd());
		gs.addToRowMapIdProd(5, gs.getProductslist().get(elementToShift).getIdProd());
		
		gs.addToProdIdMapCounter(idItemToAdd, counter);
		System.out.println("@BeforeEach initializing");
	}

	
	@Test
	public void canCheckProductsListElements() {
		assertEquals(gs.getProductslist().size(),listSize);
		System.out.println("@Test can get product list elements number");
	}
	
	@Test
	public void canCheckRowMapIdProd() {
		assertEquals(gs.getRowmapidprod().get(row),gs.getProductslist().get(index).getIdProd());
		assertNotEquals(gs.getRowmapidprod().get(row),gs.getProductslist().get(index).getIdProd()+1);
		System.out.println("@Test verify that hashmap global variable map row with his idProduct");
	}
	
	@Test
	public void canCheckProdIdMapCounterd() {
		assertEquals(gs.getProdidmapcounter().get(idItemToAdd),counter);
		assertNotEquals(gs.getRowmapidprod().get(idItemToAdd),0);
	}
	
	@Test
	public void canContainsKeyProdIdMapCounter() {
		assertTrue(gs.containsKeyProdIdMapCounter(idItemToAdd));
	}
	
	@Test
	public void canRemoveFromProdIdMapCounter() {
		gs.removeFromProdIdMapCounter(idItemToAdd);
		assertNull(gs.getProdidmapcounter().get(idItemToAdd));
	}
	
	@Test
	public void canRemoveFromRowMapIdprod() {
		gs.removeFromRowMapIdprod(rowToRemove);
		assertNull(gs.getRowmapidprod().get(rowToRemove));
	}
	
	@Test
	public void canShiftRow() {
		gs.shiftRow(rowToRemove);
		assertEquals(gs.getRowmapidprod().get(rowToRemove), gs.getProductslist().get(elementToShift).getIdProd());
		assertNotEquals(gs.getRowmapidprod().get(rowToRemove), gs.getProductslist().get(elementToRemove).getIdProd());
	}
	
	@Test
	public void canGenerateProductsCheckoutist() {
		gs.generateProductsCheckoutist();
		assertEquals(gs.getRowmapidprod().size(), gs.getProductscheckoutlist().size());
		assertTrue(gs.getProductscheckoutlist().contains(gs.getProductById(gs.getRowmapidprod().get(row))));
	}
	
	@AfterAll
	public static void canClearAll() {
		gs.clearAll();
		assertEquals(gs.getProdidmapcounter(), new HashMap<Integer, Integer>());
		assertEquals(gs.getProductscheckoutlist(), new ArrayList<ProductsBean>());
		assertEquals(gs.getProductslist(), new ArrayList<ProductsBean>());
		assertEquals(gs.getRowmapidprod(), new HashMap<Integer, Integer>());
	}
	
}
