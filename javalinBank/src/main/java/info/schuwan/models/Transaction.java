package info.schuwan.models;

public class Transaction {
    private  int id;
    private  String date;
    private String type;
    private String description;
    private double  amount;
    private String accountnumber;


    public Transaction() {
    }

    public Transaction(String type, String description, double amount, String accountnumber) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.accountnumber = accountnumber;
    }
    public Transaction(int id, String date, String type, String description, double amount, String accountnumber) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.accountnumber = accountnumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    @Override
    public String toString(){                                       // to be able to display the content of the list of User objects
        return  getId() + "," + getDate() + "," + getType() + "," + getDescription() + "," + getAmount() + "," + getAccountnumber();
    }
}
