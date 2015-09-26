package org.test.boris.testapp.data.api.domain.entity.other;

/**
 * Created by BORIS on 26.07.2015.
 */
public class Credentials {

    private String username;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int isEmpty() {
        if(key == null || key.isEmpty() || key.replaceAll("\\s+", "").length() == 0){
            return 1;
        }

        return 0;
    }
}
