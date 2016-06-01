package by.trapecia.dao;

import by.trapecia.Main;
import by.trapecia.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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

}
