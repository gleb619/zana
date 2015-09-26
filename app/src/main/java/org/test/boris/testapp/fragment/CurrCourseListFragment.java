package org.test.boris.testapp.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.test.boris.testapp.R;
import org.test.boris.testapp.TestApp;
import org.test.boris.testapp.activity.MainActivity;
import org.test.boris.testapp.data.api.Project;
import org.test.boris.testapp.data.api.adapter.CurrCourseListAdapter;
import org.test.boris.testapp.data.api.converter.ServerRequestConverter;
import org.test.boris.testapp.data.api.domain.entity.CurrCourse;
import org.test.boris.testapp.data.api.domain.entity.other.Credentials;
import org.test.boris.testapp.data.api.domain.entity.other.ServerRequest;
import org.test.boris.testapp.data.api.domain.response.CurrCourseServerResponse;
import org.test.boris.testapp.data.api.resource.CurrCourseResource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by BORIS on 26.07.2015.
 */
public class CurrCourseListFragment extends Fragment {

    @Inject CurrCourseResource currCourseResource;
    @Inject TestApp app;
    @Inject Credentials credentials;
    @Inject Project project;

    @Bind(R.id.search_box) EditText searchBox;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Bind(R.id.curr_course_progress_bar) View progressView;
    @Bind(R.id.curr_course_main_pane) View mainPaneView;

    private CurrCourseListAdapter currCourseListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        currCourseListAdapter = currCourseListAdapter == null ? new CurrCourseListAdapter(project, onItemClickListner()) : currCourseListAdapter;
        recyclerView.setAdapter(currCourseListAdapter);
        loadData(new ServerRequest().firstPage());
    }

    @NonNull
    private CurrCourseListAdapter.ViewHolder.ViewHolderClickListener onItemClickListner() {
        return new CurrCourseListAdapter.ViewHolder.ViewHolderClickListener() {
            @Override
            public void onItemClick(View caller, int rowId) {
                if (rowId > -1) {
                    project.setLastId(rowId, project.ENTITY_CODE.CURR_COURSE);
                    CurrCourseEditFragment nextFrag = new CurrCourseEditFragment();
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer, nextFrag)
                                .addToBackStack(null)
                                .commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void loadData(ServerRequest serverRequest){
        showProgress(true);

        Map<String, String> request = new HashMap<>();
        try {
            request = new ServerRequestConverter().convert(serverRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        currCourseResource.list(credentials.getKey(), request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(project.getTimeout(), project.getTimeoutLatency())
                .retry(project.getRetry())
                .onErrorResumeNext(onLoadError())
                .subscribe(new Subscriber<CurrCourseServerResponse>() {
                    @Override
                    public void onCompleted() {
                        showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e);
                    }

                    @Override
                    public void onNext(CurrCourseServerResponse responseList) {
                        currCourseListAdapter.setCurrCourses((List<CurrCourse>) responseList.getData());
                        currCourseListAdapter.notifyDataSetChanged();
                    }
                });
    }

    private Func1<Throwable, Observable<CurrCourseServerResponse>> onLoadError() {
        return new Func1<Throwable, Observable<CurrCourseServerResponse>>() {
            @Override
            public Observable<CurrCourseServerResponse> call(Throwable throwable) {
                CurrCourse currCourse = new CurrCourse();
                currCourse.setCourse(0);
                currCourse.setDatecourse(new Date());

                final CurrCourseServerResponse responseList = new CurrCourseServerResponse();
                responseList.setSuccess(false);
                responseList.setMessage("Can't connect to the server");

                System.err.println(this.getClass().getName() + "\n" + "----------------------------------------");
                System.err.println(this.getClass().getName() + ".onLoadError# "
                        + "\n\t throwable: " + throwable.getClass()
                        + "\n\t cause: " + throwable.getCause()
                        + "\n\t localizedMessage: " + throwable.getLocalizedMessage()
                        + "\n\t message: " + throwable.getMessage()
                );
                showError(throwable);
//                throwable.printStackTrace();
                System.err.println(this.getClass().getName() + "\n" + "----------------------------------------");

                Observable observable = Observable.create(new Observable.OnSubscribe<CurrCourseServerResponse>() {
                    @Override
                    public void call(Subscriber<? super CurrCourseServerResponse> subscriber) {
                        subscriber.onNext(responseList);
                        subscriber.onCompleted();
                    }
                });

                return observable;
            };
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getObjectGraph().inject(this);
        View view = inflater.inflate(R.layout.fragment_list_curr_course, container, false);
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

            mainPaneView.setVisibility(show ? View.GONE : View.VISIBLE);
            mainPaneView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mainPaneView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mainPaneView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

        if (show) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
        }
    }

    public void showError(Throwable e) {
        showProgress(false);
        Toast.makeText(app, "error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
    }

}

