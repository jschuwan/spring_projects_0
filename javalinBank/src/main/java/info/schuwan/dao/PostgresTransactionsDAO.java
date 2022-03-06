package info.schuwan.dao;

import info.schuwan.database.ConnectionManager;
import info.schuwan.models.Transaction;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.text.SimpleDateFormat;

public class PostgresTransactionsDAO implements TransactionRepositoryDAO {

    private ConnectionManager connectionManager;

    public PostgresTransactionsDAO() {                  //consturctor
    }

    public PostgresTransactionsDAO(ConnectionManager connectionManager) {           //constructor
        this.connectionManager = connectionManager;
    }

    @Override
    public Integer save(Transaction  obj) {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try(Connection conn = this.connectionManager.getConnection()) {

            String sql = "     WITH ins1 AS ( " +
            "        SELECT accountnumber as assignedaccountnumber_s from bank_account " +
            "        where user_id = (SELECT id  from bank_user_accounts where username = 'jack.jack') " +
  	        "  ), " +
            "   ins2 as ( " +
            "        select assignedaccountnumber_s as assignedaccountnumber from ins1 " +
            "        where assignedaccountnumber_s = ? " +
            " )" +
            "   INSERT INTO bank_transactions  (accountnumber , date, type, description, amount) " +                    // create the sql statement
            "   select assignedaccountnumber ,  CURRENT_DATE, ? , ? ,?  FROM ins2  ;";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);                                 // create the statement object
            ps.setString(2, obj.getType());                // apply the 1st ?
            ps.setString(3, obj.getDescription());
            ps.setDouble(4, obj.getAmount());
            ps.setString(1, obj.getAccountnumber());

            ps.executeUpdate();                                          // execute the statement and get the resultset
            ResultSet keys = ps.getGeneratedKeys();                     // get back the results in an object ResultSet
            keys.next();
            return keys.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }


    @Override
    public List<Transaction> getAll() {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try {
            Connection conn = this.connectionManager.getConnection();                                   //get a connection
            System.out.println("Successfully connected to the database");                               //comfirm message we have a connection

            List<Transaction> transactions = new ArrayList<>();                                          //Set up the list to hold all records

            String sql = "select * from bank_transactions "
                    ;


            PreparedStatement sqlPrepStmt = conn.prepareStatement(sql);                             // create the statement object
            ResultSet sqlResultSet = sqlPrepStmt.executeQuery();                                   // execute the sql query statement and get the resultset
            sqlResultSet.next();                                                                   // iterator over the resultset and create the objects
//            while (sqlResultSet.next()) {
//                String g_i = String.valueOf(sqlResultSet.getInt("id"));
//                String g_t = sqlResultSet.getString("date");
//                String u_i = sqlResultSet.getString("type");
//                String u_n = sqlResultSet.getString("description");
//                String r_i = sqlResultSet.getString("amount");
//                String r_o = sqlResultSet.getString("accountnumber");
//                System.out.println("\tgreeting_id:\t"+g_i+"\tgreeting_text:\t"+g_t+"\tuser_id:\t"+u_i+"\tu.username:\t"+u_n+"\tr.id:\t"+r_i+"\tr.role:\t"+r_o);
//            }
            Transaction tempTransaction = newTransaction(sqlResultSet);
//            do {
//                if(tempTransaction.getId() != sqlResultSet.getInt("id")) {
//                    // working with a new record
//                    // add the old temp to the return
//                    // and create a new temp
//                    transactions.add(tempTransaction);
//                    tempTransaction = newTransaction(sqlResultSet);
//                } else {
//                    // add new roles to the user
////                    tempTransaction.getUser().getRoles().add(Roles.valueOf(sqlResultSet.getString("role")));
//                }
//            }while(sqlResultSet.next());
            transactions.add(tempTransaction);
            return transactions;                                                                    // return the Objects

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public Transaction getById(Integer transactionid) {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try(Connection conn = this.connectionManager.getConnection()) {                                 //get a connection

            Transaction transaction = null;

            String sql = "select * from bank_transactions " +
                    "where id = ?"
                    ;                                                                     // create the sql statement

            PreparedStatement ps = conn.prepareStatement(sql);                                      // create the statement object
            ps.setInt(1, transactionid);                                             // substiture the wildcards
            ResultSet rs = ps.executeQuery();                                                   // execute the statement and get the resultset
            rs.next();                                                                    // iterator over the resultset and create the objects
            transaction = newTransaction(rs);
            return transaction;                                                              // return the Objects
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }




    private Transaction newTransaction(ResultSet rs) throws SQLException {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        Transaction tempTransaction = new Transaction();

        tempTransaction.setId(rs.getInt("id"));
        tempTransaction.setDate(rs.getString("date"));
        tempTransaction.setType(rs.getString("type"));
        tempTransaction.setDescription(rs.getString("description"));
        tempTransaction.setAmount(Double.parseDouble(rs.getString("amount")));
        tempTransaction.setAccountnumber(rs.getString("accountnumber"));

        return tempTransaction;
    }

    @Override
    public List<Transaction> getByDate(String trnxdate) {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
            try {
                Connection conn = this.connectionManager.getConnection();                                   //get a connection
                System.out.println("Successfully connected to the database");                               //comfirm message we have a connection

                List<Transaction> transactions = new ArrayList<>();                                          //Set up the list to hold all records

            String sql = "select * from bank_transactions " +
                    "where date = ?"
                    ;


                PreparedStatement sqlPrepStmt = conn.prepareStatement(sql);
                sqlPrepStmt.setString(1, trnxdate);  // create the statement object
                ResultSet sqlResultSet = sqlPrepStmt.executeQuery();                                   // execute the sql query statement and get the resultset

                sqlResultSet.next();                                                                   // iterator over the resultset and create the objects

                Transaction tempTransaction = newTransaction(sqlResultSet);
                transactions.add(tempTransaction);
                return transactions;                                                                    // return the Objects

            } catch (SQLException ex) {
                System.out.println(ex);
                return null;
            }

    }


}
