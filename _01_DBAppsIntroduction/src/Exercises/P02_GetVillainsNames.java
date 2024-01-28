package Exercises;

import Constants.Utils;
import Constants.Constants;

import java.sql.*;
import java.util.Scanner;

public class P02_GetVillainsNames {
    private static final String GET_VILLAINS_NAMES =
            "select v.name, count(distinct mv.minion_id) as minions_count" +
                    " from villains as v" +
                    " join minions_villains mv on v.id = mv.villain_id" +
                    " group by v.name" +
                    " having minions_count > ?" +
                    " order by minions_count desc";
    private final static String MINIONS_COUNT = "minions_count";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        final int inputMinionsCount = new Scanner(System.in).nextInt();

        final PreparedStatement statement = connection.prepareStatement(GET_VILLAINS_NAMES);
        statement.setInt(1, inputMinionsCount);

        final ResultSet result = statement.executeQuery();


        while (result.next()) {
            final String villainName = result.getString(Constants.COLUMN_NAME);
            final int minionsCount = result.getInt(MINIONS_COUNT);

            System.out.printf("%s %d", villainName, minionsCount);
        }

        connection.close();
    }
}