package it.gb.salestaxes.test;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import it.gb.salestaxes.daoImpl.ProductsDAOImpl;

// Junit class test for footballDAOImpl methods
public class ProductsDAOImplTest {
	ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl(); // Class tested
	
    @Test
    public void canReadFile() {
        assertNotNull("Method canReadFile(): Read ok file",productsDAOImpl.findProductsData());
    }
    
    @Test
    public void canReadHeaders() {
        assertNotNull("Method canReadHeaders(): Read ok headers",productsDAOImpl.findProductsHeaders());
    }
}