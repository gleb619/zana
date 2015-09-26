package org.test.boris.testapp.data.api.resource;


import org.test.boris.testapp.data.api.domain.entity.CurrCourse;
import org.test.boris.testapp.data.api.domain.response.CurrCourseServerResponse;
import org.test.boris.testapp.data.api.domain.response.CurrCourseServerResponseSingle;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by BORIS on 26.07.2015.
 */
public interface CurrCourseResource {

    @GET("/rest/{key}/currcourse")
    Observable<CurrCourseServerResponse> list(@Path("key") String key, @QueryMap Map<String, String> options);

    @GET("/rest/{key}/currcourse/{id}")
    Observable<CurrCourseServerResponseSingle> findById(@Path("key") String key, @Path("id") Integer id, @QueryMap Map<String, String> options);

    @PUT("/rest/{key}/currcourse")
    Observable<CurrCourseServerResponseSingle> update(@Path("key") String key, @Body CurrCourse currCourse);

}
