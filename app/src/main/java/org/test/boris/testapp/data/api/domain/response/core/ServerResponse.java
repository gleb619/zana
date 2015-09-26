package org.test.boris.testapp.data.api.domain.response.core;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BORIS on 05.08.2015.
 */
public interface ServerResponse extends Serializable {
    Integer getTotal();

    void setTotal(Integer total);

    Boolean getSuccess();

    void setSuccess(Boolean success);

    Boolean getWarning();

    void setWarning(Boolean warning);

    String getMessage();

    void setMessage(String message);
}
