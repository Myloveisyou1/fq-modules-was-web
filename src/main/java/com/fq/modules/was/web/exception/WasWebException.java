package com.fq.modules.was.web.exception;

import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.enums.ResultEnum;
import lombok.Data;

@Data
public class WasWebException extends RuntimeException{
    private Integer code;


    public WasWebException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

}
