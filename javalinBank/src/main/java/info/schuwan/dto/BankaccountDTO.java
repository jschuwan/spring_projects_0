package info.schuwan.dto;

import info.schuwan.models.BankAccount;
import info.schuwan.models.User;

public class BankaccountDTO {


    private BankAccount bankaccount;


    public BankaccountDTO() {       //constructor

    }

    public BankaccountDTO(BankAccount bankaccount) {       //constructor
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.bankaccount = bankaccount;
    }

    // Getters and Setters for all variables


    public BankAccount getBankaccount() {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        return bankaccount;
    }

    public void setBankaccount(BankAccount bankaccount) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.bankaccount = bankaccount;
    }


}