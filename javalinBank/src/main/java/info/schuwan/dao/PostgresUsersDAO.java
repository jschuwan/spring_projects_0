package info.schuwan.dao;

import info.schuwan.database.ConnectionManager;
import info.schuwan.models.BankAccount;
import info.schuwan.models.Roles;
import info.schuwan.models.Transaction;
import info.schuwan.models.User;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PostgresUsersDAO implements UserRepositoryDAO {

    private AtomicInteger idGen;
    private Map<Integer, User> users;
    private ConnectionManager connectionManager;


    public PostgresUsersDAO() {                  //consturctor

    }

    public PostgresUsersDAO(ConnectionManager connectionManager) {           //constructor
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.connectionManager = connectionManager;
        //getLoginInfo();

//        idGen = new AtomicInteger(1);
//        users = new HashMap<>();
//        users.put(idGen.getAndDecrement(), new User(idGen.get(), "august.duet", "p@$$w0rd123",
//                new Roles[]{Roles.ROLE_ADMIN, Roles.ROLE_USER},750,"august@revature.com"));
//        users.put(idGen.getAndDecrement(), new User(idGen.get(), "jack.schuwan", "p@$$w0rd123",
//                new Roles[]{Roles.ROLE_USER},750,"jack@revature.com"));
        idGen = new AtomicInteger(1);
        users = new HashMap<>();
//        users.put(idGen.getAndDecrement(), new User(idGen.get(), "august.duet", "p@$$w0rd123",
//                "ROLE_ADMIN",750,"august@revature.com"));
//        users.put(idGen.getAndDecrement(), new User(idGen.get(), "jack.schuwan", "p@$$w0rd123",
//                "ROLE_USER",750,"jack@revature.com"));
//

    }

    public Map<Integer, User> getLoginInfo(){
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");

        try(Connection conn = this.connectionManager.getConnection()) {                                 //get a connection

            User tempuser = null;

            String sql = "select * from bank_user_accounts ;" ;                         // create the sql statement

            PreparedStatement sqlPrepStmt = conn.prepareStatement(sql);                                      // create the statement object
//            ps.setInt(1, transactionid);                                             // substiture the wildcards
            ResultSet sqlResultSet = sqlPrepStmt.executeQuery();                                                    // execute the sql query statement and get the resultset
            sqlResultSet.next();                                                                                    // iterator over the resultset and create the objects
            while (sqlResultSet.next()) {
                tempuser = newAccount(sqlResultSet);
                users.put(idGen.getAndIncrement(), tempuser);
            }
            return users;                                                              // return the Objects
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public Integer save(User obj) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try(Connection conn = this.connectionManager.getConnection()) {                                             //get a connection

            String sql = "insert into Bank_user_accounts (username, password, roles_id, email) " +                  // create the sql statement
                    "values ( ? , ? , ? , ?);";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);                     // create the statement object
            ps.setString(1, obj.getUsername());
            ps.setString(2, obj.getPassword());
            ps.setInt(3, obj.getRolesid());
            ps.setString(4, obj.getEmail());

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
    public List<User> getAll() {                // Get User Account

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        try {
            Connection conn = this.connectionManager.getConnection();                                               //get a connection
            System.out.println("Successfully connected to the database");                                           //comfirm message we have a connection

            List<User> accounts = new ArrayList<>();                                                                //Set up the list to hold all records

            String sql = "select * from bank_user_accounts "     // create the sql statement
                   + "where Bank_user_accounts.username = 'jack.schuwan' " +                                         // should incorporate the cookie
                    "order by bank_user_accounts.id  "
                    ;

            PreparedStatement sqlPrepStmt = conn.prepareStatement(sql);                                             // create the statement object
            ResultSet sqlResultSet = sqlPrepStmt.executeQuery();                                                    // execute the sql query statement and get the resultset
            sqlResultSet.next();                                                                                    // iterator over the resultset and create the objects
            User tempAccount = newAccount(sqlResultSet);

            accounts.add(tempAccount);

            return accounts;                                                                                          // return the Objects
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public User getById(Integer userid) {
            return null;

    }

    private User newAccount(ResultSet rs) throws SQLException {

        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        User tempAccount = new User();


        ResultSet rs2;
        try (Connection conn = this.connectionManager.getConnection()) {                                 //get a connection

            User u = null;

            String sql = "select * from bank_roles br " +
                    "join bank_user_accounts bua on br.id = bua.roles_id " +
                    "where bua.id = ? ;";                                                                     // create the sql statement

            PreparedStatement ps = conn.prepareStatement(sql);                                      // create the statement object
            ps.setInt(1, (rs.getInt("id")));                                             // substiture the wildcards
            rs2 = ps.executeQuery();
            rs2.next();                                                                    // iterator over the resultset and create the objects

            // return the Objects
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }


        tempAccount.setId(rs.getInt("id"));
        tempAccount.setUsername(rs.getString("username"));
        tempAccount.setPassword(rs.getString("password"));
        tempAccount.setRolesid(Integer.parseInt(rs.getString("roles_id")));
        tempAccount.setCreditScore((int) Double.parseDouble(rs.getString("creditscore")));
        tempAccount.setEmail(rs.getString("email"));

        // applying a second query to update the User entity with the Roles using the rolesid
        tempAccount.setRoles(rs2.getString("role"));
        return tempAccount;
    }



    @Override
    public User getByUsername(String username) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");

        return users.values()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElse(null);
    }

}