package org.test.boris.testapp.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.test.boris.testapp.R;
import org.test.boris.testapp.TestApp;
import org.test.boris.testapp.activity.MainActivity;
import org.test.boris.testapp.data.api.Project;
import org.test.boris.testapp.data.api.converter.ServerRequestConverter;
import org.test.boris.testapp.data.api.domain.entity.CurrCourse;
import org.test.boris.testapp.data.api.domain.entity.other.Credentials;
import org.test.boris.testapp.data.api.domain.entity.other.ServerRequest;
import org.test.boris.testapp.data.api.domain.response.CurrCourseServerResponseSingle;
import org.test.boris.testapp.data.api.resource.CurrCourseResource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CurrCourseEditFragment extends Fragment {

    @Inject CurrCourseResource currCourseResource;
    @Inject TestApp app;
    @Inject Credentials credentials;
    @Inject Project project;
    private CurrCourse currCourse;

    @Bind(R.id.curr_course_datecourse) EditText currCourseDatecourse;
    @Bind(R.id.curr_course_course) EditText currCourseCourse;

    @Bind(R.id.curr_course_edit_pane) View currCourseEditPane;
    @Bind(R.id.curr_course_control_pane) View currCourseControlPane;
    @Bind(R.id.curr_course_progress_bar_edit) View currCourseProgressBarEdit;

    @Bind(R.id.curr_course_submit) Button currCourseSubmit;
    @Bind(R.id.curr_course_cancel) Button currCourseCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData(new ServerRequest().loadById(project.getLastId(project.ENTITY_CODE.CURR_COURSE)));
    }

    private void loadData(ServerRequest serverRequest){
        if (serverRequest.getId() != null && serverRequest.getId() > -1) {
            showProgress(true);

            Map<String, String> request = new HashMap<>();
            try {
                request = new ServerRequestConverter().convert(serverRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

            currCourseResource.findById(credentials.getKey(), serverRequest.getId(), request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(project.getTimeout(), project.getTimeoutLatency())
                .retry(project.getRetry())
                .onErrorResumeNext(onLoadError())
                .subscribe(new Subscriber<CurrCourseServerResponseSingle>() {
                    @Override
                    public void onCompleted() {
                        showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e);
                    }

                    @Override
                    public void onNext(CurrCourseServerResponseSingle response) {
                        setValues(response);
                    }
                });
        }
    }

    @OnClick(R.id.curr_course_submit)
    public void updateData(){
        try {
            update(getValues());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.curr_course_cancel)
    public void cancelData(){
        try {
            getActivity().moveTaskToBack(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(CurrCourse currCourse){
        if (currCourse != null && currCourse.getId() > -1) {
            showProgress(true);

            currCourseResource.update(credentials.getKey(), currCourse)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(project.getTimeout(), project.getTimeoutLatency())
                .retry(project.getRetry())
//                .onErrorResumeNext(onLoadError())
                .subscribe(new Subscriber<CurrCourseServerResponseSingle>() {
                    @Override
                    public void onCompleted() {
                        showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e);
                    }

                    @Override
                    public void onNext(CurrCourseServerResponseSingle response) {
                        if (!response.getSuccess()) {
                            showError(response.getMessage());
                        }
                    }
                });
        }
    }

    private Func1<Throwable, Observable<CurrCourseServerResponseSingle>> onLoadError() {
        return new Func1<Throwable, Observable<CurrCourseServerResponseSingle>>() {
            @Override
            public Observable<CurrCourseServerResponseSingle> call(Throwable throwable) {
                CurrCourse currCourse = new CurrCourse();
                currCourse.setCourse(0);
                currCourse.setDatecourse(new Date());

                final CurrCourseServerResponseSingle response = new CurrCourseServerResponseSingle();
                response.setSuccess(false);
                response.setMessage("Can't connect to the server");

                System.err.println(this.getClass().getName() + "\n" + "----------------------------------------");
                throwable.printStackTrace();
                System.err.println(this.getClass().getName() + "\n" + "----------------------------------------");

                Observable observable = Observable.create(new Observable.OnSubscribe<CurrCourseServerResponseSingle>() {
                    @Override
                    public void call(Subscriber<? super CurrCourseServerResponseSingle> subscriber) {
                        subscriber.onNext(response);
                        subscriber.onCompleted();
                    }
                });

                return observable;
            };
        };
    }

    private CurrCourse getValues(){
        try {
            currCourse.setDatecourse(new SimpleDateFormat(project.getDATE_FORMAT_FULL()).parse(currCourseDatecourse.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            currCourse.setCourse(Integer.valueOf(currCourseCourse.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return currCourse;
    }

    private void setValues(CurrCourseServerResponseSingle values){
        if (values.getSuccess()){
            currCourse = values.getData();
            currCourseDatecourse.setText(new SimpleDateFormat(project.getDATE_FORMAT_FULL()).format(values.getData().getDatecourse()));
            currCourseCourse.setText(values.getData().getCourse().toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getObjectGraph().inject(this);
        View view = inflater.inflate(R.layout.fragment_edit_curr_course, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            currCourseEditPane.setVisibility(show ? View.GONE : View.VISIBLE);
            currCourseEditPane.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    currCourseEditPane.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            currCourseProgressBarEdit.setVisibility(show ? View.VISIBLE : View.GONE);
            currCourseProgressBarEdit.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    currCourseProgressBarEdit.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            currCourseProgressBarEdit.setVisibility(show ? View.VISIBLE : View.GONE);
            currCourseEditPane.setVisibility(show ? View.GONE : View.VISIBLE);
        }

        currCourseControlPane.setVisibility(show ? View.GONE : View.VISIBLE);

    }

    public void showError(Throwable e) {
        showProgress(false);
        Toast.makeText(app, "error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
    }

    public void showError(String message) {
        showProgress(false);
        Toast.makeText(app, "error: " + message, Toast.LENGTH_LONG).show();
    }

}
