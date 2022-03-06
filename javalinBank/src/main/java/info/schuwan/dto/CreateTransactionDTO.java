package info.schuwan.dto;

import info.schuwan.models.Transaction;

//
public class CreateTransactionDTO {

    private Transaction transaction;


    public CreateTransactionDTO() {
    }

    public CreateTransactionDTO(Transaction transaction) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.transaction = transaction;
    }


    public Transaction getTransaction() {      //getter
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        return transaction;
    }

    public void setTransaction(Transaction transaction) {      //setter
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.transaction = transaction;
    }


}
