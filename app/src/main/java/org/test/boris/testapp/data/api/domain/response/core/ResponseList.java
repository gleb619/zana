package org.test.boris.testapp.data.api.domain.response.core;

import org.test.boris.testapp.data.api.domain.entity.CurrCourse;

import java.util.List;

/**
 * Created by BORIS on 16.08.2015.
 */
public interface ResponseList<ENTITY> {

    List<ENTITY> getData();

    void setData(List<ENTITY> data);

}
