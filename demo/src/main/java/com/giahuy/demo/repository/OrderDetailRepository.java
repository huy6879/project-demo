package com.giahuy.demo.repository;

import com.giahuy.demo.entity.OrderDetails;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer> {

    @Query("SELECT DATE(od.order.orderDate) AS date, SUM(od.quantity * od.unitPrice) AS revenue " +
            "FROM OrderDetails od WHERE DATE(od.order.orderDate) = :date " +
            "GROUP BY DATE(od.order.orderDate)")
    List<Tuple> findRevenueByDate(@Param("date") Date date);


    @Query("SELECT FUNCTION('MONTH',od.order.orderDate) as month, SUM(od.quantity * od.unitPrice) as revenue " +
            "FROM OrderDetails od " +
            "GROUP BY FUNCTION('MONTH', od.order.orderDate)")
    List<Tuple> findRevenueByMonths();


}
