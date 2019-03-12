package com.bla.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bla.common.TimeProvider;

@Entity
@Table(name = "order_table")
public class Order {

	@Id
	@GeneratedValue
	private Long id;
	private Long userId;
	private double value;
	private double price;
	private double margin;
	
	@Temporal(TemporalType.DATE)
	private Date date;

	public Order() {}
	
	public Order(Long userId) {
		this.userId = userId;
		this.date = TimeProvider.now();
	}
	
	public Order(Long userId, double value, double price, double margin) {
		this.userId = userId;
		this.value = value;
		this.price = price;
		this.margin = margin;
		this.date = TimeProvider.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	
}
