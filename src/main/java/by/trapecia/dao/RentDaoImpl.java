package by.trapecia.dao;

import by.trapecia.Main;
import by.trapecia.model.Client;
import by.trapecia.model.Rent;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 15.12.2015.
 */
public class RentDaoImpl implements RentDao {

    private static Logger log = Logger.getLogger(Main.class.getName());

    private ConnectionPool cp;

    public RentDaoImpl() throws Exception {
        cp = ConnectionPool.getInstance();
    }
    @Override
    public Integer saveRent(Rent rent, Integer clientId) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            connection = cp.getConnection();
            ps = connection.prepareStatement(
                    "INSERT INTO rent VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, clientId);
            ps.setInt(2, rent.climbingShoes);
            ps.setInt(3, rent.harness);
            ps.setInt(4, rent.magnesia);
            ps.setInt(5, rent.carabine);
            ps.setInt(6, rent.griGri);
            ps.executeUpdate();
            result = ps.getGeneratedKeys();
            result.next();
            rent.rentId = result.getInt(1);
            return rent.rentId;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            throw new Exception("Can't execute sql", e);
        } finally {
            if(result != null)result.close();
            if(ps != null)ps.close();
            cp.returnConnection(connection);
        }

    }

    @Override
    public Rent loadRent(Integer clientId) throws Exception {
        Connection connection = null;
        Statement st = null;
        ResultSet result = null;
        Rent rent = new Rent();
        PreparedStatement ps = null;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT * FROM rent" +
                    "     WHERE rent_id IN (SELECT rent_id FROM service" +
                    "                  WHERE check_out = '0000-00-00 00:00:00'" +
                    "AND client_id = ?)";
            ps = connection.prepareStatement(selectStatement);
            ps.setInt(1, clientId);
            result = ps.executeQuery();
            while (result.next()) {
                rent.clientId = result.getInt("client_id");
                rent.rentId = result.getInt("rent_id");
                rent.climbingShoes = result.getInt("climbing_shoes");
                rent.magnesia = result.getInt("magnesia");
                rent.carabine = result.getInt("carabine");
                rent.griGri = result.getInt("gri_gri");
                rent.harness = result.getInt("harness");
            }
            result.close();

        } catch (SQLException se) {
            log.log(Level.SEVERE, "Exception: ", se);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        } finally {
            if(result != null)result.close();
            if(ps != null)ps.close();
            cp.returnConnection(connection);
        }
       return rent;

    }
}



