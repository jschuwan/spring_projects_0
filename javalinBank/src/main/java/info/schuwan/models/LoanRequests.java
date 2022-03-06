package info.schuwan.models;

public class LoanRequests {
    private int id;
    private String date;
    private String description;
    private Boolean reviewed;
    private Boolean approved;
    private int creditScore;
    private double interestRate;
    private double  amount;
    private int user_id;
    String loanaccountnumber;

    public LoanRequests() {         //constructor
    }

    public LoanRequests(int id, String date, String description, Boolean reviewed, Boolean approved, int creditScore, double interestRate, double amount, int user_id, String loanaccountnumber) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.reviewed = reviewed;
        this.approved = approved;
        this.creditScore = creditScore;
        this.interestRate = interestRate;
        this.amount = amount;
        this.user_id = user_id;
        this.loanaccountnumber = loanaccountnumber;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getReviewed() {
        return reviewed;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLoanaccountnumber() {
        return loanaccountnumber;
    }

    public void setLoanaccountnumber(String loanaccountnumber) {
        this.loanaccountnumber = loanaccountnumber;
    }

    @Override
    public String toString(){ // to be able to display the content of the list of User objects
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" +
                "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        return  getId() + "," + getDate() + "," + getDescription() +"," + getReviewed() + "," + getApproved() + "," +
                getCreditScore()+ "," + getInterestRate() + "," + getAmount() + "," + getUser_id() + "," + getLoanaccountnumber() ;
    }
}
