package com.splitit.splitit.API;

import com.splitit.splitit.DebtGraph;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepo extends MongoRepository<DebtGraph, ObjectId> {
    @Query("{_id: '?0'}")
    DebtGraph findDebtGraphByGroupId(ObjectId debtId);
}
