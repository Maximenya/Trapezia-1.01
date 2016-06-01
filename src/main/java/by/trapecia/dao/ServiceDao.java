package by.trapecia.dao;

import by.trapecia.model.Service;

/**
 * Created by Denis on 09.12.2015.
 */
public interface ServiceDao {
    void saveService(Service service, Integer clientId, Integer subscriptionId, Integer rentId) throws Exception;
    void  endOfService(Integer clientId) throws Exception;
    Service loadKey(Integer clientId) throws Exception;
    Service getFName(Service service) throws Exception;
}
