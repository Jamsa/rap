package com.github.jamsa.rap.meta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class MetaTransactionManager {
    @Autowired
    private PlatformTransactionManager transactionManager;

    protected <T> T execute(Class<T> clazz,int propagationBehavior, MetaTransactionFunction<T> tx) throws RuntimeException{
        TransactionDefinition def = new DefaultTransactionDefinition(propagationBehavior);

        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            T result = tx.execute();
            transactionManager.commit(status);
            return result;
        }catch (Exception e){
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    protected <T> T execute(Class<T> clazz,MetaTransactionFunction<T> tx) throws RuntimeException{
        return execute(clazz,TransactionDefinition.PROPAGATION_REQUIRED,tx);
    }

}
