package org.szh.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Result<T> implements Serializable{
    
    private static final long serialVersionUID = 3332779079932570582L;

    private String code = "200";
    
    private String msg = "success";
    
    private T data;
    
    public Result() {}
    
    public Result(T data){
        this.data = data;
    }
}
