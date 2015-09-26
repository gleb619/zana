package org.test.boris.testapp;

import android.support.multidex.MultiDexApplication;

import dagger.ObjectGraph;

/**
 * Created by BORIS on 26.07.2015.
 */
public class TestApp extends MultiDexApplication {
//public class App extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
//        new Runnable() {
//            @Override
//            public void run() {
//                objectGraph = ObjectGraph.create(new AppModule(test()));
        //        objectGraph = ObjectGraph.create(Arrays.asList(
        //                new AppModule(this))
        //                new ModelService(),
        //                new SecurityService()
        //        );
//                objectGraph.inject(this);
//            }
//        }.run();
        objectGraph = ObjectGraph.create(new AppModule(this));
        objectGraph.inject(this);
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }

    public TestApp test() {
        return this;
    }


}
