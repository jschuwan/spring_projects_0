package info.schuwan.services;

import info.schuwan.dao.TransactionRepositoryDAO;
import info.schuwan.dto.ListTranactionDTO;
import info.schuwan.dto.TransactionDTO;
import info.schuwan.models.Transaction;

import java.util.ArrayList;
import java.util.List;

// The service class represents our Business Logic Layer / what are the business rules
//  but at this moment all what it does is that it returns whatever the DAO returns
public class TransactionService {

    TransactionRepositoryDAO tranxRepositoryDAO;

    public TransactionService(TransactionRepositoryDAO tranxnRepDAO) {        //constructor
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");

        this.tranxRepositoryDAO = tranxnRepDAO;
    }



    public Integer save(Transaction  trxn){
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");

        return this.tranxRepositoryDAO.save(trxn)  ;
    };
    public Transaction transactionById(int id) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");

        return this.tranxRepositoryDAO.getById(id);
    }

    public List<Transaction> getAll() {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");

        return this.tranxRepositoryDAO.getAll();
    }

    public List<Transaction> getByDate(String trnxdate){
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");

        return this.tranxRepositoryDAO.getByDate(trnxdate);
    }



    public ListTranactionDTO getAllGreetings() {

        List<Transaction> transactions = tranxRepositoryDAO.getAll();
        ListTranactionDTO listTrxnDTO = new ListTranactionDTO();
        listTrxnDTO.setGreetings(new ArrayList<>());


        return listTrxnDTO;

    }
}
