package com.spoom.nymph.config;

import com.spoom.nymph.utils.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * package com.spoom.nymph.config
 *
 * @author spoomlan
 * @date 2018/10/24
 */
@Slf4j
@RestControllerAdvice
public class ArcherExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BaseResult handleIChatException(RuntimeException e) {
        return BaseResult.error(500,e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> list = e.getBindingResult().getFieldErrors();
        for (FieldError error : list) {
            log.info("error: " + error.getDefaultMessage());
            errorMsg.append(error.getDefaultMessage()).append(".");
        }
        return BaseResult.error(500, errorMsg.toString());
    }
}