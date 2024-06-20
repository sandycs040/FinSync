package com.example.FinSync.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MutualFundRepository extends JpaRepository<MutualFunds, UUID> {
    List<MutualFunds> findByUserId(Long userId);

    MutualFunds findByDematAccountNumber(String dematAccountNumber);

    //@Query(value ="SELECT m from MutualFunds m where m.User.id =:userId and m.mf_name =:mfName",nativeQuery = true)
//    @Query("SELECT m FROM MutualFunds m WHERE m.user.id = :userId AND m.mfName = :mfName")
//    MutualFunds getFundByUserIdAndFundName(@Param("userId") Long userId,@Param("stockName") String mfName);
}
