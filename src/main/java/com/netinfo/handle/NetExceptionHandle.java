package com.netinfo.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netinfo.entity.Result;
import com.netinfo.exception.NetException;
import com.netinfo.utils.ResultUtil;


@ControllerAdvice
public class NetExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(NetExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof NetException) {
            NetException netException = (NetException) e;
            return ResultUtil.error(netException.getCode(), netException.getMessage());
        }else {
            logger.error("【系统异常】{}", e);
            e.printStackTrace();
            return ResultUtil.error(-1, "未知错误");
        }
    }
}
