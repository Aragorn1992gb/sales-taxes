package it.gb.salestaxes.daoImpl;

import java.io.BufferedWriter;
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
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.dao.ProductsDAO;
import it.gb.salestaxes.util.GlobalStorage;
import it.gb.salestaxes.util.UtilConstants;

public class ProductsDAOImpl implements ProductsDAO {
	UtilConstants utilConsts = new UtilConstants();
	static GlobalStorage gs = GlobalStorage.getInstance();
	private static DecimalFormat df = new DecimalFormat("#.00");
	private static final int CATEGORY_TAX_PERCENTAGE = 10;
	private static final int IMPORT_DUTY_PERCENTAGE = 5;

	@Override
	public ArrayList<ProductsBean> findProductsData() {
		ClassLoader classL = new ProductsDAOImpl().getClass().getClassLoader();
		// String filename = "products.csv";
		URI uri = null;
		ArrayList<ProductsBean> prodList = new ArrayList();
		try {
			uri = classL.getResource(utilConsts.PRODUCTS_FILENAME).toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String mainPath = Paths.get(uri).toString();

		try (Reader reader = Files.newBufferedReader(Paths.get(mainPath),
				Charset.forName(utilConsts.PRODUCTS_FILE_CHARSET));

				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.newFormat(utilConsts.PRODUCTS_FILE_DELIMITER).withFirstRecordAsHeader());

		) {
			for (CSVRecord csvRecord : csvParser) {
				// Accessing Values by Column Index

				ProductsBean prodObj = new ProductsBean();
				prodObj.setIdProd(Integer.parseInt(csvRecord.get(0)));
				prodObj.setName(csvRecord.get(1));

				NumberFormat nf = NumberFormat.getInstance(Locale.ITALIAN); // Looks like a US format
				double d = nf.parse(csvRecord.get(2)).doubleValue();
				prodObj.setPrice(d);

				prodObj.setCurrency(csvRecord.get(3));
				prodObj.setCategory(csvRecord.get(4));
				prodObj.setGender(csvRecord.get(5));
				prodObj.setDescription(csvRecord.get(6));
				prodObj.setIngredients(csvRecord.get(7));
				prodObj.setBrands(csvRecord.get(8));
				prodObj.setUnits(Integer.parseInt(csvRecord.get(9)));

				if (csvRecord.get(10).equals("")) {
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
			System.out.println("Exception " + e + " in parsing String to double.");
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

		try (Reader reader2 = Files.newBufferedReader(Paths.get(mainPath),
				Charset.forName(utilConsts.PRODUCTS_FILE_CHARSET));

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

	@Override
	public void updateCSV(ArrayList<ProductsBean> products) {
		URI uri = null;
		ClassLoader classL = new ProductsDAOImpl().getClass().getClassLoader();

		try {
			uri = classL.getResource(utilConsts.PRODUCTS_FILENAME).toURI();

			String mainPath = Paths.get(uri).toString();
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(mainPath),
					Charset.forName(utilConsts.PRODUCTS_FILE_CHARSET));

			CSVPrinter csvPrinter = new CSVPrinter(writer,
					CSVFormat.newFormat(utilConsts.PRODUCTS_FILE_DELIMITER)
							.withHeader("idProd", "Name", "Price", "Currency", "Category", "Gender", "Description",
									"Ingredients", "Brand", "Units", "Grams", "CountryProd")
							.withRecordSeparator(System.getProperty("line.separator")));
			{
				for (ProductsBean product : products) {
					csvPrinter.printRecord(product.getIdProd(), product.getName(), String.valueOf(product.getPrice()).replace(".", ","),
							product.getCurrency(), product.getCategory(), product.getGender(), product.getDescription(),
							product.getIngredients(), product.getBrands(), product.getUnits(), product.getGrams(),
							product.getCountryProd());
				}

				csvPrinter.flush();
			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double getTaxedPrice(ProductsBean product) {

		double initialPrice = product.getPrice();
		double taxedPrice = initialPrice;
		if (!(product.getCategory().toLowerCase().equals("book") || product.getCategory().toLowerCase().equals("food")
				|| product.getCategory().toLowerCase().equals("medical"))) {
			taxedPrice = taxedPrice + (initialPrice * CATEGORY_TAX_PERCENTAGE) / 100;
		}
		if (!product.getCountryProd().toLowerCase().equals(utilConsts.NATION.toLowerCase())) {
			taxedPrice = taxedPrice + (initialPrice * IMPORT_DUTY_PERCENTAGE) / 100;
		}

//		String strDouble = String.format("%.2f", 1.23456);
//		return  Double.parseDouble(df.format(taxedPrice));
		return roundPrice(taxedPrice);
	}

	@Override
	public double getTotalCalculation(ArrayList<ProductsBean> productsList) {
		double total = 0;

		for (ProductsBean product : productsList) {
			total = total + getTaxedPrice(product) * gs.getProdidmapcounter().get(product.getIdProd());
		}

		return roundPrice(total);
	}
	
	@Override
	public double roundPrice(double price) {
		return Math.round(price * 100.0) / 100.0;
	}


}
