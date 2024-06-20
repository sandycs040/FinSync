package com.example.FinSync.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stocks, UUID> {
    List<Stocks> findByUserId(Long userId);

    Stocks findByDematAccountNumber(String dematAccountNumber);

    //@Query(value="SELECT m from Stocks m where m.User.id  =:userId and m.stockName =:stockName" ,nativeQuery = true)
//    @Query("SELECT m FROM Stocks m WHERE m.user.id = :userId AND m.stockName = :stockName")
//    Stocks getStockByUserIdAndStockName(@Param("stockName") String stockName, @Param("userId") Long userId);
}
