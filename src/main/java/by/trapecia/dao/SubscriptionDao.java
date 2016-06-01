package by.trapecia.dao;

import by.trapecia.model.Subscription;

import java.util.ArrayList;

/**
 * Created by Denis on 15.12.2015.
 */
public interface SubscriptionDao {
    Integer saveSubscription(Subscription subscription, Integer clientId)  throws Exception;
    ArrayList<Subscription> getCurrent(Integer clientId) throws Exception;
    void update(Subscription subscription) throws Exception;
    Subscription getFName(Subscription subscription) throws Exception;
    void updateCounter(Subscription subscription) throws Exception;
    Subscription load(Integer subscriptionId) throws Exception;
}
