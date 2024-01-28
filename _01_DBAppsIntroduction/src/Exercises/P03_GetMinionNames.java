package Exercises;

import Constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P03_GetMinionNames {
    private static final String GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID =
            "select m.name, m.age" +
                    " from minions as m" +
                    " join minions_villains as mv on m.id = mv.minion_id" +
                    " where mv.villain_id = ?";

    private static final String GET_VILLAIN_BY_ID = "select v.name from villains as v where v.id = ?";
    private static final String NO_VILLAIN_ID = "No villain with ID %d exists in the database.";
    private static final String VILLAIN_NAME = "Villain: %s%n";
    private static final String MINIONS_FORMAT = "%d. %s %d%n";
    private static final String COLUMN_AGE = "age";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        Scanner scanner = new Scanner(System.in);
        final int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_BY_ID);

        villainStatement.setInt(1, villainId);

        final ResultSet villainResult = villainStatement.executeQuery();

        if (!villainResult.next()) {
            System.out.printf(NO_VILLAIN_ID, villainId);
            connection.close();
            return;
        }

        String villainName = villainResult.getString(Constants.COLUMN_NAME);
        System.out.printf(VILLAIN_NAME, villainName);

        final PreparedStatement minionsStatement = connection.prepareStatement(GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID);
        minionsStatement.setInt(1, villainId);

        final ResultSet resultMinions = minionsStatement.executeQuery();

        for (int i = 1; resultMinions.next(); i++) {
            final String minionName = resultMinions.getString(Constants.COLUMN_NAME);
            final int minionAge = resultMinions.getInt(COLUMN_AGE);
            System.out.printf(MINIONS_FORMAT, i, minionName, minionAge);
        }

        connection.close();
    }
}