package it.gb.salestaxes.dao;

import java.util.ArrayList;

import org.apache.commons.csv.CSVRecord;

import it.gb.salestaxes.bean.ProductsBean;

public interface ProductsDAO {
	public abstract ArrayList<ProductsBean> findProductsData();
	public abstract CSVRecord findProductsHeaders();
}
