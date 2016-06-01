package by.trapecia.gson.deserialize;

import by.trapecia.Main;
import by.trapecia.model.Rent;
import by.trapecia.model.Service;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 15.12.2015.
 */
public class ServiceDeserializer implements JsonDeserializer<Service> {

    private static Logger log = Logger.getLogger(Main.class.getName());

        @Override
        public Service deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = json.getAsJsonObject();

            Service service = new Service();
try {
            if(jsonObject.get("key")  != null) {
                service.setClientId(jsonObject.get("clientId").getAsInt());
                service.setType(jsonObject.get("service").getAsInt());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                service.setCheckIn(dateFormat.format(date));
                service.setKeyNumber(jsonObject.get("key").getAsInt());
                service.setSaleTime(dateFormat.format(date));
                service.checkOut = "0000-00-00 00:00:00";
            }}
catch (JsonParseException j){
    log.log(Level.SEVERE, "Exception: ", j);
}
            return service;
        }

    }

// "serviceType":20, "checkIn":"1973-12-30 15:30:00", "keyNumber":44, "saleTime":"1973-12-30 15:30:00"
