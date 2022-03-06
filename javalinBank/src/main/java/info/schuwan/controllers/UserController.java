package info.schuwan.controllers;

import info.schuwan.dao.PostgresUsersDAO;
import info.schuwan.dao.RepositoryDAO;
import info.schuwan.dao.UserRepositoryDAO;
import info.schuwan.dto.BankUserDTO;
import info.schuwan.dto.CreateUserDTO;
import info.schuwan.models.User;
import info.schuwan.services.UserService;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jetbrains.annotations.NotNull;

public class UserController implements CrudHandler {

    private UserService service;        //mod-  adding service

    public UserController(UserService service) {        // constructor
        this.service = service;
    }

    @Override
    public void create(@NotNull Context context) {      //-------------1- CRUD

        CreateUserDTO addNewUser = context.bodyAsClass(CreateUserDTO.class);
        int addNewId = service.save(addNewUser.getUser()); //mod- populate new greet
        context.header("Location", "http://localhost:8080/useradmin/banking/" + addNewId);
        context.status(201);

        System.out.println("BankEmployeeController--account--created");                             //    Testing
        System.out.println(addNewUser.getUser());
    }

    @Override
    public void getAll(@NotNull Context context) {      //-------------3- CRUD


        CreateUserDTO addNewUser = context.bodyAsClass(CreateUserDTO.class);
        UserRepositoryDAO bankAccounts = new PostgresUsersDAO();     // PostgresDao
        context.json(bankAccounts);            //context.result("You made it");
    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {      //-------------4- CRUD

        RepositoryDAO<Integer, User> userAccount = new PostgresUsersDAO();
        int id = context.pathParamAsClass("id", Integer.class).get();
        User usx = userAccount.getById(id);
        if( usx != null) {
            BankUserDTO account = new BankUserDTO(usx);
            context.json(account);
            return;
        }
        throw new NotFoundResponse("a userAccount with an id [" + id + "] was not found");
    }

    //  No updates or deletes in a banking system
    @Override
    public void update(@NotNull Context context, @NotNull String s) {     //-------------5- CRUD
    }

    @Override
    public void delete(@NotNull Context context, @NotNull String s) {    //-------------2- CRUD
    }
}
