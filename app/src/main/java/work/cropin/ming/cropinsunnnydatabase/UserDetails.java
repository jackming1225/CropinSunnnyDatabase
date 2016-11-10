package work.cropin.ming.cropinsunnnydatabase;

/**
 * Created by ming on 17/9/16.
 */
public class UserDetails {

    private String userId, name, email, mobile, jsonStr;

    public UserDetails(String userId, String name, String email, String mobile, String jsonStr) {

        this.setUserId(userId);
        this.setName(name);
        this.setEmail(email);
        this.setMobile(mobile);
        this.setJsonStr(jsonStr);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
