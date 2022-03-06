package info.schuwan.services;
import info.schuwan.dao.BankaccountRepositoryDAO;
import info.schuwan.dao.RepositoryDAO;
import info.schuwan.models.BankAccount;
import info.schuwan.models.Transaction;
import info.schuwan.models.User;

import java.util.List;

// The service class represents our Business Logic Layer / what are the business rules
//  but at this moment all what it does is that it returns whatever the DAO returns
public class BankaccountService {

    BankaccountRepositoryDAO bankaccountRepDAO;

    public BankaccountService(BankaccountRepositoryDAO accountRepdao) {        //constructor
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ " + new Exception().getStackTrace()[0].getMethodName() + " ]");
        this.bankaccountRepDAO = accountRepdao;
    }

    public Integer save(BankAccount bnkacct) {
        return bankaccountRepDAO.save(bnkacct);
    }


    public List<BankAccount> getAll(){
        return bankaccountRepDAO.getAll();
    }


    public BankAccount getById(Integer bankaccountid){
        return bankaccountRepDAO.getById(bankaccountid);
    }


    public BankAccount getPrimaryBankaccount(Integer userid){
        return bankaccountRepDAO.getPrimaryBankaccount(userid);
    }


    public String closeAccount(String bankaccnt){
        return bankaccountRepDAO.closeAccount(bankaccnt);
    }


    public BankAccount getBankaccount(int accntnmberID) {
        return bankaccountRepDAO.getById(accntnmberID);
    }


}