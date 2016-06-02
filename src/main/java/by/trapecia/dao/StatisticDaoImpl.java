package by.trapecia.dao;

import by.trapecia.Main;
import by.trapecia.model.Client;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 31.05.2016.
 */
public class StatisticDaoImpl implements StatisticDao {

        private static Logger log = Logger.getLogger(Main.class.getName());

        public int clientId;
        private ConnectionPool cp;

        public StatisticDaoImpl() throws Exception {
            cp = ConnectionPool.getInstance();
        }
        @Override
        public Integer totalPeoples() throws Exception {
            Integer totalPeoples = null;
            ArrayList<Client> clients = new ArrayList<Client>();
            Connection connection = null;
            Statement st = null;
            ResultSet result;
            try {
                connection = cp.getConnection();
                String selectStatement = "SELECT * FROM client";
                PreparedStatement ps = connection.prepareStatement(selectStatement);
                result = ps.executeQuery();
                while (result.next()) {
                    Client client = new Client();
                    clients.add(client);
                }
                result.close();
                totalPeoples = clients.size();

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
    public Integer allMen() throws Exception {
        Integer allMen = null;
        ArrayList<Client> clients = new ArrayList<Client>();
        Connection connection = null;
        Statement st = null;
        ResultSet result;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT * FROM client WHERE sex = 'М'";
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            result = ps.executeQuery();
            while (result.next()) {
                Client client = new Client();
                clients.add(client);
            }
            result.close();
            allMen = clients.size();

        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }   finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Exception: ", e);
            }
        }
        return allMen;
    }

    @Override
    public Integer allWomen() throws Exception {
        Integer allWomen = null;
        ArrayList<Client> clients = new ArrayList<Client>();
        Connection connection = null;
        Statement st = null;
        ResultSet result;
        try {
            connection = cp.getConnection();
            String selectStatement = "SELECT * FROM client WHERE sex = 'Ж'";
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            result = ps.executeQuery();
            while (result.next()) {
                Client client = new Client();
                clients.add(client);
            }
            result.close();
            allWomen = clients.size();

        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }   finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Exception: ", e);
            }
        }
        return allWomen;
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

}
