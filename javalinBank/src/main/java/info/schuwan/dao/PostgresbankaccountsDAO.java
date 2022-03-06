package info.schuwan.dao;

import info.schuwan.database.ConnectionManager;
import info.schuwan.models.BankAccount;
import info.schuwan.models.Transaction;
import info.schuwan.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostgresbankaccountsDAO implements BankaccountRepositoryDAO{

    private Map<Integer, BankAccount> accounts;
    private ConnectionManager connectionManager;

    public PostgresbankaccountsDAO() {                  //consturctor
    }

    public PostgresbankaccountsDAO(ConnectionManager connectionManager) {           //constructor
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.connectionManager = connectionManager;
    }

    @Override
    public Integer save(BankAccount obj) {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try(Connection conn = this.connectionManager.getConnection()) {                                             //get a connection

            String sql = "INSERT INTO bank_account  ( user_id , accounttype , accountopen)" +
                    "SELECT ? , ? , ? from bank_user_accounts where username = 'chris.jones';" ;                       // create the sql statement

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);                     // create the statement object
            ps.setInt(1, obj.getUser_id());
            ps.setString(2, obj.getAccountType());
            ps.setBoolean(3, obj.getAccountOpen());

            ps.executeUpdate();                                                                                     // execute the statement and get the resultset
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            return keys.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }


    @Override
    public List<BankAccount> getAll() {                // Get ALL Open BankAccounts for the user

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        BankAccount tempAccount;
        try {
            Connection conn = this.connectionManager.getConnection();                                               //get a connection
            System.out.println("Successfully connected to the database");                                           //comfirm message we have a connection

            List<BankAccount> bankAccounts = new ArrayList<>();                                                                //Set up the list to hold all records

            String sql = "--  GET ALL BANK ACCOUNTS FOR A SPECIFC USER\n" +
                    "SELECT * from bank_account ba \n" +
                    "\twhere (user_id = 3 and accountopen = true ) ;" ;

            PreparedStatement sqlPrepStmt = conn.prepareStatement(sql);                                             // create the statement object
            ResultSet sqlResultSet = sqlPrepStmt.executeQuery();                                                    // execute the sql query statement and get the resultset
            sqlResultSet.next();                                                                                    // iterator over the resultset and create the objects
            while (sqlResultSet.next()) {
                tempAccount = newBankaccount(sqlResultSet);
                bankAccounts.add(tempAccount);
            }

            return bankAccounts;                                                                                          // return the Objects
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public BankAccount getById(Integer bankaccountid) {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try(Connection conn = this.connectionManager.getConnection()) {                                 //get a connection

            BankAccount bankaccount = null;

            String sql = "select * from bank_account " +
                    "where id = ?"
                    ;                                                                     // create the sql statement

            PreparedStatement ps = conn.prepareStatement(sql);                                      // create the statement object
            ps.setInt(1, bankaccountid);                                             // substiture the wildcards
            ResultSet rs = ps.executeQuery();                                                   // execute the statement and get the resultset
            rs.next();                                                                    // iterator over the resultset and create the objects
            bankaccount = newBankaccount(rs);
            return bankaccount;                                                              // return the Objects
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }

    @Override
    public BankAccount getPrimaryBankaccount(Integer userid) {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try(Connection conn = this.connectionManager.getConnection()) {                                 //get a connection

            BankAccount bankaccount = null;

            String sql = "SELECT * from bank_account ba " +
                    "where (user_id = ? and accountopen = true  " +
                    "and  id = (select min(id) from bank_account))" +
                    "; " ;                                                                     // create the sql statement

            PreparedStatement ps = conn.prepareStatement(sql);                                      // create the statement object
            ps.setInt(1, userid);                                             // substiture the wildcards
            ResultSet rs = ps.executeQuery();                                                   // execute the statement and get the resultset
            rs.next();                                                                    // iterator over the resultset and create the objects
            bankaccount = newBankaccount(rs);
            return bankaccount;                                                              // return the Objects
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }


    private BankAccount newBankaccount(ResultSet rs) throws SQLException {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        BankAccount tempBankAccount = new BankAccount();

        tempBankAccount.setId(rs.getInt("id"));
        tempBankAccount.setAccountNumber(rs.getString("accountnumber"));
        tempBankAccount.setAccountType(rs.getString("accounttype"));
        tempBankAccount.setAccountOpen(Boolean.valueOf(rs.getString("accountopen")));
        tempBankAccount.setUser_id(Integer.parseInt(rs.getString("user_id")));
        return tempBankAccount;
    }

    @Override
    public String closeAccount(String bankaccnt) {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try(Connection conn = this.connectionManager.getConnection()) {                                 //get a connection

         //   BankAccount bankaccount = null;

            String sql = " --CLOSE AN ACCOUNT\n" +
                    "  update bank_account ba\n" +
                    "   set accountopen  = false\n" +
                    "   where accountnumber = ? \n" +
                    "   ; "
                    ;                                                                     // create the sql statement

            PreparedStatement ps = conn.prepareStatement(sql);                                      // create the statement object
            ps.setString(1, bankaccnt);                                             // substiture the wildcards
            ResultSet rs = ps.executeQuery();                                                   // execute the statement and get the resultset
            rs.next();                                                                    // iterator over the resultset and create the objects
         //   bankaccount = newBankaccount(rs);
            return bankaccnt;                                                              // return the Objects
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }


    @Override
    public BankAccount getBankaccount(String accntnmber) {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try(Connection conn = this.connectionManager.getConnection()) {                                 //get a connection

            BankAccount bankaccount = null;

            String sql = "select * from bank_account " +
                    "where accountnumber = ?"
                    ;                                                                     // create the sql statement

            PreparedStatement ps = conn.prepareStatement(sql);                                      // create the statement object
            ps.setString(1, accntnmber);                                             // substiture the wildcards
            ResultSet rs = ps.executeQuery();                                                   // execute the statement and get the resultset
            rs.next();                                                                    // iterator over the resultset and create the objects
            bankaccount = newBankaccount(rs);
            return bankaccount;                                                              // return the Objects
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }
}