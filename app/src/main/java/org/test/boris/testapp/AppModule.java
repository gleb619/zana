package org.test.boris.testapp;


import org.test.boris.testapp.activity.LoginActivity;
import org.test.boris.testapp.activity.MainActivity;
import org.test.boris.testapp.data.MainModule;
import org.test.boris.testapp.fragment.CurrCourseEditFragment;
import org.test.boris.testapp.fragment.CurrCourseListFragment;
import org.test.boris.testapp.fragment.MainMenuFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by BORIS on 26.07.2015.
 */
@Module(
        includes = {
                MainModule.class
        },
        injects = {
                LoginActivity.class,
                MainActivity.class,
                MainMenuFragment.class,
                CurrCourseListFragment.class,
                CurrCourseEditFragment.class,
                TestApp.class
        }
)
public class AppModule {

    private final TestApp app;
    public AppModule(TestApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    TestApp provideApplication() {
        return app;
    }

}
