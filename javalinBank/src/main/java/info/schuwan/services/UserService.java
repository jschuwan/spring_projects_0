package info.schuwan.services;

import info.schuwan.dao.TransactionRepositoryDAO;
import info.schuwan.dao.UserRepositoryDAO;
import info.schuwan.dto.ListTranactionDTO;
import info.schuwan.dto.ListUserDTO;
import info.schuwan.dto.TransactionDTO;
import info.schuwan.models.Transaction;
import info.schuwan.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {


    private UserRepositoryDAO userRepDAO;

    public UserService(UserRepositoryDAO dao) {                              //constructor
        this.userRepDAO = dao;
    }


    public Integer save(User user) {
        return userRepDAO.save(user);
    }




    public User getById(Integer userid){
        return userRepDAO.getById(userid);
    }



    public User getByUsername(String username){
        return userRepDAO.getByUsername(username);
    }





    public ListUserDTO getAll() {

        List<User> users = userRepDAO.getAll();
        ListUserDTO listDTO = new ListUserDTO();
        listDTO.setUsers(new ArrayList<>());

        return listDTO;

    }

}

