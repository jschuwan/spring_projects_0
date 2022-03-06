package info.schuwan.dao;

import java.util.List;

public interface RepositoryDAO<ID, T> {

    ID save(T object);
    List<T> getAll();
    T getById(ID id);

  //  no updates or deletes in a bank environment

}
