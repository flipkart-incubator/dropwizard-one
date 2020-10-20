package com.flipkart.utils.dwone.persistence.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.transaction.InvalidTransactionException;

@Getter
@RequiredArgsConstructor
public class TransactionManager {
    private final Session session;

    public void beginTransaction() throws InvalidTransactionException {
        Transaction txn = session.getTransaction();
        if (txn != null && txn.getStatus() == TransactionStatus.ACTIVE) {
            throw new InvalidTransactionException("Session already has active transaction");
        }
        session.beginTransaction();
    }

    public void commitTransaction() {
        Transaction txn = session.getTransaction();
        if (txn != null && txn.getStatus() == TransactionStatus.ACTIVE) {
            txn.commit();
        }

        session.close();
    }

    public void rollbackTransaction() {
        Transaction txn = session.getTransaction();
        if (txn != null && txn.getStatus() == TransactionStatus.ACTIVE) {
            txn.rollback();
        }

    }
}
