package Exercises;

import Constants.Constants;
import Constants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P06_RemoveVillain {

    public static final String SELECT_VILLAIN_NAME_BY_ID = "select name from villains where id = ?";
    public static final String SELECT_COUNT_MINIONS_BY_VILLAIN_ID =
            "select count(minion_id) as count_minions from minions_villains where villain_id = ?";

    public static final String COLUMN_COUNT_MINIONS = "count_minions";
    public static final String DELETE_FROM_MINIONS_VILLAINS_BY_VILLAIN_ID = "delete from minions_villains where villain_id = ?";
    public static final String DELETE_VILLAIN_BY_ID = "delete from villains where id = ?";

    public static final String PRINT_IF_NO_VILLAIN_FOUND = "No such villain was found";
    public static final String PRINT_DELETED_VILLAIN = "%s was deleted%n";
    public static final String PRINT_COUNT_MINIONS_RELEASED = "%d minions released";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final int villainId = new Scanner(System.in).nextInt();

        final PreparedStatement selectVillain = connection.prepareStatement(SELECT_VILLAIN_NAME_BY_ID);
        selectVillain.setInt(1, villainId);
        final ResultSet villainSet = selectVillain.executeQuery();

        if (!villainSet.next()) {
            System.out.println(PRINT_IF_NO_VILLAIN_FOUND);
            connection.close();
            return;
        }

        final String villainName = villainSet.getString(Constants.COLUMN_NAME);

        final PreparedStatement selectAllVillainMinions = connection.prepareStatement(SELECT_COUNT_MINIONS_BY_VILLAIN_ID);
        selectAllVillainMinions.setInt(1, villainId);

        final ResultSet countMinionsSet = selectAllVillainMinions.executeQuery();
        countMinionsSet.next();
        final int countReleasedMinions = countMinionsSet.getInt(COLUMN_COUNT_MINIONS);

        connection.setAutoCommit(false);

        try {
            final PreparedStatement deleteMinionsVillains = connection.prepareStatement(DELETE_FROM_MINIONS_VILLAINS_BY_VILLAIN_ID);
            deleteMinionsVillains.setInt(1, villainId);
            deleteMinionsVillains.executeUpdate();

            final PreparedStatement deleteVillain = connection.prepareStatement(DELETE_VILLAIN_BY_ID);
            deleteVillain.setInt(1, villainId);
            deleteVillain.executeUpdate();

            connection.commit();

            System.out.printf(PRINT_DELETED_VILLAIN, villainName);
            System.out.printf(PRINT_COUNT_MINIONS_RELEASED, countReleasedMinions);

        } catch (SQLException e) {
            connection.rollback();
            connection.close();
        }
        connection.close();
    }
}