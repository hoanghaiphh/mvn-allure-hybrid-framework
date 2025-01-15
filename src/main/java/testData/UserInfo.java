package testData;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class UserInfo {
    private String firstName, lastName, companyName, emailAddress, password;

    public static UserInfo getUserInfo() {
        return new UserInfo();
    }

    public void setEmailAddress(String emailAddress) {
        if (isValidEmail(emailAddress)) {
            this.emailAddress = emailAddress;
        } else {
            throw new IllegalArgumentException("Địa chỉ email không hợp lệ");
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
