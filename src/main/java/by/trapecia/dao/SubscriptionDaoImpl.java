package by.trapecia.dao;

import by.trapecia.Main;
import by.trapecia.model.Subscription;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 15.12.2015.
 */
public class SubscriptionDaoImpl implements SubscriptionDao {

    private static Logger log = Logger.getLogger(Main.class.getName());

    public int subscriptionId;
    private ConnectionPool cp;

    public SubscriptionDaoImpl() throws Exception {
        cp = ConnectionPool.getInstance();
    }

    @Override
    public Integer saveSubscription(Subscription subscription, Integer clientId) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            connection = cp.getConnection();
            ps = connection.prepareStatement(
                    "INSERT INTO subscription VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, subscription.type);
            ps.setInt(2, clientId);
            ps.setString(3, subscription.saleTime);
            ps.setString(4, subscription.firstDate);
            ps.setString(5, subscription.lastDate);
            ps.setInt(6, subscription.counter);
            ps.setInt(7, subscription.current);
            ps.executeUpdate();
            result = ps.getGeneratedKeys();
            result.next();
            subscription.subscriptionId = result.getInt(1);
            return subscription.subscriptionId;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            throw new Exception("Can't execute sql", e);
        } finally {
            if (result != null) result.close();
            if (ps != null) ps.close();
            cp.returnConnection(connection);
        }

    }

    @Override
    public ArrayList<Subscription> getCurrent(Integer clientId) throws Exception {
        ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();
        Connection connection = null;
        Statement st = null;
        ResultSet result = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT * FROM subscription WHERE client_id= ? AND current = 1";
            ps = connection.prepareStatement(selectStatement);
            ps.setInt(1, clientId);
            result = ps.executeQuery();
            while (result.next()) {
                Subscription subscription = new Subscription();
                subscription.clientId = result.getInt("client_id");
                subscription.subscriptionId = result.getInt("subscription_id");
                subscription.counter = result.getInt("counter");
                subscription.firstDate = result.getString("first_date");
                subscription.type = result.getInt("subscription_type");
                subscription.current = result.getInt("current");
                subscription.lastDate = result.getString("last_date");
                subscription.saleTime = result.getString("sale_time");
                getFName(subscription);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date lastDate = format.parse(subscription.lastDate);
                Date current = new Date();
                if (!lastDate.before(current)) {
                    subscriptions.add(subscription);
                }
                else {
                    ps2 = connection.prepareStatement(
                            "UPDATE subscription SET  current = ?" +
                                    " WHERE subscription_id = ?", Statement.RETURN_GENERATED_KEYS);
                        ps2.setInt(1, (subscription.current = 0));
                    ps2.setInt(2, subscription.subscriptionId);
                    ps2.executeUpdate();

                }

            }
            result.close();

        } catch (SQLException se) {
            log.log(Level.SEVERE, "Exception: ", se);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }  finally {
            if (result != null) result.close();
            if (ps != null) ps.close();
            if (ps2 != null) ps2.close();
            cp.returnConnection(connection);
        }
        return subscriptions;
    }

    @Override
    public void update(Subscription subscription) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            connection = cp.getConnection();
            ps = connection.prepareStatement(
                    "UPDATE subscription SET  first_date = ?, last_date = ?, subscription_type = ?, sale_time = ?, counter = ?, current = ?" +
                            " WHERE subscription_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subscription.firstDate);
            ps.setString(2, subscription.lastDate);
            ps.setInt(3, subscription.type);
            ps.setString(4, subscription.saleTime);
            ps.setInt(5, subscription.counter);
            ps.setInt(6, subscription.current);
            ps.setInt(7, subscription.subscriptionId);
            System.out.println(subscription.subscriptionId);
            ps.executeUpdate();
            result = ps.getGeneratedKeys();
            result.next();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            throw new Exception("Can't execute sql", e);
        } finally {
            if (result != null) result.close();
            if (ps != null) ps.close();
            cp.returnConnection(connection);
        }

    }

    @Override
    public Subscription load(Integer subscriptionId) throws Exception {
        Connection connection = null;
        Statement st = null;
        ResultSet result = null;
        Subscription subscription = new Subscription();
        PreparedStatement ps = null;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT * FROM subscription WHERE subscription_id= ? ";
            ps = connection.prepareStatement(selectStatement);
            ps.setInt(1, subscriptionId);
            result = ps.executeQuery();
            while (result.next()) {
                subscription.lastDate = result.getString("last_date");
                subscription.firstDate = result.getString("first_date");
                subscription.type = result.getInt("subscription_type");
                subscription.saleTime = result.getString("sale_time");
                subscription.current = result.getInt("current");
                subscription.counter = result.getInt("counter");
                subscription.subscriptionId = result.getInt("subscription_id");
                subscription.clientId = result.getInt("client_id");
            }

        } catch (SQLException se) {
            log.log(Level.SEVERE, "Exception: ", se);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }  finally {
            if(result != null)result.close();
            if(ps != null)ps.close();
            cp.returnConnection(connection);
        }
        return  subscription;
    }

    @Override
    public void updateCounter(Subscription subscription) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            connection = cp.getConnection();
            ps = connection.prepareStatement(
                    "UPDATE subscription SET  counter = ?, current = ?" +
                            " WHERE subscription_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, (subscription.counter - 1));
            if (subscription.counter == 1){
                ps.setInt(2, (subscription.current = 0));
            }
            else {
                ps.setInt(2, (subscription.current = 1));
            }
            ps.setInt(3, subscription.subscriptionId);
            ps.executeUpdate();
            result = ps.getGeneratedKeys();
            result.next();

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            throw new Exception("Can't execute sql", e);
        } finally {
            if (result != null) result.close();
            if (ps != null) ps.close();
            cp.returnConnection(connection);
        }

    }

    @Override
    public Subscription getFName(Subscription subscription) throws Exception {
        switch (subscription.type) {
            case 21:
                subscription.fancyName = "Абонемент безлимитный 'дневной'";
                break;
            case 22:
                subscription.fancyName = "4 посещения в любое время";
                break;
            case 23:
                subscription.fancyName = "Абонемент безлимитный";
                break;
            case 24:
                subscription.fancyName = "Групповые занятия 4 раза в месяц";
                break;
            case 25:
                subscription.fancyName = "Групповые занятия 8 раза в месяц";
                break;
            case 26:
                subscription.fancyName = "Абонемент 'Хан-Тенгри'";
                break;
        }
        return subscription;
    }

}