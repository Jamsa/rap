package com.github.jamsa.rap.meta.service;

@FunctionalInterface
public interface MetaTransactionFunction<T> {
    T execute();
}
