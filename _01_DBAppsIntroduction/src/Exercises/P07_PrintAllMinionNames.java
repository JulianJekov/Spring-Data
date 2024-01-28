package Exercises;

import Constants.Constants;
import Constants.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class P07_PrintAllMinionNames {

    public static final String SELECT_NAMES_FROM_MINIONS = "select name from minions";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement minionsNames = connection.prepareStatement(SELECT_NAMES_FROM_MINIONS);

        final ResultSet minionsSet = minionsNames.executeQuery();

        final List<String> minions = new ArrayList<>();

        while (minionsSet.next()) {
            minions.add(minionsSet.getString(Constants.COLUMN_NAME));
        }

        int end = minions.size() - 1;
        int start = 0;

        for (int i = 0; i < minions.size(); i++) {
            System.out.printf("%s%n", i % 2 == 0 ? minions.get(start++) : minions.get(end--));
        }

        connection.close();

    }
}