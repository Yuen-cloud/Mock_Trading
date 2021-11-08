package user.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserDao {
    public void showDebug(String msg) {
        System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "][device/dao/Db]" + msg);
    }

    /*添加记录*/
    public void addUserRecord(Data data, JSONObject json) throws JSONException, SQLException {
        //构造sql语句，根据传递过来的条件参数
        String userName = data.getParam().has("username") ? data.getParam().getString("username") : null;
        String password = data.getParam().has("password") ? data.getParam().getString("password") : null;
        String authority = data.getParam().has("authority") ? data.getParam().getString("authority") : null;
        if (userName != null && password != null && authority != null) {
            String sql = "insert into user_file(username,password,authority)";
            sql = sql + " values('" + userName + "'";
            sql = sql + " ,'" + password + "'";
            sql = sql + " ,'" + authority + "')";
            data.getParam().put("sql", sql);
            updateRecord(data, json);
        }
    }

    /*删除记录*/
    public void deleteUserRecord(Data data, JSONObject json) throws JSONException, SQLException {
        //构造sql语句，根据传递过来的条件参数
        String id = data.getParam().has("id") ? data.getParam().getString("id") : null;
        if (id != null) {
            String sql = "delete from user_file where id=" + data.getParam().getString("id");
            data.getParam().put("sql", sql);
            updateRecord(data, json);
        }
    }

    /*修改记录*/
    public void modifyUserRecord(Data data, JSONObject json) throws JSONException, SQLException {
        //构造sql语句，根据传递过来的条件参数
        String id = data.getParam().has("id") ? data.getParam().getString("id") : null;
        String userName = data.getParam().has("username") ? data.getParam().getString("username") : null;
        String password = data.getParam().has("password") ? data.getParam().getString("password") : null;
        if (id != null) {
            String sql = "update user_file";
            sql = sql + " set username='" + userName + "'";
            sql = sql + " ,password='" + password + "'";
            sql = sql + " where id=" + id;
            data.getParam().put("sql", sql);
            updateRecord(data, json);
        }
    }

    /*查询记录*/
    public void getUserRecord(Data data, JSONObject json) throws JSONException, SQLException {
        //构造sql语句，根据传递过来的查询条件参数
        String sql = createGetRecordSql(data);            //构造sql语句，根据传递过来的查询条件参数
        data.getParam().put("sql", sql);
        queryRecord(data, json);
    }

    /*
     * 这是一个样板的函数，可以拷贝做修改用
     */
    private void updateRecord(Data data, JSONObject json) throws JSONException, SQLException {
        /*--------------------获取变量 开始--------------------*/
        JSONObject param = data.getParam();
        int resultCode = 0;
        String resultMsg = "ok";
        /*--------------------获取变量 完毕--------------------*/
        /*--------------------数据操作 开始--------------------*/
        Db updateDb = new Db("mock_trading");
        String sql = data.getParam().getString("sql");
        showDebug("[updateRecord]" + sql);
        updateDb.executeUpdate(sql);
        updateDb.close();
        /*--------------------数据操作 结束--------------------*/
        /*--------------------返回数据 开始--------------------*/
        json.put("result_msg", resultMsg);                                                            //如果发生错误就设置成"error"等
        json.put("result_code", resultCode);                                                        //返回0表示正常，不等于0就表示有错误产生，错误代码
        /*--------------------返回数据 结束--------------------*/
    }

    private void queryRecord(Data data, JSONObject json) throws JSONException, SQLException {
        /*--------------------获取变量 开始--------------------*/
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        List jsonName = new ArrayList();
        /*--------------------获取变量 完毕--------------------*/
        /*--------------------数据操作 开始--------------------*/
        Db queryDb = new Db("mock_trading");
        String sql = data.getParam().getString("sql");
        showDebug("[queryRecord]构造的SQL语句是：" + sql);
        try {
            ResultSet rs = queryDb.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int fieldCount = rsmd.getColumnCount();
            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 0; i < fieldCount; i++) {
                    map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
                }
                jsonList.add(map);
            }
            rs.close();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                String columnLabel = rsmd.getColumnLabel(i + 1);
                jsonName.add(columnLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showDebug("[queryRecord]查询数据库出现错误：" + sql);
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + e.getMessage();
        }
        queryDb.close();
        /*--------------------数据操作 结束--------------------*/
        /*--------------------返回数据 开始--------------------*/
        json.put("aaData", jsonList);
        json.put("aaFieldName", jsonName);
        json.put("result_msg", resultMsg);                                                            //如果发生错误就设置成"error"等
        json.put("result_code", resultCode);                                                        //返回0表示正常，不等于0就表示有错误产生，错误代码
        /*--------------------返回数据 结束--------------------*/
    }

    private String createGetRecordSql(Data data) throws JSONException {
        String sql = "select * from user_file";
        String id = data.getParam().has("id") ? data.getParam().getString("id") : null;
        if (id != null && !id.isEmpty())
            sql = sql + " where id=" + id;
        String userName = data.getParam().has("username") ? data.getParam().getString("username") : null;
        if (userName != null && !userName.isEmpty()) {
            if (sql.indexOf("where") > -1) {
                sql = sql + " and username like '%" + userName + "%'";
            } else {
                sql = sql + " where device_id like '%" + userName + "%'";
            }
        }
        return sql;
    }

    public void login(Data data, JSONObject json) throws IOException, JSONException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        String userName = data.getParam().getString("username");
        String password = data.getParam().getString("password");
        String action = data.getParam().getString("action");
        Db queryDb = new Db("mock_trading");
        String sql = "select * from user_file where username='" + userName + "' and password='" + password + "'";
        try {
            ResultSet rs = queryDb.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int fieldCount = rsmd.getColumnCount();
            if (!rs.next()) {
                resultCode = 10;
                resultMsg = "登录失败";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库失败!" + e.getMessage();
        }
        queryDb.close();
        json.put("aaData", jsonList);
        json.put("action", action);
        json.put("result_msg", resultMsg);
        json.put("result_code", resultCode);
    }
}
