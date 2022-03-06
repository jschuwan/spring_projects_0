package info.schuwan.dto;

import info.schuwan.models.Transaction;
import info.schuwan.models.User;

public class TransactionDTO {

    private Transaction transaction;

    public TransactionDTO() {                                         //constructor
    }

    public TransactionDTO(Transaction transaction) {                                           //constructor
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.transaction = transaction;
    }

    public void setTransaction(Transaction transaction) {       //setter
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.transaction = transaction;
    }

    public Transaction getTransaction() {                         //getter
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        return transaction;
    }

}
