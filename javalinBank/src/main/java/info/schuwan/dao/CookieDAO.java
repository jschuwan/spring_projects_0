package info.schuwan.dao;

import info.schuwan.database.ConnectionManager;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CookieDAO {

    String [] identification= new String[5] ;
    private ConnectionManager connectionManager;


    //Singleton class for cookie implementation to be assingned in a session to hold info about current logged in useraccount and bankaccount
    private static CookieDAO single_instance = null;

    private CookieDAO(Javalin app, ConnectionManager connectionManager ) {
        this.connectionManager = connectionManager;
        cookieInfo();
        app.post("/who", ctx -> {
            ctx.cookieStore("credentials", identification);
        });
    }

    public static CookieDAO CookieDAO(Javalin app, ConnectionManager connectionManager) {        // Static method to create instance of Singleton class
        if (single_instance == null) {
            single_instance = new CookieDAO(app, connectionManager );
        }
        return single_instance;
    }


    public void getMyCookie(Javalin app) {
        app.get("/me", ctx -> {
//            String x= ctx.cookieStore("credentials").toString();
            String [] cookie= ctx.cookieStore("credentials");
        });
    }

    public String[] cookieInfo() {

        try {
            Connection conn = this.connectionManager.getConnection();
//            String[] identification = new String[5];

            String sql = "SELECT id, username, roles_id from bank_user_accounts where (username = 'jill.brown'); "
                    ;
            PreparedStatement sqlPrepStmt = conn.prepareStatement(sql);                             // create the statement object
            ResultSet sqlResultSet = sqlPrepStmt.executeQuery();                                   // execute the sql query statement and get the resultset
//            sqlResultSet.next();                                                                   // iterator over the resultset and create the objects

            while (sqlResultSet.next()) {
                identification[0]= String.valueOf(sqlResultSet.getInt("id"));
                identification[1]= sqlResultSet.getString("username");
                identification[2]= String.valueOf(sqlResultSet.getInt("roles_id"));
            }


            sql = "SELECT accountnumber, accounttype from bank_account where (user_id = 3 and accountopen = true); "
            ;
            sqlPrepStmt = conn.prepareStatement(sql);                             // create the statement object
            sqlResultSet = sqlPrepStmt.executeQuery();                                   // execute the sql query statement and get the resultset
//            sqlResultSet.next();                                                                   // iterator over the resultset and create the objects

            while (sqlResultSet.next()) {
                identification[3]= sqlResultSet.getString("accountnumber");
                identification[4]= sqlResultSet.getString("accounttype");
            }

            return identification;

        } catch (SQLException ex) {
            System.out.println(ex);
            return new String[]{"x", "x", "x", "x","x"};
        }
    }

}

