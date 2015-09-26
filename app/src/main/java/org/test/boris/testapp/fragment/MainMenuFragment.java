package org.test.boris.testapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.test.boris.testapp.R;
import org.test.boris.testapp.activity.MainActivity;
import org.test.boris.testapp.data.api.Project;
import org.test.boris.testapp.data.api.adapter.MainPageAdapter;
import org.test.boris.testapp.data.api.domain.entity.other.GridItem;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;



public class MainMenuFragment extends Fragment {

    private final String TAG = MainMenuFragment.class.getName();

    @Inject
    Project project;
    @Bind(R.id.main_menu_grid_view) GridView gridView;

    MainPageAdapter mainPageAdapter;

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainPageAdapter = mainPageAdapter == null ? new MainPageAdapter(getActivity(), R.layout.row_main_menu, project.getModules()) : mainPageAdapter;
        gridView.setAdapter(mainPageAdapter);
        gridView.setOnItemClickListener(onModuleSelect());
    }

    @NonNull
    private AdapterView.OnItemClickListener onModuleSelect() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridItem gridItem = null;
                try {
                    gridItem = (GridItem) parent.getItemAtPosition(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (gridItem != null && gridItem.getCode() == 1) {
                    CurrCourseListFragment nextFrag = new CurrCourseListFragment();
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getObjectGraph().inject(this);
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
