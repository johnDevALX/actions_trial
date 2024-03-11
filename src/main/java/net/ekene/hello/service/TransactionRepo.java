package net.ekene.hello.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findTransactionByRefIgnoreCase(String transactionRef);
}
