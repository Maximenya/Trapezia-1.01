package by.trapecia.dao;

import by.trapecia.model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 26.11.2015.
 */
public interface ClientDao {

    Integer save(Client client) throws Exception;

    Client load(Integer clientId) throws Exception;

    void update(Client client) throws Exception;

    void delete(Integer clientId) throws Exception;

    List<Client> loadClimbingNow();

    ArrayList<String> getSearchList(String lastName) throws Exception;

}
