package by.trapecia.dao;

import by.trapecia.Main;
import by.trapecia.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 26.11.2015.
 */


public class ClientDaoImpl implements ClientDao {

    private static Logger log = Logger.getLogger(Main.class.getName());

    public int clientId;
    private ConnectionPool cp;

    public ClientDaoImpl() throws Exception {
        cp = ConnectionPool.getInstance();
    }
    @Override
    public Integer save(Client client) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            connection = cp.getConnection();
            ps = connection.prepareStatement(
                    "INSERT INTO client VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.firstName);
            ps.setString(2, client.middleName);
            ps.setString(3, client.lastName);
            ps.setString(4, client.email);
            ps.setString(5, client.phone);
            ps.setString(6, client.document);
            if (client.birthDate == ""){
                client.birthDate = "0001-01-01";
                ps.setString(7, client.birthDate);
            }
            ps.setString(7, client.birthDate);
            ps.setString(8, client.registrationDate);
            ps.setString(9, client.sex);
            ps.setString(10, client.knowFrom);
            ps.setInt(11, client.parentAgreed);
            ps.executeUpdate();
            result = ps.getGeneratedKeys();
            result.next();
            clientId = result.getInt(1);
            return clientId;
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
    public Client load(Integer clientId) throws Exception {
        Connection connection = null;
        Statement st = null;
        ResultSet result = null;
        Client client = new Client();
        PreparedStatement ps = null;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT * FROM client WHERE client_id=? ";
            ps = connection.prepareStatement(selectStatement);
            ps.setInt(1, clientId);
            result = ps.executeQuery();
            while (result.next()) {
                client.clientId = result.getInt("client_id");
                client.firstName = result.getString("first_name");
                client.middleName = result.getString("middle_name");
                client.lastName = result.getString("last_name");
                client.email = result.getString("email");
                client.phone = result.getString("phone");
                client.document = result.getString("document");
                client.birthDate = result.getString("birth_date");
                client.registrationDate = result.getString("registration_date");
                client.sex = result.getString("sex");
                client.knowFrom = result.getString("know_from");
                client.parentAgreed = result.getInt("parentAgreed");
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }  finally {
            if(result != null)result.close();
            if(ps != null)ps.close();
            cp.returnConnection(connection);
        }
        return  client;
    }

    @Override
    public void update(Client client) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            connection = cp.getConnection();
            ps = connection.prepareStatement(
                    "UPDATE client SET  first_name = ?, middle_name = ?, last_name = ?, email = ?, phone = ?, document = ?," +
                            " birth_date = ?, registration_date = ?, sex = ?, know_from = ?, parentAgreed = ? WHERE client_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.firstName);
            ps.setString(2, client.middleName);
            ps.setString(3, client.lastName);
            ps.setString(4, client.email);
            ps.setString(5, client.phone);
            ps.setString(6, client.document);
            ps.setString(7, client.birthDate);
            ps.setString(8, client.registrationDate);
            ps.setString(9, client.sex);
            ps.setString(10, client.knowFrom);
            ps.setInt(11, client.parentAgreed);
            ps.setInt(12, client.clientId);
            System.out.println(client.clientId);
            ps.executeUpdate();
            result = ps.getGeneratedKeys();
            result.next();
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
    public void delete(Integer clientId) throws Exception {
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet result = null;
            try {
                connection = cp.getConnection();
                ps = connection.prepareStatement(
                        "DELETE FROM client  WHERE client_id = ?", Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, clientId);
                ps.executeUpdate();
                result = ps.getGeneratedKeys();
                result.next();
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
    public ArrayList<Client> loadClimbingNow() {
        ArrayList<Client> clients = new ArrayList<Client>();
        Connection connection = null;
        Statement st = null;
        ResultSet result;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT * FROM client" +
                    "     WHERE client_id IN (SELECT client_id FROM service" +
                    "                  WHERE check_out = '0000-00-00 00:00:00')";
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            result = ps.executeQuery();
            while (result.next()) {
                Client client = new Client();
                client.clientId = result.getInt("client_id");
                client.firstName = result.getString("first_name");
                client.middleName = result.getString("middle_name");
                client.lastName = result.getString("last_name");
                client.email = result.getString("email");
                client.phone = result.getString("phone");
                client.document = result.getString("document");
                client.birthDate = result.getString("birth_date");
                client.registrationDate = result.getString("registration_date");
                client.sex = result.getString("sex");
                client.knowFrom = result.getString("know_from");
                client.parentAgreed = result.getInt("parentAgreed");

                clients.add(client);
            }
            result.close();

        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }   finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Exception: ", e);
            }
        }
        return  clients;
    }

    public ArrayList<String> getSearchList (String lastName) {
        ArrayList<String> list = new ArrayList<String>();
        Connection connection = null;
        PreparedStatement ps = null;
        String data;
        try {
            connection = cp.getConnection();
            ps = connection
                    .prepareStatement("SELECT * FROM client  WHERE last_name  LIKE ?");
            ps.setString(1, lastName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data = rs.getString("last_name")+" "+rs.getString("first_name")+" "+rs.getString("client_id");
                list.add(data);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Exception: ", e);
            }
        }
        return list;
    }

}
