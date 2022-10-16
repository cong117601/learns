package com.cgm.generics;



public class Result<T> {



    T data;

    String msg;

    Code code;

    public Result(Code type, String msg, T data)
    {
       this.code =type;
       this.msg = msg;
       this.data = data;
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public Result success(T data)
    {
        return Result.success("操作成功", data);
    }

    public static <T> Result success(String msg, T data)
    {
        return new Result(Code.SUCCESS, msg, data);
    }

    public enum Code{

        SUCCESS(0),
        /** 警告 */
        WARN(301),
        /** 错误 */
        ERROR(500);
        private int vale;

        Code(int vale) {
            this.vale = vale;
        }
    }


}
