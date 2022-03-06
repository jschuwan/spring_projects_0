package info.schuwan.dto;

import java.util.List;

public class ListTranactionDTO {


    private List<TransactionDTO> transactions;

    public ListTranactionDTO() {                           //constructor
    }

    public List<TransactionDTO> getTransactions() {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        return transactions;
    }

    public ListTranactionDTO(List<TransactionDTO> transactions) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.transactions = transactions;
    }

    public void setGreetings(List<TransactionDTO> transactions) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.transactions = transactions;
    }
}
