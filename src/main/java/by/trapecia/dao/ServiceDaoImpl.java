package by.trapecia.dao;

import by.trapecia.Main;
import by.trapecia.model.Service;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 09.12.2015.
 */
public class ServiceDaoImpl implements ServiceDao {

    private static Logger log = Logger.getLogger(Main.class.getName());

    private ConnectionPool cp;

    public ServiceDaoImpl() throws Exception {
        cp = ConnectionPool.getInstance();
    }


    @Override
    public void saveService(Service service, Integer clientId, Integer subscriptionId, Integer rentId) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            connection = cp.getConnection();
            if (rentId == null){
                rentId = 0;
            }
            if (subscriptionId == null){
                subscriptionId = 0;
            }
            ps = connection.prepareStatement(
                    "INSERT INTO service VALUES (DEFAULT, ?, ?, ?, ?, ?, ? , ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, clientId);
            ps.setInt(2, service.Type);
            ps.setInt(3, subscriptionId);
            ps.setInt(4, rentId);
            ps.setString(5, service.checkIn);
            ps.setString(6, service.checkOut);
            ps.setInt(7, service.keyNumber);
            ps.setString(8, service.saleTime);
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
    public Service loadKey(Integer clientId) throws Exception {
        Connection connection = null;
        Statement st = null;
        ResultSet result = null;
        Service service = new Service();
        PreparedStatement ps = null;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT * FROM service" +
                    "     WHERE service_id IN (SELECT service_id FROM service" +
                    "                  WHERE check_out = '0000-00-00 00:00:00'" +
                    "AND client_id = ?)";
            ps = connection.prepareStatement(selectStatement);
            ps.setInt(1, clientId);
            result = ps.executeQuery();
            while (result.next()) {
                service.clientId = result.getInt("client_id");
                service.Type = result.getInt("service_type");
                service.checkIn = result.getString("check_in");
                service.keyNumber = result.getInt("key_number");
                service.saleTime = result.getString("sale_time");
                service.rentId = result.getInt("rent_id");
                service.serviceId = result.getInt("service_id");
                service.subscriptionId = result.getInt("subscription_id");
                getFName(service);
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
        return service;

    }

    @Override
    public void endOfService(Integer clientId) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet result = null;
        try {
            connection = cp.getConnection();

            ps = connection.prepareStatement("SELECT check_in FROM service" +
                    "     WHERE service_id IN (SELECT service_id FROM service" +
                    "                  WHERE check_out = '0000-00-00 00:00:00'" +
                    "AND client_id = ?)",  Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, clientId);
            result = ps.executeQuery();
            result.next();
               String checkIn = result.getString("check_in");

            ps2 = connection.prepareStatement(
                    "UPDATE service SET check_out = ?,check_in =? WHERE client_id= ? AND check_out = '0000-00-00 00:00:00'", Statement.RETURN_GENERATED_KEYS);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date date = new java.util.Date();
            ps2.setString(1, dateFormat.format(date));
            ps2.setString(2, checkIn);
            ps2.setInt(3, clientId);
            ps2.executeUpdate();
            result = ps2.getGeneratedKeys();
            result.next();

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            throw new Exception("Can't execute sql", e);
        } finally {
            if(result != null)result.close();
            if(ps != null)ps.close();
            if(ps2 != null)ps2.close();
            cp.returnConnection(connection);
        }

    }

    @Override
    public Service getFName(Service service) throws Exception {
        switch (service.Type) {
            case 11:
                service.fancyName = "Разовое посещение до 16:00 понедельник-пятница";
                break;
            case 13:
                service.fancyName = "Разовое посещение 16:00-23:00 и выходные";
                break;
            case 14:
                service.fancyName = "Старт-пакет";
                break;
            case 15:
                service.fancyName = "Индивидуальная тренировка";
                break;
            case 16:
                service.fancyName = "Групповое занятие с тренером для взрослых";
                break;
            case 17:
                service.fancyName = "Семейный старт-пакет";
                break;
            case 18:
                service.fancyName = "Семейное разовое посещение";
                break;
            case 19:
                service.fancyName = "Семейное разовое посещение до 16:00 понедельник-пятница";
                break;
            case 20:
                service.fancyName = "Абонемент";
                break;
        }
        return service;
    }

}
