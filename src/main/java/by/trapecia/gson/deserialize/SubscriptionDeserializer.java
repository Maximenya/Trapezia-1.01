package by.trapecia.gson.deserialize;
        import by.trapecia.Main;
import by.trapecia.model.Subscription;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 15.12.2015.
 */
public class SubscriptionDeserializer implements JsonDeserializer<Subscription> {

    private static Logger log = Logger.getLogger(Main.class.getName());

        @Override
        public Subscription deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = json.getAsJsonObject();

            Subscription subscription = new Subscription();

            try {

            if (jsonObject.get("subscriptionId")  != null){
                subscription.setSubscriptionId(jsonObject.get("subscriptionId").getAsInt());
        }
           else if(jsonObject.get("subscriptionType")  != null) {
                subscription.setClientId(jsonObject.get("clientId").getAsInt());
                subscription.setType(jsonObject.get("subscriptionType").getAsInt());
                switch (subscription.type) {
                    case 21:
                        subscription.counter = 60;
                        break;
                    case 22:
                        subscription.counter = 4;
                        break;
                    case 23:
                        subscription.counter = 60;
                        break;
                    case 24:
                        subscription.counter = 4;
                        break;
                    case 25:
                        subscription.counter = 8;
                        break;
                    case 26:
                        subscription.counter = 60;
                        break;
                }

                subscription.setFirstDate(jsonObject.get("firstDate").getAsString());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, 30);
                Date d = c.getTime();
                subscription.setLastDate(dateFormat.format(d));
                Date date = new Date();
                subscription.setSaleTime(dateFormat.format(date));
                subscription.current = 1;
            }
            } catch (JsonParseException j){
                log.log(Level.SEVERE, "Exception: ", j);
            }
            return subscription;
        }

}

//{"clientId":33, "subscriptionType":21, "firstDate":"2015-12-11", "lastDate":"2016-01-11", "saleTime":"2015-01-11"}
