package by.trapecia;

import by.trapecia.dao.*;
import by.trapecia.gson.deserialize.RentDeserializer;
import by.trapecia.gson.deserialize.ServiceDeserializer;
import by.trapecia.gson.deserialize.SubscriptionDeserializer;
import by.trapecia.model.Client;
import by.trapecia.model.Rent;
import by.trapecia.model.Service;
import by.trapecia.model.Subscription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

import static spark.Spark.*;

public class Main {
    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        System.setProperty("file.encoding","UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null, null);
        Handler fileHandler = null;
        Formatter simpleFormatter = null;
        try{
            fileHandler = new FileHandler("./application.log", 1000000, 5);
            simpleFormatter = new SimpleFormatter();
            LOGGER.addHandler(fileHandler);
            fileHandler.setFormatter(simpleFormatter);
            fileHandler.setLevel(Level.SEVERE);
            LOGGER.setLevel(Level.SEVERE);
        }catch(IOException exception){
                    LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);}


        Gson  gson = new GsonBuilder()
                .registerTypeAdapter(Rent.class, new RentDeserializer())
                .registerTypeAdapter(Subscription.class, new SubscriptionDeserializer())
                .registerTypeAdapter(Service.class, new ServiceDeserializer())
                .create();

        ClientDao clientDao = new ClientDaoImpl();
        SubscriptionDaoImpl subscriptionDao = new SubscriptionDaoImpl();
        ServiceDaoImpl serviceDao = new ServiceDaoImpl();
        RentDaoImpl rentDao = new RentDaoImpl();
        StatisticDao statisticDao = new StatisticDaoImpl();

        port(8080);
        staticFileLocation("/public");

        get("/", (request, response) -> {
            response.redirect("/climbingList");
                return    1;
                });

        get("/addUser", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "addUser.ftl");
        }, new FreeMarkerEngine());

        post("/addUser", (request, response) -> {
            Client client =  new Client();
            client.firstName = request.queryParams("firstName");
            client.middleName = request.queryParams("middleName");
            client.lastName = request.queryParams("lastName");
            client.email = request.queryParams("eMail");
            client.phone = request.queryParams("phone");
            client.document = request.queryParams("document");
            client.sex = request.queryParams("sex");
            client.knowFrom = request.queryParams("knowFrom");
            client.birthDate = request.queryParams("birthDate");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            client.registrationDate = dateFormat.format(date);
            Integer clientId = clientDao.save(client);
            response.redirect("/visit?clientId="+clientId);
            return 1;
        });

        get("/visit", (request, response) -> {
            Client client = clientDao.load(Integer.parseInt(request.queryParams("clientId")));
            ArrayList<Subscription> subscriptions = new ArrayList<>(subscriptionDao.getCurrent(Integer.parseInt(request.queryParams("clientId"))));
                response.status(200);
                response.type("text/html");
                Map<String, Object> attributes =  new HashMap<>();
                attributes.put("client", client);
                attributes.put("subscriptions", subscriptions);
            return new FreeMarkerEngine().render(new ModelAndView(attributes, "visit.ftl"));
        });

        post("/visit", (request, response) -> {
            String json = request.body();

            Rent rent = gson.fromJson(json , Rent.class);
            if (rent.clientId != 0){
            rentDao.saveRent(rent, rent.clientId);
            }

            Subscription subscription = gson.fromJson(json, Subscription.class);
            if (subscription.subscriptionId != 0){
                Subscription subscription1 = subscriptionDao.load(subscription.subscriptionId);
                subscriptionDao.updateCounter(subscription1);
            }
           else if (subscription.clientId != 0){
            subscriptionDao.saveSubscription(subscription, subscription.clientId);
                subscriptionDao.updateCounter(subscription);}

            Service service = gson.fromJson(json, Service.class);
            if (service.clientId != 0) {
                serviceDao.saveService(service, service.clientId, subscription.subscriptionId, rent.rentId);
            }
            response.status(200);
            response.redirect("/climbingList");
            return 1;
        });

        post("/buySubscription", (request, response) -> {
            String json = request.body();
            Map<String, Object> attributes =  new HashMap<>();
            Subscription subscription = gson.fromJson(json, Subscription.class);
            if (subscription.clientId != 0){
                subscriptionDao.saveSubscription(subscription, subscription.clientId);}
            ArrayList<Subscription> subscriptions = new ArrayList<>(subscriptionDao.getCurrent(subscription.clientId));
            attributes.put("subscriptions", subscriptions);
            response.status(200);
            response.redirect("/visit?clientId="+subscription.clientId);
            return response;
        });


        get("/climbingList", (request, response) -> {
            ArrayList<Client> clients = new ArrayList<>(clientDao.loadClimbingNow());
            Integer count = clients.size();
            response.status(200);
            response.type("text/html");
            Map<String, Object> attributes =  new HashMap<>();
            attributes.put("clients", clients);
            attributes.put("count", count);
            return new FreeMarkerEngine().render(new ModelAndView(attributes, "climbingList.ftl"));
        });


        get("/exit", (request, response) -> {
            Client client = clientDao.load(Integer.parseInt(request.queryParams("clientId")));
            Rent rent = rentDao.loadRent(Integer.parseInt(request.queryParams("clientId")));
            Service service = serviceDao.loadKey(Integer.parseInt(request.queryParams("clientId")));
            response.status(200);
            response.type("text/html");
            Map<String, Object> attributes =  new HashMap<>();
            attributes.put("client", client);
            attributes.put("rent", rent);
            attributes.put("service", service);
            return new FreeMarkerEngine().render(new ModelAndView(attributes, "exit.ftl"));
        });

        post("/exit", (request, response) -> {
            serviceDao.endOfService(Integer.parseInt(request.queryParams("clientId")));
            response.redirect("/climbingList");
            return 1;
        });

        get("/editUser", (request, response) -> {
            Client client = clientDao.load(Integer.parseInt(request.queryParams("clientId")));
            ArrayList<Subscription> subscriptions = new ArrayList<>(subscriptionDao.getCurrent(Integer.parseInt(request.queryParams("clientId"))));
            response.status(200);
            response.type("text/html");
            Map<String, Object> attributes =  new HashMap<>();
            attributes.put("client", client);
            attributes.put("subscriptions", subscriptions);
            return new FreeMarkerEngine().render(new ModelAndView(attributes, "editUser.ftl"));
        });

        post("/editUser", (request, response) -> {
            Client client =  new Client();
            client.firstName = request.queryParams("firstName");
            client.middleName = request.queryParams("middleName");
            client.lastName = request.queryParams("lastName");
            client.email = request.queryParams("eMail");
            client.phone = request.queryParams("phone");
            client.document = request.queryParams("document");
            client.birthDate = request.queryParams("birthDate").toString();
            client.clientId = Integer.parseInt(request.queryParams("clientId"));
            client.registrationDate = request.queryParams("registrationDate");
            client.sex = request.queryParams("sex");
            client.knowFrom = request.queryParams("knowFrom");
            clientDao.update(client);
            response.redirect("/climbingList");
            return 1;
        });

        post("/deleteClient",(request, response) -> {
            clientDao.delete(Integer.parseInt(request.queryParams("clientId")));
            response.redirect("/climbingList");
            return 1;
        });

        post("/editSubscription", (request, response) -> {
            Subscription subscription = new Subscription();
            subscription.lastDate = request.queryParams("lastDate");
            subscription.firstDate = request.queryParams("firstDate");
            subscription.type = Integer.parseInt(request.queryParams("type"));
            subscription.saleTime = request.queryParams("saleTime");
            subscription.current = Integer.parseInt(request.queryParams("current"));
            subscription.counter = Integer.parseInt(request.queryParams("counter"));
            subscription.subscriptionId = Integer.parseInt(request.queryParams("subscriptionId"));
            subscription.clientId = Integer.parseInt(request.queryParams("clientId"));
            subscriptionDao.update(subscription);
            response.redirect("/visit?clientId=" + subscription.clientId);
            return 1;
        });

        get("/SearchController", (request, response) -> {
            String term = request.queryParams("term");
            List<String> list = clientDao.getSearchList(term);
            String searchList = gson.toJson(list);
            response.status(200);
            response.type("application/json");
            response.body(searchList);
            return searchList;
        });

        get("/StatisticController", (request, response) -> {
            JSONObject obj = new JSONObject();
            obj.put("totalPeoples", statisticDao.totalPeoples());
            JSONObject gender = new JSONObject();
            gender.put("allMen", statisticDao.allMen());
            gender.put("allWomen", statisticDao.allWomen());
            obj.put("gender", gender);
            obj.put("genderAge", statisticDao.genderAge());
            obj.put("knowFrom", statisticDao.knowFrom());
            obj.put("regMonth", statisticDao.regMonth());
            response.status(200);
            response.type("application/json");
            response.body(obj.toString());
            return obj;
        });

    }
}
