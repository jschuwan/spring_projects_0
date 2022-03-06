package info.schuwan;

import info.schuwan.controllers.AuthController;
import info.schuwan.controllers.BankaccountsController;
import info.schuwan.controllers.TransactionsController;
import info.schuwan.controllers.UserController;
import info.schuwan.dao.*;
import info.schuwan.database.ConnectionManager;
import info.schuwan.database.PostgresConnectionManager;
import info.schuwan.dto.ErrorResponseDTO;
import info.schuwan.models.Roles;
import info.schuwan.models.Transaction;
import info.schuwan.models.User;
import info.schuwan.services.*;
import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static io.javalin.apibuilder.ApiBuilder.*;
/**
 * @author      Jack Schuwan <jack.schuwan@ revature.net>
 * @version     0.0
 * The main application App.java                             (1)
 * <p>
 * This application is a an online bank API                  (2)
 * It utilizes the power of java and multiple other
 * frameworks such as, JDBC Database Connectivity,
 * Javalin Web framework, PostgreSQL Server hosted on
 * the Google Cloud
 * <p>
 * We are still not in beta yet, because we implemented       (3)
 * data persistence and undergoing some minor modifications
 * <p>
 *     *
 */
public class App {
    public static void main( String[] args ) {


        loadWebApplicationWithAccessAuthorization();                         // Option A -- With authentication
//        loadWebApplicationWithout();                                       // Option B -- Without authentication
    }

    // Option A -- With authentication
    static void loadWebApplicationWithAccessAuthorization(){

        //setuo a atabase connection
        Properties connectionManagerProps = new Properties();
        connectionManagerProps.setProperty("db.username", "postgres");
        connectionManagerProps.setProperty("db.password","");   //I have it as blank in my localhost but it should be p@$$w0rd
        connectionManagerProps.setProperty("db.url","jdbc:postgresql://localhost:5432/postgres");    //postgres JDBC url -- every driver has its own format-- I am connecting to localhost because I have a local postgres server
        ConnectionManager connectionManager = new PostgresConnectionManager();
        try {
            connectionManager.init(connectionManagerProps);
            Connection conn = connectionManager.getConnection(); // to see if we got a connection
            System.out.println("Successfully connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }


        // Start Javalin with custom configuration
        UserRepositoryDAO userRepo =  new PostgresUsersDAO(connectionManager);

        UserService userService = new UserService(userRepo);
        JWTService tokenService = new JWTService();
        AuthService authService = new AuthService(userService, tokenService);

        AuthController authController = new AuthController(authService);
        // setup the Javalin app with Access Manager configuration
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.accessManager((handler, context, requiredRoles) ->{
                String header = context.header("Authorization");

                if(requiredRoles.isEmpty()) {
                    System.out.println("Haadi");
                    System.out.println(requiredRoles);
                    handler.handle(context);
                    return;
                }
                if(header == null) {
                    throw new ForbiddenResponse("This request requires an Authorization Header");
                } else {
                    if(!header.startsWith("Bearer ")) {
                        throw new ForbiddenResponse("This reqwest requires token bearer access");
                    } else {
                        String token = header.split(" ")[1];
                        try{
                            Claims claims = tokenService.decode(token);
                            String username = claims.getSubject();

                            User user = userService.getByUsername(username);

                            if(user == null) {
                                throw new ForbiddenResponse("USER is unauthorized to perform reaquest");
                            } else {
                                if(authService.authorize(user, String.valueOf(requiredRoles))) {
                                    // if we get to here, the USER is authorized
                                    handler.handle(context);
                                } else {
                                    throw new ForbiddenResponse("USER is not authorized to perform request");
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            throw new ForbiddenResponse("The USER could not be validated- decode isn't working?");
                        }
                    }
                }
            });
        }).start(8080);



        // create all dependencies at this level to control how they get used downstream (DEPENDENCY INJECTION)
        TransactionRepositoryDAO transRepo = new PostgresTransactionsDAO(connectionManager);     //mod - adding repo
        UserRepositoryDAO accountRepo = new PostgresUsersDAO(connectionManager);               // PostgresDao
        BankaccountRepositoryDAO bankaccountRepo = new PostgresbankaccountsDAO(connectionManager);               // PostgresDao
        TransactionService transSvc = new TransactionService(transRepo);          //mod - adding service
        UserService accountSvc = new UserService(accountRepo);          //mod - adding service
        BankaccountService bankaccountSvc = new BankaccountService(bankaccountRepo);          //mod - adding service

        HomePageDAO homePage = new HomePageDAO();       // setup the Home Page with API information

        // setup the paths
        app.get("/", ctx -> ctx.result( homePage.getMainMessage()));
        app.routes(() ->{
            crud("banking/{id}", new TransactionsController(transSvc), Roles.ROLE_USER);       //mod-  pass service as parameter
            crud("useradmin/banking/{id}", new UserController(accountSvc), Roles.ROLE_ADMIN);       //mod-  pass service as parameter
            crud("bankaccountadmin/banking/{id}", new BankaccountsController(bankaccountSvc), Roles.ROLE_ADMIN);       //mod-  pass service as parameter
            path("auth", () -> {
                post("login", authController.login);
            });
        });

        //Handling Exceptions
        app.exception(NotFoundResponse.class, (e, ctx) -> {
            ErrorResponseDTO errResp = new ErrorResponseDTO(e.getMessage(), 404);
            ctx.status(404);
            ctx.json(errResp);
        });
        app.exception(NullPointerException.class, (e, ctx) -> {
            ErrorResponseDTO errResp = new ErrorResponseDTO("The devs don't do null checks!", 500);
            ctx.status(500);
            ctx.json(errResp);
        });
        app.exception(UnauthorizedResponse.class, (e, ctx) -> {
            ErrorResponseDTO errResp = new ErrorResponseDTO(e.getMessage(), e.getStatus());
            ctx.status(e.getStatus());
            ctx.json(errResp);
        });
        app.exception(Exception.class, (e, ctx) -> {
            ErrorResponseDTO errResp = new ErrorResponseDTO("Something went terribly wrong, we don't know what", 500);
            ctx.status(500);
            ctx.json(errResp);
        });


        System.out.println("Some testing");                     //        Testing method calls in the DAO
        System.out.println("user - getAll");
        System.out.println(userRepo.getAll());                  //----------Testing other classes output
        System.out.println("user - getLoginInfo");
        System.out.println(accountRepo.getLoginInfo());         //----------Testing other classes output
        System.out.println(transRepo.getAll());                 //----------Testing other classes output
        System.out.println(transRepo.getById(17));              //----------Testing other classes output
        System.out.println(accountRepo.getAll());               //----------Testing other classes output
        System.out.println(bankaccountRepo.getAll());           //----------Testing other classes output
        System.out.println(bankaccountRepo.getById(4));         //----------Testing other classes output
        System.out.println(bankaccountRepo.getBankaccount(String.valueOf(773327)));   //----------Testing other classes output
        System.out.println("transactions by date");
        System.out.println(transRepo.getByDate("2022-02-02"));   //----------Testing other classes output
        System.out.println(bankaccountRepo.closeAccount("292854"));   //----------Testing other classes output 773327

        //Testing --        Adding a transaction
        Transaction fakeTranaction= new Transaction("deposit","Cash deposit in person suffulk branch",10000,"66899");
        System.out.println(transRepo.save(fakeTranaction));   //----------Testing other classes output
        User fakeUser= new User ("chris.jones", "p@$$w0rd123", 1, "chris.jones@revature.net");
        System.out.println(accountRepo.save(fakeUser));   //----------Testing other classes output

        // Testing--identify the user and mark which acccount in a cookie
        CookieDAO cookieUser = CookieDAO.CookieDAO(app, connectionManager);      // set5 and get a cookie
        // CookieDAO daocookie = new CookieDAO(connectionManager);
        cookieUser.getMyCookie(app);
        System.out.println("COOKIE DETAIL");
        String [] identification = new String[5];                   //Testing the cookie in an Array
        identification = cookieUser.cookieInfo();
        for (int i = 0; i < identification. length; i++) {          //Print out the details stored in the cookie
            System.out.println(identification[i]);
        }
    }




//////////////////////////////////////////////------------------------------------//////////////////////////////////////////////////////////////////////
    // Option B -- Without authentication

    static void loadWebApplicationWithout(){

        //setuo database connection
        Properties connectionManagerProps = new Properties();
        connectionManagerProps.setProperty("db.username", "postgres");
        connectionManagerProps.setProperty("db.password","");   //I have it as blank in my localhost but it should be p@$$w0rd
        connectionManagerProps.setProperty("db.url","jdbc:postgresql://localhost:5432/postgres");    //postgres JDBC url -- every driver has its own format-- I am connecting to localhost because I have a local postgres server
        ConnectionManager connectionManager = new PostgresConnectionManager();
        try {
            connectionManager.init(connectionManagerProps);
            Connection conn = connectionManager.getConnection(); // to see if we got a connection
            System.out.println("Successfully connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }


        Javalin app = Javalin.create().start(8080);                    // setup the Javalin app without Access Manager configuration

        // create all dependencies at this level to control how they get used downstream (DEPENDENCY INJECTION)
        TransactionRepositoryDAO transRepo = new PostgresTransactionsDAO(connectionManager);     //mod - adding repo
        UserRepositoryDAO accountRepo = new PostgresUsersDAO(connectionManager);               // PostgresDao
        BankaccountRepositoryDAO bankaccountRepo = new PostgresbankaccountsDAO(connectionManager);               // PostgresDao
        TransactionService transSvc = new TransactionService(transRepo);          //mod - adding service
        UserService accountSvc = new UserService(accountRepo);          //mod - adding service
        BankaccountService bankaccountSvc = new BankaccountService(bankaccountRepo);          //mod - adding service

        HomePageDAO homePage = new HomePageDAO();       // setup the Home Page with API information

        // setup the paths to the different controllers
        app.get("/", ctx -> ctx.result( homePage.getMainMessage()));
        app.routes(() ->{
            crud("banking/{id}", new TransactionsController(transSvc));
            crud("useradmin/banking/{id}", new UserController(accountSvc));
            crud("bankaccountadmin/banking/{id}", new BankaccountsController(bankaccountSvc));
        });

    }

}
