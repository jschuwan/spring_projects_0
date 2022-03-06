package info.schuwan.controllers;

import info.schuwan.dao.PostgresTransactionsDAO;
import info.schuwan.dao.RepositoryDAO;
import info.schuwan.dto.CreateTransactionDTO;
import info.schuwan.dto.TransactionDTO;
import info.schuwan.models.Transaction;
import info.schuwan.services.TransactionService;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.NotFoundResponse;
import org.jetbrains.annotations.NotNull;


public class TransactionsController implements CrudHandler {

    private TransactionService service;

    public TransactionsController(TransactionService service) {        // constructor
        this.service = service;
    }

    @Override
    public void create(@NotNull Context context) {      //-------------1- CRUD

        CreateTransactionDTO addNewTrans = context.bodyAsClass(CreateTransactionDTO.class);
        int addNewId = service.save(addNewTrans.getTransaction());
        context.header("Location", "http://localhost:8080/banking/" + addNewId);
        context.status(201);

        System.out.println("BankUserController--transaction--created");
        System.out.println(addNewTrans.getTransaction());

    }

    @Override
    public void delete(@NotNull Context context, @NotNull String s) {    //-------------2- CRUD

        throw new ForbiddenResponse("USER does not have access to this, you must be an ADMIN");
    }


    @Override
    public void getAll(@NotNull Context context) {      //-------------3- CRUD

        RepositoryDAO<Integer, Transaction> bankTransactions = new PostgresTransactionsDAO();
        context.json(bankTransactions);            //context.result("You made it");
    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {      //-------------4- CRUD

        RepositoryDAO<Integer, Transaction> bankTransactions = new PostgresTransactionsDAO();

        int id = context.pathParamAsClass("id", Integer.class).get();
        Transaction trx = bankTransactions.getById(id);

        if( trx != null) {
            TransactionDTO trans = new TransactionDTO(trx);
            context.json(trans);
            return;
        }
        throw new NotFoundResponse("a bankTransactions with an id [" + id + "] was not found");
    }


    @Override
    public void update(@NotNull Context context, @NotNull String s) {      //-------------5- CRUD

        throw new ForbiddenResponse("USER does not have access to this, you must be an ADMIN");
    }
}
