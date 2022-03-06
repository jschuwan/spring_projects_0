package info.schuwan.dao;

import info.schuwan.models.BankAccount;

public interface BankaccountRepositoryDAO extends RepositoryDAO<Integer, BankAccount> {

    BankAccount getBankaccount(String bankaccount);

    String closeAccount(String bankaccnt);

    BankAccount getPrimaryBankaccount(Integer userid);

}