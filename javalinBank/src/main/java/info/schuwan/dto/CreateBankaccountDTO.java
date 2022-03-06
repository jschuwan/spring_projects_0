package info.schuwan.dto;

import info.schuwan.models.BankAccount;

public class CreateBankaccountDTO {

    private BankAccount account;

    public CreateBankaccountDTO() {      //constructor
    }

    public CreateBankaccountDTO(BankAccount account) {      //constructor
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.account = account;
    }

    public BankAccount getAccount() {      //getter
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        return account;
    }

    public void setUser(BankAccount account) {      //setter
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.account = account;
    }
}
