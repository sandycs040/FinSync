package com.example.FinSync.entity.mongoWealth;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MutualFundPriceRepository extends MongoRepository<MutualFundPrice,String> {
    @Override
    List<MutualFundPrice> findAll();


}
