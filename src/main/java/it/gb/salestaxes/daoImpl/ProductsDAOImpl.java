package it.gb.salestaxes.daoImpl;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.dao.ProductsDAO;
import it.gb.salestaxes.util.GlobalStorage;
import it.gb.salestaxes.util.UtilConstants;

public class ProductsDAOImpl implements ProductsDAO{
	UtilConstants utilConsts = new UtilConstants();
	static GlobalStorage gs = GlobalStorage.getInstance();
	//private static DecimalFormat df = new DecimalFormat("0.00");
	private static final int CATEGORY_TAX_PERCENTAGE = 10;
	private static final int IMPORT_DUTY_PERCENTAGE = 5;

	@Override
	public ArrayList<ProductsBean> findProductsData() {
		//String filename = "products.csv";
		ClassLoader classL = new ProductsDAOImpl().getClass().getClassLoader();
		URI uri = null;
		ArrayList<ProductsBean> prodList = new ArrayList();
		try {
			uri = classL.getResource(utilConsts.PRODUCTS_FILENAME).toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String mainPath = Paths.get(uri).toString();
		
        try (
                Reader reader = Files.newBufferedReader(Paths.get(mainPath),Charset.forName(utilConsts.PRODUCTS_FILE_CHARSET));

                CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(utilConsts.PRODUCTS_FILE_DELIMITER).withFirstRecordAsHeader());
        	
        		) {
                for (CSVRecord csvRecord : csvParser) {
                    // Accessing Values by Column Index
                	
    				ProductsBean prodObj = new ProductsBean();
    				prodObj.setIdProd(Integer.parseInt(csvRecord.get(0)));
    				prodObj.setName(csvRecord.get(1));
    				
    				NumberFormat nf = NumberFormat.getInstance(Locale.ITALIAN); // Looks like a US format
    				double d = nf.parse(csvRecord.get(2)).floatValue();
    				prodObj.setPrice(d);
    				
    				prodObj.setCurrency(csvRecord.get(3));
    				prodObj.setCategory(csvRecord.get(4));
    				prodObj.setGender(csvRecord.get(5));
    				prodObj.setDescription(csvRecord.get(6));
    				prodObj.setIngredients(csvRecord.get(7));
    				prodObj.setBrands(csvRecord.get(8));
    				prodObj.setUnits(Integer.parseInt(csvRecord.get(9)));
    				
    				if(csvRecord.get(10).equals("")) {
    					prodObj.setGrams(null);
    				} else {
    					prodObj.setGrams(Integer.valueOf(csvRecord.get(10)));
    				}
    				
    				prodObj.setCountryProd(csvRecord.get(11));
 
    				prodList.add(prodObj);
                }
				csvParser.close();
				reader.close();
            } catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				System.out.println("Exception "+e+" in parsing String to double.");
				e.printStackTrace();
			}
		return prodList;
	}
	
	@Override
	public CSVRecord findProductsHeaders() {
		ClassLoader classL = new ProductsDAOImpl().getClass().getClassLoader();
		URI uri = null;
		try {
			uri = classL.getResource(utilConsts.PRODUCTS_FILENAME).toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String mainPath = Paths.get(uri).toString();
		
        try (
        		Reader reader2 = Files.newBufferedReader(Paths.get(mainPath),Charset.forName(utilConsts.PRODUCTS_FILE_CHARSET));

                CSVParser csvParser = new CSVParser(reader2, CSVFormat.newFormat(utilConsts.PRODUCTS_FILE_DELIMITER));
        	
        		) {
                for (CSVRecord csvRecord : csvParser) {
                	return csvRecord;
                }
            } catch (IOException e) {
				e.printStackTrace();
            }
        
		return null;
	}
	
	public double getTaxedPrice(ProductsBean product) {
		
		double initialPrice = product.getPrice();
		double taxedPrice = initialPrice;
		if(!(product.getCategory().toLowerCase().equals("book") ||  product.getCategory().toLowerCase().equals("food") ||  product.getCategory().toLowerCase().equals("medical"))) {
			taxedPrice = taxedPrice+(initialPrice*CATEGORY_TAX_PERCENTAGE)/100;
		}
		if(!product.getCountryProd().toLowerCase().equals(utilConsts.NATION.toLowerCase())) {
			taxedPrice = taxedPrice + (initialPrice*IMPORT_DUTY_PERCENTAGE)/100;
		}
		
		return  Math.round(taxedPrice*100.0)/100.0;
	}

}
