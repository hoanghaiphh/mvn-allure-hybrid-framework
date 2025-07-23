package testData;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class UserInfoPOJO {
    private String firstName, lastName, companyName, emailAddress, password;

    public static UserInfoPOJO getUserInfo() {
        return new UserInfoPOJO();
    }

    public void setEmailAddress(String emailAddress) {
        if (isValidEmail(emailAddress)) {
            this.emailAddress = emailAddress;
        } else {
            throw new IllegalArgumentException("Email address is not valid.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
