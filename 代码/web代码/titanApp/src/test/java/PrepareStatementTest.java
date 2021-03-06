import edu.neu.titan.titanApp.common.utils.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class PrepareStatementTest {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCUtil.getConnection();

//        String sql = "insert into base_user_active_day(active_date, channel_id, version_id, active_num) values (?, ?, ?, ?)";
//        String sql = "insert into base_participation_frequency_day(frequency_date, channel_id, version_id, range_id,start_num,user_num) values (?, ?, ?, ?,?,?)";
        String sql = "insert into base_participation_interval(interval_date, channel_id, version_id, range_id,start_num) values (?, ?, ?, ?,?)";
//        String sql = "insert into base_user_active_day(active_date, channel_id, version_id, active_num) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        String start = "2020-05-25";
        String end = "2020-07-25";
        String[] dates = DateUtils.getDays(start, end);

        Random random = new Random();

        //一次启动的使用时长范围
        String[] durationRange = {"1秒-3秒", "4秒-10秒", "11秒-30秒", "31秒-1分", "1分-3分", "3分-10分", "10分-30分", "30分+"};
        //用户一天内启动应用的次数范围
        String[] dayFrequencyRange = {"1-2", "3-5", "6-9", "10-19", "20-49", "50+"};
        //用户一周内启动应用的次数范围
        String[] weekFrequencyRange = {"1-2", "3-5", "6-9", "10-19", "20-49", "50-99", "100+"};
        //用户一月内启动应用的次数范围
        String[] monthFrequencyRange = {"1-2", "3-5", "6-9", "10-19", "20-49", "50-99", "100-199", "200-299", "300+"};
        //用户一次启动内访问的页面数范围。
        String[] pageRange = {"1-2", "3-5", "6-9"};
        //同一用户相邻两次启动间隔的时间长度范围
        String[] intervalRange = {"首次", "0-24h", "1天", "2天", "3天", "4天", "5天", "6天", "7天", "8-14天", "15-30天"};

        /*for (String date : dates) {
            for(int version_id = 1; version_id <= 11; version_id++) {
                for(int channel_id = 1; channel_id <=11; channel_id++) {
                    for (int range_id=1;range_id<=8;range_id++) {
                        preparedStatement.setObject(1, date);
                        preparedStatement.setObject(2, channel_id);
                        preparedStatement.setObject(3, version_id);
                        preparedStatement.setObject(4, range_id);
                        preparedStatement.setObject(5, random.nextInt(100));
                        preparedStatement.setObject(6, random.nextInt(100));
                        preparedStatement.addBatch();   //批处理，减少IO次数，速度更快
                    }
                }
            }*/

        for (String date : dates) {
            for (int version_id = 1; version_id <= 11; version_id++) {
                for (int channel_id = 1; channel_id <= 11; channel_id++) {
                    for (int range_id = 1; range_id <=11; range_id++) {
                        preparedStatement.setObject(1, date);
                        preparedStatement.setObject(2, channel_id);
                        preparedStatement.setObject(3, version_id);
                        preparedStatement.setObject(4, range_id);
                        preparedStatement.setObject(5, random.nextInt(100));
//                        preparedStatement.setObject(6, random.nextInt(100));
                        preparedStatement.addBatch();   //批处理，减少IO次数，速度更快
                    }
                }
            }

            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
        }

        /*for (String date : dates) {
            for (int version_id = 1; version_id <= 11; version_id++) {
                for (int channel_id = 1; channel_id <= 11; channel_id++) {
                    preparedStatement.setObject(1, date);
                    preparedStatement.setObject(2, channel_id);
                    preparedStatement.setObject(3, version_id);
//                        preparedStatement.setObject(4, range_id);
                    preparedStatement.setObject(4, random.nextInt(100));
//                        preparedStatement.setObject(6, random.nextInt(1000));
                    preparedStatement.addBatch();   //批处理，减少IO次数，速度更快

                }
            }

            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
        }*/

        JDBCUtil.closeConnection(connection, preparedStatement);
    }
}
