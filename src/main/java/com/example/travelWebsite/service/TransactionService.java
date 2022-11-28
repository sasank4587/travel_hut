package com.example.travelWebsite.service;

import com.example.travelWebsite.collections.Transactions;
import com.example.travelWebsite.request.TransactionRequest;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    Transactions createTransaction(TransactionRequest transactionRequest);
}
