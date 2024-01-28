package Exercises;

import Constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class P04_AddMinion {

    private static final String GET_TOWN_BY_NAME = "select id from towns where name = ?";
    private static final String INSERT_INTO_TOWNS = "insert into towns (name) values (?)";
    private static final String PRINT_ADDED_TOWN = "Town %s was added to the database.%n";

    private static final String GET_VILLAIN_BY_NAME = "select id from villains where name = ?";
    private static final String INSERT_INTO_VILLAINS = "insert into villains (name, evilness_factor) values (?,?)";
    private static final String EVILNESS_FACTOR = "evil";
    private static final String PRINT_ADDED_VILLAIN = "Villain %s was added to the database.%n";

    private static final String INSERT_MINION = "insert into minions (name, age, town_id) values (?, ?, ?)";
    private static final String GET_LAST_MINION_ID = "select id from minions order by id desc limit 1";
    private static final String INSERT_INTO_MINIONS_VILLAINS = "insert into minions_villains values (?, ?)";
    private static final String PRINT_ADDED_MINION_TO_VILLAIN = "Successfully added %s to be minion of %s.%n";


    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        Scanner scanner = new Scanner(System.in);

        final String[] minionInfo = scanner.nextLine().split(" ");

        final String minionName = minionInfo[1];
        final int minionAge = Integer.parseInt(minionInfo[2]);
        final String minionTown = minionInfo[3];

        final String villainName = scanner.nextLine().split(" ")[1];

        final int townId = getId(connection, List.of(minionTown), GET_TOWN_BY_NAME, INSERT_INTO_TOWNS, PRINT_ADDED_TOWN);
        final int villainId = getId(connection, List.of(villainName, EVILNESS_FACTOR), GET_VILLAIN_BY_NAME, INSERT_INTO_VILLAINS, PRINT_ADDED_VILLAIN);

        final PreparedStatement insertMinion = connection.prepareStatement(INSERT_MINION);
        insertMinion.setString(1, minionName);
        insertMinion.setInt(2, minionAge);
        insertMinion.setInt(3, townId);

        insertMinion.executeUpdate();

        final PreparedStatement getLastMinionId = connection.prepareStatement(GET_LAST_MINION_ID);
        final ResultSet lastMinionSet = getLastMinionId.executeQuery();
        lastMinionSet.next();
        final int minionId = lastMinionSet.getInt(Constants.COLUMN_ID);

        final PreparedStatement insertIntoMinionsVillains = connection.prepareStatement(INSERT_INTO_MINIONS_VILLAINS);
        insertIntoMinionsVillains.setInt(1, minionId);
        insertIntoMinionsVillains.setInt(2, villainId);
        insertIntoMinionsVillains.executeUpdate();

        System.out.printf(PRINT_ADDED_MINION_TO_VILLAIN, minionName, villainName);

        connection.close();
    }

    private static int getId(Connection connection, List<String> args, String getQuery, String insertQuery, String printFormat) throws SQLException {
        String name = args.getFirst();
        final PreparedStatement selectQuery = connection.prepareStatement(getQuery);
        selectQuery.setString(1, name);

        final ResultSet resultSet = selectQuery.executeQuery();

        if (!resultSet.next()) {
            insertInToQuery(connection, args, insertQuery);
            ResultSet newResultSet = selectQuery.executeQuery();
            newResultSet.next();
            System.out.printf(printFormat, name);
            return newResultSet.getInt(Constants.COLUMN_ID);
        }
        return resultSet.getInt(Constants.COLUMN_ID);
    }

    private static void insertInToQuery(Connection connection, List<String> args, String insertQuery) throws SQLException {
        final PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

        for (int i = 1; i <= args.size(); i++) {
            insertStatement.setString(i, args.get(i - 1));
        }
        insertStatement.executeUpdate();
    }
}