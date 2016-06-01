package by.trapecia.dao;

import by.trapecia.model.Rent;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Denis on 15.12.2015.
 */
public interface RentDao {
    Integer saveRent(Rent rent, Integer clientId) throws Exception;
    Rent loadRent(Integer clientId) throws Exception;
}
