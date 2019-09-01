package com.enhinck.db.excel;

import com.enhinck.db.entity.ExcelCell;
import com.enhinck.db.entity.ExcelFile;
import com.enhinck.db.entity.ExcelRow;
import com.enhinck.db.entity.ExcelSheet;

public enum ErrorCodeEnum {
    SUCCESS(0, "成功"),
    NO_LOGIN_OR_TIMEOUT(100000, "未登录或登录超时，请重新登录"),
    USERS_LOGIN_USERNAME_OR_PASSWORD_ERROR(100001, "登录用户名或密码错误"),
    USERS_QUERY_USER_NOT_EXISTS(100006, "要查询的用户信息不存在"),
    USERS_TOKEN_CACHE_IS_NULL(100008, "Token不能为空"),
    MOBILE_PHONE_NUM_FORMAT_ERROR(100010, "手机号格式错误"),
    SEND_SMS_EXCEPTION(100011, "发送验证码错误"),
    USERS_VERIFY_CODE_ERROR(100012, "验证码错误"),
    USERS_VERIFY_CODE_TIMEOUT(100013, "验证码错误，请重新输入"),
    USERS_VERIFY_CODE_ERROR_TIMES_MAX(100014, "输入超过指定错误次数"),
    IS_NOT_VALID_TOKEN(100017, "token无效"),
    USERS_IS_EXIST(100020, "该用户已存在"),
    USERS_RELATED_ROLE(100021, "该角色已有用户使用"),
    USERS_RELATED_PROJECT(100022, "该项目已有用户使用"),
    ONLY_SUPPORT_LIMIT(100025, "仅支持中英文数字，字数不超过15"),
    USER_NOT_EXISTS(100023, "用户不存在"),
    SEND_CODE_FIVE_TIME_IIMIT(10024,"获取验证码过于频繁，请24小时后重试"),
    AUTH_ERROR_NUM_LIMIT(10025,"多次输入错误，请重新获取验证码"),
    AUTH_CODE_ERROR_AND_CONFIRM(10026,"验证码错误，请确认后提交"),


    PARAMS_CHECK_ERROR(200000, "参数校验不合法"),

    DATABASE_ERROR(200006, "数据库异常"),
    PLATE_HAS_BEAN_BIND(200016, "已有用户绑定该车牌"),
    USER_NOT_PERMISSION(200022, "无此操作权限"),
    DEVICE_NOT_EXIST(200027, "设备不存在"),
    REQUEST_GATEWAY_ERROR(200028, "访问网关失败"),
    GET_TOKEN_ERROR(200029, "获取token失败"),
    UPDATE_CENTER_USERINFO_EXCEPTION(200031, "更新用户失败"),
    OWNER_MOBILE_OR_NAME_ISNULL(200032, "业主姓名或手机号为空"),
    OWNER_INFO_NOT_EXIST(200033, "填写业主信息不正确"),
    NOT_FREQUENT_OPERATION(200034, "请勿频繁操作,您可输入之前发送的验证码"),
    AUTH_CODE_IS_FAILURE(200035, "验证码已失效"),
    ROOM_ALREADY_OWNER(200037, "您已加入该房屋"),

    DATA_NOT_FOUND(200001, "数据不存在"),

    ONE_PARKING_SPACE_THREE_VEHICLE(200038, "一个车位最多能添加三辆车"),
    PLAT_NUMBER_EXIST(200039, "你已添加该车辆"),

    LEELEN_SAVE_MEMBER_FAIL(300001, "leelen-用户授权失败"),
    LEELEN_OPEN_DOOR_FAIL(300003, "leelen开门失败"),
    UPLOAD_IMAGE_SIZE_TOOBIG(300006, "上传图片超过2Mb"),
    FACE_RECOGNITION_SERVER_ERRO(300007, "人脸识别服务异常"),
    FACE_NO_FOUND(300008, "未检测到人脸"),
    FACE_MULTI_FIND(300009, "检测到多张人脸"),
    UPLOAD_IMAGE_IS_NULL(300010, "上传文件为空"),
    FACE_IMAGE_IS_FUZZY(300011, "请上传清晰的人脸"),
    LEELEN_GARDEN_HOUSE_GET_FAIL(300012, "立林虚拟房间获取失败"),
    GET_QRCODE_INFO_FAIL(300013, "访客二维码记录获取失败"),
    UPLOAD_FILL_PATH_ERROR(300014, "文件路径错误"),

    POINTS_NOT_ENOUGH_ERROR(400001, "积分不足，兑换失败"),


    DNAKE_DEVICE_GROUP_FAIL(500003, "dnake-设备组获取失败"),

    EMERGENCY_CONTACT_REPEAT(600001, "该紧急联系人已存在，请勿重复添加"),
    EMERGENCY_CONTACT_NAME_LIMIT(600002, "请输入正确的姓名"),
    EMERGENCY_CONTACT_NAME_SPECIAL_CHAR(600003, "可能含有<>/等特殊符号，请修改重试。"),

    EMERGENCY_CONTACT_SELF_MOBILE(600004, "请勿设置本人手机号为紧急联系人"),
    EMERGENCY_CONTACT_NICKNAME_LIMIT(600005, "请输入正确昵称"),
    EMERGENCY_CONTACT_AGE_LIMIT(600006, "请输入正确年龄"),

    SYSTEM_REQUEST_INVALID(999974, "请求已失效"),
    SYSTEM_CHANNEL_CODE_ILLEGAL(999975, "非法的渠道参数。"),
    SYSTEM_ILLEGAL_REQUEST_PARAMETERS(999978, "非法的请求参数"),
    SYSTEM_MISSING_REQUEST_PARAMETERS(999979, "缺少必填参数"),
    SYSTEM_OTHER_EXCEPTION(999999, "服务器开小差"),

    NO_INTERFACE_PERMISSION(888888, "没有接口调用权限"),
    FACE_SAVE_ERROR(800001, "人脸图片保存失败"),

    //黑名单异常
    FACE_PIC_ERROR(700001, "请上传人脸照片"),
    NAME_CHECK_ERROR(700002, "姓名应为不超过12位字符的汉字、字母组合"),
    REMARK_CHECK_ERROR(700003, "备注应为不超过24位字符的汉字、字母、数字组合"),
    SEX_ERROR(700004, "请填写性别"),
    BLACK_CREATE_ERROR(700005, "黑名单创建失败"),
    //角色管理异常
    ROLE_NAME_ISNULL(700101, "角色名称不能为空"),
    ROLE_NAME_LIMIT(700102, "角色名称应为1-10位汉字"),
    ROLE_CODE_ISNULL(700103, "角色编码不能为空"),
    ROLE_CODE_LIMIT(700104, "角色编码应为6-50位ROLE_和大写字母组合"),
    ROLE_REMARK_ISNULL(700105, "描述信息不能为空"),
    ROLE_REMARK_LIMIT(700106, "描述信息应为1-40位汉字"),
    ROLE_CODE_EXIST(700107, "角色编码已存在"),
    //用户管理异常
    USER_NAME_ISNULL(700201, "用户名称不能为空"),
    USER_NAME_LIMIT(700202, "用户名称应为不超过12位字母、数字组合"),
    USER_NAME_EXIST(700203, "该用户名已存在，请重新输入"),
    USER_PASSWORD_ISNULL(700204, "密码不能为空"),
    USER_PASSWORD_LIMIT(700205, "密码应为8-16位字母、数字、@、-、_组合"),
    USER_MOBILE_ISNULL(700206, "手机号不能为空"),
    USER_MOBILE_LIMIT(700207, "请输入正确的手机号"),
    USER_ROLE_ISNULL(700208, "角色类型不能为空"),
    USER_PROJECT_ISNULL(700209, "所属项目不能为空"),
    //项目管理异常
    PROJECT_NAME_ISNULL(700301, "项目名称不能为空"),
    PROJECT_NAME_LIMIT(700302, "项目名称应为不超过24位中文、分隔符组合"),
    PROJECT_NAME_EXIST(700303, "该项目名称已存在，请重新输入"),
    PROJECT_ADDRESS_ISNULL(700304, "所在地不能为空"),
    PROJECT_ADDRESS_LIMIT(700305, "所在地为不超过60位中文、字母、数字组合"),
    PROJECT_COORDINATECENTER_ISNULL(700306, "中心点坐标不能为空"),

    ALREADY_HAS_TICKET(700307,"该兑换券已拥有"),
    EXCHANGE_HAS_GONE(700308,"该券已领取完"),


    // 人脸查找失败，请等待上一个人脸查找任务完成后再执行
    HIK_SEARCH_FACE_ERROR(810001, "系统繁忙请稍后再试"),
    // 人脸轨迹查找失败，请等待上一个查找人脸轨迹查询任务完成后再执行
    HIK_SEARCH_FACE_TRACK_ERROR(810002, "系统繁忙请稍后再试"),

    AEP_ERROR(999901, "aep平台异常"),
    SERVER_HYSTRIX(999991, "服务熔断"),

    MISSING_REQUEST_PARAMETERS(999979, "缺少必填参数"),
    NULL_POINT_ERROR(999997, "后台空指针"),

    DB_ERROR(999998, "数据库异常"),
    OTHER_ERROR(999999, "其它系统异常"),
    FREQUENT_OPERATION(999990, "请勿频繁操作"),
    SYSTEM_DEFINE(900001, "自定义消息异常");


    private int code;
    private String desc;// 错误信息

    ErrorCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ErrorCodeEnum toEnum(int code) {
        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if (errorCodeEnum.getCode() == code) {
                return errorCodeEnum;
            }
        }
        return OTHER_ERROR;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static void main(String[] args) {
        ExcelFile excelFile = new ExcelFile();

        excelFile.setFileName("错误枚举");
        ExcelSheet excelSheet = new ExcelSheet();
        excelFile.AddSheet(excelSheet);

        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            ExcelRow row = new ExcelRow();
            ExcelCell cell1 = new ExcelCell(errorCodeEnum.code+"");
            ExcelCell cell2 = new ExcelCell(errorCodeEnum.desc+"");
            row.AddCell(cell1);
            row.AddCell(cell2);
            excelSheet.AddRow(row);
        }

        CommonExcelWriteUtil.write(excelFile);
    }
}
