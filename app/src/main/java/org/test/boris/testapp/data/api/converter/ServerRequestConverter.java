package org.test.boris.testapp.data.api.converter;

import org.test.boris.testapp.data.api.domain.entity.other.ServerRequest;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by BORIS on 26.07.2015.
 */
public class ServerRequestConverter {

    public Map<String, String> convert(ServerRequest serverRequest) throws Exception {
        Map<String, String> map = new WeakHashMap<String, String>();

        if (serverRequest.getId() != null && serverRequest.getId().toString().replaceAll("\\s+", "").length() > 0) {
            map.put("id", serverRequest.getId().toString());
        }
        if (serverRequest.getInitDicts() != null && serverRequest.getInitDicts().toString().replaceAll("\\s+", "").length() > 0) {
            map.put("initDicts", serverRequest.getInitDicts().toString());
        }
        if (serverRequest.getInitLists() != null && serverRequest.getInitLists().toString().replaceAll("\\s+", "").length() > 0) {
            map.put("initLists", serverRequest.getInitLists().toString());
        }
        if (serverRequest.getPageNumber() != null && serverRequest.getPageNumber().toString().replaceAll("\\s+", "").length() > 0) {
            map.put("pageNumber", serverRequest.getPageNumber().toString());
        }
        if (serverRequest.getPageSize() != null && serverRequest.getPageSize().toString().replaceAll("\\s+", "").length() > 0) {
            map.put("pageSize", serverRequest.getPageSize().toString());
        }
        if (serverRequest.getFilter() != null && serverRequest.getFilter().toString().replaceAll("\\s+", "").length() > 0) {
            map.put("filter", serverRequest.getFilter().toString());
        }
        if (serverRequest.getDate() != null && serverRequest.getDate().toString().replaceAll("\\s+", "").length() > 0) {
            map.put("date", serverRequest.getDate().toString());
        }
        if (serverRequest.getSync() != null && serverRequest.getSync().toString().replaceAll("\\s+", "").length() > 0) {
            map.put("sync", serverRequest.getSync().toString());
        }
        if (serverRequest.getType() != null && serverRequest.getType().toString().replaceAll("\\s+", "").length() > 0) {
            map.put("type", serverRequest.getType().toString());
        }

        return map;
    }

}
