package org.test.boris.testapp.data;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squareup.okhttp.OkHttpClient;

import org.test.boris.testapp.activity.LoginActivity;
import org.test.boris.testapp.data.api.Project;
import org.test.boris.testapp.data.api.domain.entity.other.Credentials;
import org.test.boris.testapp.data.api.resource.CurrCourseResource;
import org.test.boris.testapp.data.api.util.SecurityUtil;
import org.test.boris.testapp.fragment.CurrCourseEditFragment;
import org.test.boris.testapp.fragment.CurrCourseListFragment;
import org.test.boris.testapp.fragment.MainMenuFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

/**
 * Created by BORIS on 14.08.2015.
 */
@Module(
        injects = {
                LoginActivity.class,
                MainMenuFragment.class,
                CurrCourseListFragment.class,
                CurrCourseEditFragment.class
        },
        complete = false,
        library = true
)
public class MainModule {

    @Provides
    @Singleton
    Credentials provideCredentials(){
        return new Credentials();
    }

    @Provides
    @Singleton
    SecurityUtil provideSecurityUtil(){
        return new SecurityUtil();
    }

    @Provides
    @Singleton
    Project provideProject(){
        return new Project();
    }

    @Provides
    @Singleton
    public Endpoint provideEndpoint(Project project) {
        return Endpoints.newFixedEndpoint(project.getEND_POINT());
    }

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper(Project project) {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        DateFormat dateFormat = new SimpleDateFormat(project.getDATE_FORMAT_FULL());
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
        dateFormat.setLenient(true);
        mapper.setDateFormat(dateFormat);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);

        return mapper;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Project project) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(project.getHTTP_CONNECT_TIMEOUT(), TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(project.getHTTP_READ_TIMEOUT(), TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    @Provides
    @Singleton
    public RestAdapter provideRestAdapter(Endpoint endpoint, OkHttpClient client, Project project, ObjectMapper mapper) {
        return new RestAdapter.Builder()
                .setConverter(new JacksonConverter(mapper))
                .setClient(new OkClient(client))
                .setEndpoint(endpoint)
                .setLogLevel(project.getLOG_LEVEL())
                .build();
    }

    @Provides
    @Singleton
    public CurrCourseResource proivdeCurrCourseResource(RestAdapter restAdapter) {
        return restAdapter.create(CurrCourseResource.class);
    }

}
