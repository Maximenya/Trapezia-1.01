package by.trapecia.dao;

import org.json.JSONObject;

/**
 * Created by Denis on 31.05.2016.
 */
public interface StatisticDao {
    JSONObject totalPeoples() throws Exception;
    JSONObject genderAge() throws Exception;
    JSONObject knowFrom() throws Exception;
    JSONObject regMonth() throws Exception;
    JSONObject popSubscr() throws Exception;
}
