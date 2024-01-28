package Exercises;

import Constants.Constants;
import Constants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class P08_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final int[] IDs = Arrays.stream(new Scanner(System.in).nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        final PreparedStatement updateMinionsAgeByGivenIDs = connection.prepareStatement("update minions set age = age + 1 where id in (?,?,?)");
        for (int i = 0; i < IDs.length; i++) {
            updateMinionsAgeByGivenIDs.setInt(i + 1, IDs[i]);
        }

        updateMinionsAgeByGivenIDs.executeUpdate();

        final PreparedStatement minionsAgeAndName = connection.prepareStatement("select name, age from minions");
        final ResultSet minionsSet = minionsAgeAndName.executeQuery();

        while (minionsSet.next()) {
            final String minionName = minionsSet.getString(Constants.COLUMN_NAME);
            final int minionAge = minionsSet.getInt(Constants.COLUMN_AGE);

            System.out.printf("%s %d%n", minionName.toLowerCase(), minionAge);
        }

        connection.close();
    }
}