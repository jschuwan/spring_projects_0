package info.schuwan.models;

public class BankAccount {
    private int id;
    private String accountNumber;
    private String accountType;
    private Boolean accountOpen;
    private int user_id;


    public BankAccount() {         //constructor
    }

    public BankAccount(int id, String accountNumber, String accountType, Boolean accountOpen, int user_id) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountOpen = accountOpen;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Boolean getAccountOpen() {
        return accountOpen;
    }

    public void setAccountOpen(Boolean accountOpen) {
        this.accountOpen = accountOpen;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString(){ // to be able to display the content of the list of User objects
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" +
                "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        return  getId() + "," + getAccountNumber() + "," + getAccountType() +"," + getAccountOpen() + "," + getUser_id();
    }
}
