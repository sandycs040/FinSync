package com.example.FinSync.entity.mongoWealth;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BankDetailsRepository extends MongoRepository<BankDetails,String> {
    List<BankDetails>  findAll();
}
