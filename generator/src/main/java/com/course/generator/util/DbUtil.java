package com.course.generator.util;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DbUtil {
    /**
     * 获取数据库连接
     *
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Connection conn = null;
//        Properties prop = new Properties();
//        InputStream res = DbUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
//        prop.load(res);
//        String url = (String) prop.get("url");
//        String user = (String) prop.get("user");
//        String password = (String) prop.get("password");
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "huge11303530";
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * 获取表注释
     *
     * @param tableName
     * @return
     */
    public static String getTableComment(String tableName) throws IOException, SQLException, ClassNotFoundException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        String sql = "select table_comment from information_schema.tables Where table_name = '" + tableName + "'";
        ResultSet rs = stmt.executeQuery(sql);
        String tableNameCN = "";
        if (rs != null) {
            while (rs.next()) {
                tableNameCN = rs.getString("table_comment");
                break;
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("表名: " + tableNameCN);
        return tableNameCN;
    }

    /**
     * 获得所有列信息
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public static List<Field> getColumnByTableName(String tableName) throws Exception {
        List<Field> fieldList = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show full columns from `" + tableName + "`");
        if (rs != null) {
            while (rs.next()) {
                String columnName = rs.getString("Field");
                String type = rs.getString("Type");
                String comment = rs.getString("Comment");
                Field field = new Field();
                field.setName(columnName);
                field.setNameHump(lineToHump(columnName));
                field.setNameBigHump(lineToBigHump(columnName));
                field.setType(type);
                field.setJavaType(DbUtil.sqlTypeToJavaType(rs.getString("Type")));
                field.setComment(comment);
                /*字段是否可以为空*/
                String nullAble = rs.getString("Null");
                if (comment.contains("|")) {
                    field.setNameCn(comment.substring(0, comment.indexOf("|")));
                } else {
                    field.setNameCn(comment);
                }
                /*字段是否可以为空*/
                field.setNullAble("YES".equals(nullAble));
                /*长度判断设置*/
                if (type.toUpperCase().contains("varchar".toUpperCase())){
                    String lengthStr = type.substring(type.indexOf("(") + 1, type.length() - 1);
                    field.setLength(Integer.valueOf(lengthStr));
                }else{
                    field.setLength(0);
                }
                /*设置完成后,添加到fieldList中去*/
                fieldList.add(field);
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("列信息：" + fieldList);
        return fieldList;
    }

    /**
     * 下划线转小驼峰
     */
    public static String lineToHump(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();

    }

    /**
     * 下划线转大驼峰
     */


    public static String lineToBigHump(String str) {
        String s = lineToHump(str);
        return s.substring(0, 1).toUpperCase() + s.substring(1);

    }

    /**
     * 数据库类型转为Java类型
     *
     */
    public static String sqlTypeToJavaType(String sqlType) {
        if (sqlType.toUpperCase().contains("varchar".toUpperCase())
                || sqlType.toUpperCase().contains("char".toUpperCase())
                || sqlType.toUpperCase().contains("text".toUpperCase())) {
            return "String";

        } else if (sqlType.toUpperCase().contains("datetime".toUpperCase())) {
            return "Date";

        } else if (sqlType.toUpperCase().contains("int".toUpperCase())) {
            return "Integer";

        } else if (sqlType.toUpperCase().contains("long".toUpperCase())) {
            return "Long";

        } else if (sqlType.toUpperCase().contains("decimal".toUpperCase())) {
            return "BigDecimal";

        } else {
            return "String";

        }
    }

}
