package Exercises;

import Constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class P05_ChangeTownNamesCasing {

    private static final String UPDATE_TOWN_NAMES_UPPER_CASE = "update towns t set t.name = upper(t.name) where t.country = ?";
    public static final String NO_TOWN_NAMES_WERE_AFFECTED = "No town names were affected.";
    public static final String COUNT_TOWN_NAMES_WERE_AFFECTED = "%d town names were affected.%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final String country = new Scanner(System.in).nextLine();

        final PreparedStatement townNamesToUpperCase = connection.prepareStatement(UPDATE_TOWN_NAMES_UPPER_CASE);
        townNamesToUpperCase.setString(1, country);

        final int countChangedTownNames = townNamesToUpperCase.executeUpdate();

        if (countChangedTownNames == 0) {
            System.out.println(NO_TOWN_NAMES_WERE_AFFECTED);
            connection.close();
            return;
        }

        System.out.printf(COUNT_TOWN_NAMES_WERE_AFFECTED, countChangedTownNames);

        List<String> townNames = new ArrayList<>();

        final PreparedStatement getTownNames = connection.prepareStatement("select t.name from towns t where t.country = ?");
        getTownNames.setString(1, country);

        final ResultSet townsSet = getTownNames.executeQuery();

        while (townsSet.next()) {
            final String townName = townsSet.getString(Constants.COLUMN_NAME);
            townNames.add(townName);
        }

        System.out.println(townNames);

        connection.close();
    }
}