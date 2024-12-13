package com.jlf.cloud.resp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
//  Lombok 自动生成 链式 setter 方法
@Accessors(chain = true)
public class ResultData<T> {
    private String code; /** 结果状态 ,具体状态码参见枚举类ReturnCodeEnum.java*/
    private String message;
    private T data;
    // 接口调用时的时间
    private long timestamp ;


    public ResultData (){
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(ReturnCodeEnum.RC200.getCode());
        resultData.setMessage(ReturnCodeEnum.RC200.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> fail(String code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        resultData.setData(null);
        return resultData;
    }
}
