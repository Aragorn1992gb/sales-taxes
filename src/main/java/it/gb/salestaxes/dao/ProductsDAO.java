package it.gb.salestaxes.dao;

import java.util.ArrayList;

import org.apache.commons.csv.CSVRecord;

import it.gb.salestaxes.bean.ProductsBean;

public interface ProductsDAO {
	public abstract ArrayList<ProductsBean> findProductsData();
	public abstract CSVRecord findProductsHeaders();
	public abstract double getTaxedPrice(ProductsBean product);
	public abstract double getTotalCalculation(ArrayList<ProductsBean> productsList);
	public abstract void updateCSV(ArrayList<ProductsBean> products);
	public abstract double  roundPrice(double price);
}
