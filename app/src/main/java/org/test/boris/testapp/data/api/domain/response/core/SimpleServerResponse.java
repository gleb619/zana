package org.test.boris.testapp.data.api.domain.response.core;

/**
 * Created by BORIS on 16.08.2015.
 */
public abstract class SimpleServerResponse implements ServerResponse {

    private static final long serialVersionUID = 2618995655599346864L;

    private Integer total;
    private Boolean success;
    private Boolean warning;
    private String message;

    @Override
    public Integer getTotal() {
        return total;
    }

    @Override
    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public Boolean getSuccess() {
        return success;
    }

    @Override
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public Boolean getWarning() {
        return warning;
    }

    @Override
    public void setWarning(Boolean warning) {
        this.warning = warning;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
