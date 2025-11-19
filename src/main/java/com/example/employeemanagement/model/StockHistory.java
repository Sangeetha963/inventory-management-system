package com.example.employeemanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stock_history")
public class StockHistory {
   

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "log_id")
   private Long logId;
   private Long productId;
   private Integer oldQty;
   private Integer newQty;
   private String changedOn;

   public StockHistory() {}
}