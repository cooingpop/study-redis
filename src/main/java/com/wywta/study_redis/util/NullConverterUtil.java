package com.wywta.study_redis.util;

import org.springframework.util.StringUtils;

public class NullConverterUtil {
    public static <T> T convertTo(Object from, T to){

        if(from == null){
            return to;
        }
        else{
            if(StringUtils.isEmpty(from.toString())){
                return to;
            }
            else {
                if(to.getClass() == Integer.class){
                    return (T)(Integer.valueOf(Integer.parseInt(from.toString())));
                }
                else if(to.getClass() == Double.class){
                    return (T)(Double.valueOf(Double.parseDouble(from.toString())));
                }
                else if(to.getClass() == Float.class){
                    return (T)(Float.valueOf(Float.parseFloat(from.toString())));
                }
                else if(to.getClass() == Long.class){
                    return (T)(Long.valueOf(Long.parseLong(from.toString())));
                }
                else{
                    return (T) from;
                }

            }
        }

    }
}
