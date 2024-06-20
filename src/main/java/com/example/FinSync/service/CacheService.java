//package com.example.FinSync.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CacheService {
//    private static final Logger logger = LoggerFactory.getLogger(AuthenticationValidator.class);
//
//    @Autowired
//    private WealthService wealthService;
//
//    @Autowired
//    private CacheManager cacheManager;
//
//    @CacheEvict(value = {"bankDetails","mutualFund","stock"}, allEntries = true)
//    @Scheduled(fixedRate = 60000 ) // Run every 60 seconds
//    public void evictAllCacheValues() {
//        // This will clear the cache, causing the next request to reload the data
//        logger.info("Executing cache eviction task...");
//        try {
//            // Perform cache eviction
//            cacheManager.getCache("bankDetails").clear();
//            cacheManager.getCache("mutualFund").clear();
//            cacheManager.getCache("stock").clear();
//            logger.info("Cache eviction completed successfully.");
//        } catch (Exception e) {
//            logger.error("Error occurred during cache eviction: {}", e.getMessage());
//        }
//    }
//
//}
