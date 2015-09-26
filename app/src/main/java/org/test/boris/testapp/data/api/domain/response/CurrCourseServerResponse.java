package org.test.boris.testapp.data.api.domain.response;


import org.test.boris.testapp.data.api.domain.entity.CurrCourse;
import org.test.boris.testapp.data.api.domain.response.core.ResponseList;
import org.test.boris.testapp.data.api.domain.response.core.SimpleServerResponse;

import java.util.List;

/**
 * Created by BORIS on 30.07.2015.
 */
public class CurrCourseServerResponse extends SimpleServerResponse implements ResponseList<CurrCourse> {

    private static final long serialVersionUID = 2218995655599346864L;

    private List<CurrCourse> data;

    @Override
    public List<CurrCourse> getData() {
        return data;
    }

    @Override
    public void setData(List<CurrCourse> data) {
        this.data = data;
    }

}
