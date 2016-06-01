package by.trapecia.gson.deserialize;

import by.trapecia.Main;
import by.trapecia.model.Rent;
import by.trapecia.model.Subscription;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 15.12.2015.
 */
public class RentDeserializer implements JsonDeserializer<Rent>{

    private static Logger log = Logger.getLogger(Main.class.getName());

        @Override
        public Rent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = json.getAsJsonObject();
            Rent rent = new Rent();
try {

            if(jsonObject.get("climbingShoes")  != null) {
                rent.setClientId(jsonObject.get("clientId").getAsInt());
                rent.setClimbingShoes(jsonObject.get("climbingShoes").getAsInt());
                rent.setHarness(jsonObject.get("harness").getAsInt());
                rent.setMagnesia(jsonObject.get("magnesia").getAsInt());
                rent.setCarabine(jsonObject.get("carabine").getAsInt());
                rent.setGriGri(jsonObject.get("griGri").getAsInt());
            if (rent.climbingShoes == 0 && rent.harness == 0 && rent.magnesia == 0 && rent.carabine == 0 && rent.griGri == 0){
                rent.clientId = 0;}
            }}
catch (JsonParseException j){
    log.log(Level.SEVERE, "Exception: ", j);
}
                return rent;

        }

    }

