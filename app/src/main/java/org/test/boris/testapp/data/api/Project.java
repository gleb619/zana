package org.test.boris.testapp.data.api;

import org.test.boris.testapp.R;
import org.test.boris.testapp.data.api.domain.entity.other.GridItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;

/**
 * Created by BORIS on 26.07.2015.
 */
public class Project {

    private String END_POINT = "http://192.168.1.75:8081/tourism-rcr-server";
    private String DATE_FORMAT = "yyyy-MM-DD";
    private String DATE_FORMAT_FULL = "yyyy-MM-DD HH:MM";
    private RestAdapter.LogLevel LOG_LEVEL = RestAdapter.LogLevel.NONE;
    private int HTTP_CONNECT_TIMEOUT = 6000; // milliseconds
    private int HTTP_READ_TIMEOUT = 10000; // milliseconds
    private long timeout = 1;
    private long retry = 3;
    private TimeUnit timeoutLatency = TimeUnit.SECONDS;
    public EntityCodes ENTITY_CODE = new EntityCodes();
    private List<GridItem> modules = new ArrayList<GridItem>() {{
        add(new GridItem(ENTITY_CODE.CURR_COURSE, R.drawable.finance_cash_receiving, "CurrCourse"));
    }};

    public Project() {

    }

    public String getEND_POINT() {
        return END_POINT;
    }

    public void setEND_POINT(String END_POINT) {
        this.END_POINT = END_POINT;
    }

    public String getDATE_FORMAT() {
        return DATE_FORMAT;
    }

    public void setDATE_FORMAT(String DATE_FORMAT) {
        this.DATE_FORMAT = DATE_FORMAT;
    }

    public String getDATE_FORMAT_FULL() {
        return DATE_FORMAT_FULL;
    }

    public void setDATE_FORMAT_FULL(String DATE_FORMAT_FULL) {
        this.DATE_FORMAT_FULL = DATE_FORMAT_FULL;
    }

    public RestAdapter.LogLevel getLOG_LEVEL() {
        return LOG_LEVEL;
    }

    public void setLOG_LEVEL(RestAdapter.LogLevel LOG_LEVEL) {
        this.LOG_LEVEL = LOG_LEVEL;
    }

    public int getHTTP_CONNECT_TIMEOUT() {
        return HTTP_CONNECT_TIMEOUT;
    }

    public void setHTTP_CONNECT_TIMEOUT(int HTTP_CONNECT_TIMEOUT) {
        this.HTTP_CONNECT_TIMEOUT = HTTP_CONNECT_TIMEOUT;
    }

    public int getHTTP_READ_TIMEOUT() {
        return HTTP_READ_TIMEOUT;
    }

    public void setHTTP_READ_TIMEOUT(int HTTP_READ_TIMEOUT) {
        this.HTTP_READ_TIMEOUT = HTTP_READ_TIMEOUT;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public TimeUnit getTimeoutLatency() {
        return timeoutLatency;
    }

    public void setTimeoutLatency(TimeUnit timeoutLatency) {
        this.timeoutLatency = timeoutLatency;
    }

    public long getRetry() {
        return retry;
    }

    public void setRetry(long retry) {
        this.retry = retry;
    }

    public List<GridItem> getModules() {
        return modules;
    }

    public void setModules(List<GridItem> modules) {
        this.modules = modules;
    }

    //    ---------------------------------------------

    public int getLastId(int moduleName) {
        for (GridItem gridItem : modules) {
            if(gridItem.getCode() == moduleName){
                return gridItem.getLastId();
            }
        }

        return -1;
    }

    public void setLastId(int lastId, int moduleName) {
        for (GridItem gridItem : modules) {
            if(gridItem.getCode() == moduleName){
                gridItem.setLastId(lastId);
            }
        }
    }

    //    ---------------------------------------------

    public class EntityCodes {

        public static final int  CURR_COURSE = 1;
        public static final int  OTHER = 0;

    }

}
