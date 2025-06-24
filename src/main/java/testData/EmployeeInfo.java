package testData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeInfo {
    private String firstName, middleName, lastName, profilePicture, employeeId, password;
    private String driverLicense, licenseExpiryDate, nationality, maritalStatus, dateOfBirth, gender;

    public static EmployeeInfo getEmployeeInfo() {
        return new EmployeeInfo();
    }

}
