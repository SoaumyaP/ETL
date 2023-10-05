package com.csfe.trs.business.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.log4j.Logger;

import com.csfe.common.business.annotation.CreatedOn;
import com.csfe.common.business.annotation.UpdatedOn;
import com.csfe.common.business.entity.DeliveryRequest;
import com.csfe.edison.business.entity.EdisonLog;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class TRSInterceptor implements Interceptor {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        if (parameter != null) {
        	Date newDate = new Date();
        	if(parameter instanceof DeliveryRequest
        			||parameter instanceof EdisonLog) {
	            Field[] declaredFields = parameter.getClass().getDeclaredFields();
	            for (Field field : declaredFields) {	
	            	 if ("createdOn".equals(field.getName())
	            			 ||"createdate".equals(field.getName())) { 
	            		 if (SqlCommandType.INSERT.equals(sqlCommandType)) {
		                        field.setAccessible(true);
		                        if (field.get(parameter) == null) {
		                            field.set(parameter, newDate);
		                        }
		                 }
		            } else if ("updatedOn".equals(field.getName())
		            		//||"lastupdate".equals(field.getName())
		            		) { 
	                    if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
	                        field.setAccessible(true);
	                        field.set(parameter, newDate);
	                    }
	                }
	            }
        	}
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
