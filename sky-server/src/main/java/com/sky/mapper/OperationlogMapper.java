package com.sky.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface OperationlogMapper {


    @Insert("insert into operation_log (url, port_check, ip, method, exception_detected, args, user_id, update_time) " +
            "values (#{url},#{portCheck},#{ip},#{method},#{exceptionDetected},#{args},#{userId},#{updateTime})")
    void insert(String url, int portCheck, String ip, String method, String exceptionDetected, String args, Long userId, LocalDateTime updateTime);

}
