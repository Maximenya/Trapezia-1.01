package by.trapecia.dao;

import by.trapecia.Main;
import by.trapecia.model.Client;
import by.trapecia.model.Subscription;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 31.05.2016.
 */
public class StatisticDaoImpl implements StatisticDao {

    SubscriptionDao subscriptionDao = new SubscriptionDaoImpl();

        private static Logger log = Logger.getLogger(Main.class.getName());

        public int clientId;
        private ConnectionPool cp;

        public StatisticDaoImpl() throws Exception {
            cp = ConnectionPool.getInstance();
        }
    @Override
    public JSONObject totalPeoples() throws Exception {
            JSONObject totalPeoples = new JSONObject();
            int all = 0;
            Connection connection = null;
            Statement st = null;
            ResultSet result;
            try {
                connection = cp.getConnection();
                String selectStatement = "SELECT sex, COUNT(*) AS 'num' FROM client group by sex";
                PreparedStatement ps = connection.prepareStatement(selectStatement);
                result = ps.executeQuery();
                while (result.next()) {
                   totalPeoples.put(result.getString("sex"), result.getInt("num"));
                    all+=result.getInt("num");
                }
                totalPeoples.put("all", all);
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
            return totalPeoples;
        }

    @Override
    public JSONObject genderAge() throws Exception {
        int M18,W18,M18_21,W18_21,M21_24,W21_24,M24_27,W24_27,M27_30,W27_30,M30_35,W30_35,M35_45,W35_45,M45,W45;
        M18=W18=M18_21=W18_21=M21_24=W21_24=M24_27=W24_27=M27_30=W27_30=M30_35=W30_35=M35_45=W35_45=M45=W45=0;
        Client client = new Client();
        DateFormat format = new SimpleDateFormat("yyyy");
        int birthDate;
        Date data;
        String yearString;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        JSONObject genderAge = new JSONObject();
        Connection connection = null;
        Statement st = null;
        ResultSet result;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT birth_date, sex FROM client";
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            result = ps.executeQuery();
            while (result.next()) {

               client.birthDate = result.getString("birth_date");
                client.sex = result.getString("sex").toUpperCase();
                data = format.parse(client.birthDate);
                yearString = format.format(data);
                birthDate = Integer.parseInt(yearString);
                int x = year-birthDate;
                    if(x<18) {
                        if (Objects.equals(client.sex, "М")) M18++;
                        else W18++;
                    }
                    else if(18<=x&&x<21) {
                        if (Objects.equals(client.sex, "М")) M18_21++;
                        else W18_21++;
                    }
                    else if(21<=x&&x<24) {
                        if (Objects.equals(client.sex, "М")) M21_24++;
                        else W21_24++;
                    }
                    else if(24<=x&&x<27) {
                            if (Objects.equals(client.sex, "М")) M24_27++;
                            else W24_27++;
                    }
                    else if(27<=x&&x<30) {
                        if (Objects.equals(client.sex, "М")) M27_30++;
                        else W27_30++;
                    }
                    else if(30<=x&&x<35) {
                        if (Objects.equals(client.sex, "М")) M30_35++;
                        else W30_35++;
                    }
                    else if(35<=x&&x<45) {
                        if (Objects.equals(client.sex, "М")) M35_45++;
                        else W35_45++;
                    }
                    else if(45<=x) {
                        if (Objects.equals(client.sex, "М")) M45++;
                        else W45++;
                    }
                }

            genderAge.put("M18",M18);
            genderAge.put("W18",W18);
            genderAge.put("M18_21",M18_21);
            genderAge.put("W18_21",W18_21);
            genderAge.put("M21_24",M21_24);
            genderAge.put("W21_24",W21_24);
            genderAge.put("M24_27",M24_27);
            genderAge.put("W24_27",W24_27);
            genderAge.put("M27_30",M27_30);
            genderAge.put("W27_30",W27_30);
            genderAge.put("M30_35",M30_35);
            genderAge.put("W30_35",W30_35);
            genderAge.put("M35_45",M35_45);
            genderAge.put("W35_45",W35_45);
            genderAge.put("M45",M45);
            genderAge.put("W45",W45);

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
        return genderAge;
    }

    @Override
    public JSONObject knowFrom() throws Exception {

        JSONObject knowFrom = new JSONObject();
        Connection connection = null;
        Statement st = null;
        ResultSet result;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT know_from, COUNT(*) AS `num` FROM client GROUP BY know_from";
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            result = ps.executeQuery();
            while (result.next()) {
                knowFrom.put(result.getString("know_from"),result.getString("num"));
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
        return knowFrom;
    }

    @Override
    public JSONObject regMonth() throws Exception {

        JSONObject regMonth = new JSONObject();
        Connection connection = null;
        Statement st = null;
        ResultSet result;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT MONTH(registration_date) AS Month, YEAR(registration_date) AS Year, COUNT(*) AS `num` \n" +
                    "FROM client\n" +
                    "WHERE YEAR(registration_date) LIKE '20%'\n" +
                    "GROUP BY MONTH(registration_date) , YEAR(registration_date)";
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            result = ps.executeQuery();
            while (result.next()) {
                regMonth.put(result.getString("Year") +'-'+ result.getString("Month"),result.getString("num"));
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
        return regMonth;
    }

    @Override
    public JSONObject popSubscr() throws Exception {
        Subscription subscription = new Subscription();
        JSONObject popSubscr = new JSONObject();
        Connection connection = null;
        Statement st = null;
        ResultSet result;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT subscription_type, COUNT(*) AS `num` FROM subscription GROUP BY subscription_type";
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            result = ps.executeQuery();
            while (result.next()) {
                subscription.type = result.getInt("subscription_type");
                subscriptionDao.getFName(subscription);
                popSubscr.put(subscription.fancyName,result.getString("num"));
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
        return popSubscr;
    }

   /* @Override
    public JSONObject regSubscr() throws Exception {

        JSONObject regSubscr,T21,T22,T23,T24,T25,T26;
        regSubscr = new JSONObject();
        T21 = new JSONObject();
        T22 = new JSONObject();
        T23 = new JSONObject();
        T24 = new JSONObject();
        T25 = new JSONObject();
        T26 = new JSONObject();

        Connection connection = null;
        Statement st = null;
        ResultSet result;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT subscription_type as Type, MONTH(sale_time) AS Month, YEAR(sale_time) AS Year, COUNT(*) AS `num` \n" +
                    "FROM subscription\n" +
                    "WHERE sale_time LIKE '2%'\n" +
                    "GROUP BY subscription_type, MONTH(sale_time) + '.' + YEAR(sale_time)";
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            result = ps.executeQuery();
            while (result.next()) {
                switch (result.getInt("Type")){
                    case 21:
                        T21.put(result.getString("Year") +'-'+ result.getString("Month"),result.getString("num"));
                        break;
                    case 22:
                        T22.put(result.getString("Year") +'-'+ result.getString("Month"),result.getString("num"));
                        break;
                    case 23:
                        T23.put(result.getString("Year") +'-'+ result.getString("Month"),result.getString("num"));
                        break;
                    case 24:
                        T24.put(result.getString("Year") +'-'+ result.getString("Month"),result.getString("num"));
                        break;
                    case 25:
                        T25.put(result.getString("Year") +'-'+ result.getString("Month"),result.getString("num"));
                        break;
                    case 26:
                        T26.put(result.getString("Year") +'-'+ result.getString("Month"),result.getString("num"));
                        break;
                }
                regSubscr.put("21", T21);
                regSubscr.put("22", T22);
                regSubscr.put("23", T23);
                regSubscr.put("24", T24);
                regSubscr.put("25", T25);
                regSubscr.put("26", T26);
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
        return regSubscr;
    }*/

    @Override
    public JSONObject attendance() throws Exception {

        JSONObject attendance = new JSONObject();
        Connection connection = null;
        Statement st = null;
        ResultSet result;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT MONTH(sale_time) AS Month, YEAR(sale_time) AS Year, COUNT(*) AS `num` \n" +
                    "FROM service WHERE YEAR(sale_time) LIKE '20%'\n" +
                    "GROUP BY MONTH(sale_time) , YEAR(sale_time)";
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            result = ps.executeQuery();
            while (result.next()) {
                attendance.put(result.getString("Year") +'-'+ result.getString("Month"),result.getString("num"));
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
        return attendance;
    }

}
