package info.schuwan.dao;

import info.schuwan.models.User;

import java.util.Map;

public interface UserRepositoryDAO extends RepositoryDAO<Integer, User> {

    User getByUsername(String username);
    Map<Integer, User> getLoginInfo();

}
