package com.desperado.chapter14.framework.swagger.properties;

/**
 * 方便Swagger 中 @ApiImplicitParam(paramType = ApiParamType.HEADER)
 */
public class ApiParamType {

    public final static String QUERY = "query";
    public final static String HEADER = "header";
    public final static String PATH = "path";
    public final static String BODY = "body";
    public final static String FORM = "form";
}
