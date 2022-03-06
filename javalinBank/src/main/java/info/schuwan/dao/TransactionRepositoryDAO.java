package info.schuwan.dao;

import info.schuwan.models.Transaction;

import java.util.List;

public interface TransactionRepositoryDAO extends RepositoryDAO<Integer, Transaction> {

    List<Transaction> getByDate(String date);

}
