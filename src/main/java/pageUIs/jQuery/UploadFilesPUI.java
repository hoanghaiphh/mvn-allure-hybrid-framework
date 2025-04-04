package pageUIs.jQuery;

public class UploadFilesPUI {

    public static final String ADD_FILES_BUTTON = "css=input[type='file']";

    public static final String DYNAMIC_ADDED_FILE = "xpath=//p[text()='%s']";

    public static final String DYNAMIC_ADDED_FILE_ACTION_BUTTON
            = "xpath=//p[text()='%s']/parent::td/parent::tr//button[contains(@class,'%s')]";

    public static final String DYNAMIC_ERROR_MESSAGE = "xpath=//p[text()='%s']/following-sibling::strong";

    public static final String DYNAMIC_UPLOADED_FILE = "xpath=//a[text()='%s']";

    public static final String DYNAMIC_UPLOADED_FILE_DELETE_BUTTON
            = "xpath=//a[text()='%s']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']";

    public static final String DYNAMIC_ALL_FILES_ACTION_BUTTON
            = "xpath=//div[@class='row fileupload-buttonbar']//button[contains(@class,'%s')]";

    public static final String SELECT_ALL_CHECKBOX = "css=div.fileupload-buttonbar input.toggle";

}
