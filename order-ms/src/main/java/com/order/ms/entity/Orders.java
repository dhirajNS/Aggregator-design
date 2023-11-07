package com.order.ms.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Data
@Table(name = "orders")
@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String item;

	@Column
	private int quantity;

	@Column
	private double amount;

	@Column
	private String status;

}
