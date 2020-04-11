package it.gb.salestaxes.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.daoImpl.ProductsDAOImpl;

// Junit class test for footballDAOImpl methods
public class ProductsDAOImplTest {
	ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl(); // Class tested

	
	double price = 5.00;
	String category1 = "Book";
	String category2 = "Electronics";
	String country1 = "Italy";
	String country2 = "Japan";
	
	
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
}