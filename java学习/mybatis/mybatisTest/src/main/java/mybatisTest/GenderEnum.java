package mybatisTest;

public enum GenderEnum {
    MALE("男"),
    FEMALE("女");

    private String desc;

    private GenderEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
