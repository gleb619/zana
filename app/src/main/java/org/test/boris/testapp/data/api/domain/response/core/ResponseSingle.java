package org.test.boris.testapp.data.api.domain.response.core;

import java.util.List;

/**
 * Created by BORIS on 16.08.2015.
 */
public interface ResponseSingle<ENTITY> {

    ENTITY getData();

    void setData(ENTITY data);

}
