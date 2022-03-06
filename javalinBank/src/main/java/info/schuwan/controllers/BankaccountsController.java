package info.schuwan.controllers;

import info.schuwan.dao.BankaccountRepositoryDAO;
import info.schuwan.dao.PostgresbankaccountsDAO;
import info.schuwan.dto.BankaccountDTO;
import info.schuwan.dto.CreateBankaccountDTO;
import info.schuwan.models.BankAccount;
import info.schuwan.services.BankaccountService;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jetbrains.annotations.NotNull;


public class BankaccountsController implements CrudHandler {

    private BankaccountService service;

    public BankaccountsController(BankaccountService service) {        // constructor
        this.service = service;
    }

    @Override
    public void create(@NotNull Context context) {      //-------------1- CRUD

        CreateBankaccountDTO addNewBankaccount = context.bodyAsClass(CreateBankaccountDTO.class);
        int addNewId = service.save(addNewBankaccount.getAccount()); //mod- populate new greet
        context.header("Location", "http://localhost:8080/bankaccountadmin/banking/" + addNewId);
        context.status(201);

        System.out.println("BankEmployeeController--account--created");
        System.out.println(addNewBankaccount.getAccount());

    }

    @Override
    public void delete(@NotNull Context context, @NotNull String s) {    //-------------2- CRUD
    }

    @Override
    public void getAll(@NotNull Context context) {                       //-------------3- CRUD

        BankaccountRepositoryDAO bankAccounts = new PostgresbankaccountsDAO();
        context.json(bankAccounts);            //context.result("You made it");

    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {      //-------------4- CRUD

        BankaccountRepositoryDAO bankaccount = new PostgresbankaccountsDAO();
        int id = context.pathParamAsClass("id", Integer.class).get();
        BankAccount usx = bankaccount.getById(id);
        if( usx != null) {
            BankaccountDTO account = new BankaccountDTO(usx);
            context.json(account);
            return;
        }
        throw new NotFoundResponse("a bankaccount with an id [" + id + "] was not found");
    }

    @Override
    public void update(@NotNull Context context, @NotNull String s) {     //-------------5- CRUD

    }
}
