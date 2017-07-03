package com.starlight.helper;

import com.starlight.utils.CollectionUtil;
import com.starlight.utils.PropsUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by huangxinguang on 2017/6/29 下午2:00.
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER;
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    private static final BasicDataSource DATA_SOURCE;

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        QUERY_RUNNER = new QueryRunner();
        CONNECTION_HOLDER = new ThreadLocal<>();

        //获取数据库配置信息
        Properties properties = PropsUtil.loadProps("config.properties");
        DRIVER = PropsUtil.getString(properties,"jdbc.driver");
        URL = PropsUtil.getString(properties,"jdbc.url");
        USERNAME = PropsUtil.getString(properties,"jdbc.username");
        PASSWORD = PropsUtil.getString(properties,"jdbc.password");

        //初始化连接池
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);

        try {
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e) {
            LOGGER.error("can not found driver");
        }
    }


    /**
     * 查询实体类
     * @param entityClass 实体类class
     * @param sql sql语句
     * @param params 参数
     * @param <T>
     * @return
     */
    public static<T> List<T> queryEntityList(Class<T> entityClass,String sql,Object...params) {
        List<T> entityList;
        Connection conn = getConnection();
        try {
            entityList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entitylist failure",e);
            throw new RuntimeException(e);
        }
        return entityList;
    }

    /**
     * 查询实体类
     * @param entityClass 实体类class
     * @param sql sql语句
     * @param params 参数
     * @param <T>
     * @return
     */
    public static<T> T queryEntity(Class<T> entityClass,String sql,Object...params) {
        T entity;
        Connection conn = getConnection();
        try {
            entity = QUERY_RUNNER.query(conn,sql, new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure",e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * 查询
     * @param sql 语句
     * @param params 参数
     * @return
     */
    public static List<Map<String,Object>> executeQuery(String sql, Object...params) {
        List<Map<String,Object>> result;
        Connection conn = getConnection();
        try {
            result = QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("execute query failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 更新
     * @param sql sql语句
     * @param params 参数
     * @return
     */
    public static int executeUpdate(String sql,Object...params) {
        int rows = 0;
        Connection conn = getConnection();
        try {
            rows = QUERY_RUNNER.update(conn,sql,params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure",e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * 执行文件sql
     * @param filePath
     */
    public static void executeSqlFile(String filePath) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String sql;
        try {
            while ((sql = br.readLine()) != null) {
                executeUpdate(sql);
            }
        }catch (Exception e) {
            LOGGER.error("execute sql file failure",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入实体类
     * @param classEntity 实体class
     * @param fieldMap 实体属性map
     * @param <T>
     * @return
     */
    public static <T> boolean insertEntity(Class<T> classEntity,Map<String,Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity:fieldMap is empty");
            return false;
        }
        String sql = "INSERT INTO " + getTableName(classEntity);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");

        for(String fieldName:fieldMap.keySet()) {
            columns.append(fieldName).append(",");
            values.append("?").append(",");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(","),values.length(),")");
        sql += columns + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql,params) == 1;
    }

    /**
     * 更新实体类
     * @param classEntity 实体class
     * @param id id
     * @param fieldMap 实体属性map
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> classEntity,long id,Map<String,Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update entity:fieldMap is empty");
            return false;
        }

        String sql = "UPDATE " + getTableName(classEntity)+ " SET ";
        StringBuilder columns = new StringBuilder();
        for(String fieldName:fieldMap.keySet()) {
            columns.append(fieldName).append(" =?").append(",");
        }
        sql += columns.substring(0,columns.lastIndexOf(",")) + " WHERE id = ?";

        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql,params) == 1;
    }

    /**
     * 删除实体类
     * @param classEntity
     * @param id
     * @param <T>
     * @return
     */
    public static<T> boolean deleteEntity(Class<T> classEntity,long id) {
        String sql = "DELETE FROM " + getTableName(classEntity) + " WHERE id =?";
        return executeUpdate(sql,id) == 1;
    }

    /**
     * 拿到表的名称
     * @param classEntity
     * @return
     */
    private static String getTableName(Class<?> classEntity) {
        return classEntity.getSimpleName();
    }

    /**
     * 获取数据库连接
     * @return
     */
    private static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if(conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure",e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }

        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn 连接对象
     *//*
    private static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close conection failure",e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }*/

}
