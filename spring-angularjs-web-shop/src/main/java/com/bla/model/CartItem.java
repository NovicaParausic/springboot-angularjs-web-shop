package com.bla.model;

public class CartItem {

	private Long code;
	private String name;
	private double buyingPrice;
	private double price;
	private int quantity;
	private double revenue;
	
	public CartItem(){}

	public CartItem(Long code, String name, double buyingPrice, double price, int quantity, double revenue) {
		this.code = code;
		this.name = name;
		this.buyingPrice = buyingPrice;
		this.price = price;
		this.quantity = quantity;
		this.revenue = revenue;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(double buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
		
	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
}