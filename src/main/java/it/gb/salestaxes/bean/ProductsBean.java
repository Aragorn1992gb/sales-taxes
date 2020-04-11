package it.gb.salestaxes.bean;

public class ProductsBean {
	private int idProd;
	private String name;
	private double price;
	private String currency;
	private String category;
	private String gender;
	private String description;
	private String ingredients;
	private String brands;
	private int units;
	private Integer grams;
	private String countryProd;
	
	public String getCountryProd() {
		return countryProd;
	}
	public void setCountryProd(String countryProd) {
		this.countryProd = countryProd;
	}
	public int getIdProd() {
		return idProd;
	}
	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String column) {
		this.currency = column;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public Integer getGrams() {
		return grams;
	}
	public void setGrams(Integer grams) {
		this.grams = grams;
	}
	
	
}
