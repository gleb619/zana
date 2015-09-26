package org.test.boris.testapp.data.api.domain.entity.other;

/**
 * Created by BORIS on 26.07.2015.
 */
public class ServerRequest {

    private Integer id;
    private Boolean initDicts;
    private Boolean initLists;
    private Boolean onlyUserData;
    private Integer pageNumber;
    private Integer pageSize;
    private String filter;
    private String date;
    private Boolean sync;
    private String type;

    public ServerRequest(String filter) {
        this.filter = filter;
    }

    public ServerRequest() {

    }

    public Integer getId() {
        return id;
    }

    public ServerRequest setId(Integer id) {
        this.id = id;
        return this;
    }

    public Boolean getInitDicts() {
        return initDicts;
    }

    public ServerRequest setInitDicts(Boolean initDicts) {
        this.initDicts = initDicts;
        return this;
    }

    public Boolean getInitLists() {
        return initLists;
    }

    public ServerRequest setInitLists(Boolean initLists) {
        this.initLists = initLists;
        return this;
    }

    public Boolean getOnlyUserData() {
        return onlyUserData;
    }

    public ServerRequest setOnlyUserData(Boolean onlyUserData) {
        this.onlyUserData = onlyUserData;
        return this;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public ServerRequest setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public ServerRequest setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String getFilter() {
        return filter;
    }

    public ServerRequest setFilter(String filter) {
        this.filter = filter;
        return this;
    }

    public String getDate() {
        return date;
    }

    public ServerRequest setDate(String date) {
        this.date = date;
        return this;
    }

    public Boolean getSync() {
        return sync;
    }

    public ServerRequest setSync(Boolean sync) {
        this.sync = sync;
        return this;
    }

    public String getType() {
        return type;
    }

    public ServerRequest setType(String type) {
        this.type = type;
        return this;
    }


    public ServerRequest firstPage(){
        this.pageNumber = 1;
        this.pageSize = 10;

        return this;
    }

    public ServerRequest loadById(int id){
        this.initDicts = false;
        this.initLists = false;
        this.id = id;

        return this;
    }

}
