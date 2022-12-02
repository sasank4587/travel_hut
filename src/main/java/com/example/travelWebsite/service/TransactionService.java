package com.example.travelWebsite.service;

import com.example.travelWebsite.collections.Transactions;
import com.example.travelWebsite.request.TransactionRequest;
import com.example.travelWebsite.response.TransactionList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    Transactions createTransaction(TransactionRequest transactionRequest);

    TransactionList getAllTransactionsForUser(Integer id);
}
