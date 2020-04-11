package it.gb.salestaxes.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.daoImpl.ProductsDAOImpl;
import it.gb.salestaxes.util.GlobalStorage;

// Junit class test for footballDAOImpl methods
public class ProductsDAOImplTest {
	ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl(); // Class tested

	
	double price = 5.00;
	String category1 = "Book";
	String category2 = "Electronics";
	String country1 = "Italy";
	String country2 = "Japan";
	double price2 = 6.00;
	double price3 = 3.00;
	
	private static final int CATEGORY_TAX_PERCENTAGE = 10;
	private static final int IMPORT_DUTY_PERCENTAGE = 5;
	
	static GlobalStorage gs = GlobalStorage.getInstance();
	
    @Test
    public void canReadFile() {
        assertNotNull("Method canReadFile(): Read ok file",productsDAOImpl.findProductsData());
    }
    
    @Test
    public void canReadHeaders() {
        assertNotNull("Method canReadHeaders(): Read ok headers",productsDAOImpl.findProductsHeaders());
    }
    
    @Test
    public void canGetTaxedPrice() {
    	ProductsBean product = new ProductsBean();
    	
    	product.setPrice(price);
    	product.setCategory(category1);
    	product.setCountryProd(country1);

    	assertEquals(productsDAOImpl.getTaxedPrice(product), price);
    	
    	product.setPrice(price);
    	product.setCategory(category1);
    	product.setCountryProd(country2);

    	assertNotEquals(productsDAOImpl.getTaxedPrice(product), price);
    	
    	product.setPrice(price);
    	product.setCategory(category2);
    	product.setCountryProd(country1);
    	
    	assertNotEquals(productsDAOImpl.getTaxedPrice(product), price);
    	
    	product.setPrice(price);
    	product.setCategory(category2);
    	product.setCountryProd(country1);
    	
    	assertNotEquals(productsDAOImpl.getTaxedPrice(product), price);

    }
    
     @Test
     public void canGetTotalCalculation() {
    	ArrayList<ProductsBean> productsList = new ArrayList<ProductsBean>();
    	//HashMap<Integer, Integer> prodIdMapCounter = new HashMap<Integer, Integer>();
    	int key1 = 0, key2 = 1, key3 = 2;
    	
    	gs.addToProdIdMapCounter(key1, 6);
    	gs.addToProdIdMapCounter(key2, 1);
    	gs.addToProdIdMapCounter(key3, 3);
    	
    	ProductsBean product1 = new ProductsBean();
    	
    	product1.setPrice(price);
    	product1.setCategory(category1);
    	product1.setCountryProd(country1);
    	product1.setIdProd(key1);
    	
    	productsList.add(product1);
    	
    	ProductsBean product2 = new ProductsBean();
    	
    	product2.setPrice(price2);
    	product2.setCategory(category2);
    	product2.setCountryProd(country1);
    	product2.setIdProd(key2);
    	
    	productsList.add(product2);
    	
    	ProductsBean product3 = new ProductsBean();

    	product3.setPrice(price3);
    	product3.setCategory(category2);
    	product3.setCountryProd(country2);
    	product3.setIdProd(key3);
    	
    	productsList.add(product3);
    	
    	double totalProd1 = productsDAOImpl.getTaxedPrice(productsList.get(key1)) * gs.getProdidmapcounter().get(productsList.get(key1).getIdProd());
    	double totalProd2 = productsDAOImpl.getTaxedPrice(productsList.get(key2)) * gs.getProdidmapcounter().get(productsList.get(key2).getIdProd());
    	double totalProd3 = productsDAOImpl.getTaxedPrice(productsList.get(key3)) * gs.getProdidmapcounter().get(productsList.get(key3).getIdProd());
    	
    	double total = totalProd1 + totalProd2 + totalProd3;
    	
    	assertEquals(productsDAOImpl.getTotalCalculation(productsList),total);
     }
}