package rocketmq.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Devil
 * @create 2022-02-26 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean success;

    private Integer code;

    private String msg;

    private Object data;

    public static Result succeed(Object data){
        return new Result(true,200,"success",data);
    }
    public static Result fail(Integer code,String msg){
        return new Result(false,code,msg,null);
    }
}
