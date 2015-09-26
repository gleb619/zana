package org.test.boris.testapp.data.api.domain.response;


import org.test.boris.testapp.data.api.domain.entity.CurrCourse;
import org.test.boris.testapp.data.api.domain.response.core.ResponseSingle;
import org.test.boris.testapp.data.api.domain.response.core.ServerResponse;
import org.test.boris.testapp.data.api.domain.response.core.SimpleServerResponse;

/**
 * Created by BORIS on 30.07.2015.
 */
public class CurrCourseServerResponseSingle extends SimpleServerResponse implements ResponseSingle<CurrCourse> {

    private static final long serialVersionUID = 6710594620428582401L;

    private CurrCourse data;

    @Override
    public CurrCourse getData() {
        return data;
    }

    @Override
    public void setData(CurrCourse data) {
        this.data = data;
    }

}
