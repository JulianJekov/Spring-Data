package Exercises;

import Constants.Utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class P09_IncreaseAgeSortedProcedure {

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final int id = new Scanner(System.in).nextInt();

        final CallableStatement increaseMinionAgeByIdProcedure = connection.prepareCall("CALL usp_get_older(?)");
        increaseMinionAgeByIdProcedure.setInt(1, id);

        increaseMinionAgeByIdProcedure.executeUpdate();
    }

//    create procedure usp_get_older (minion_id int)
//    begin
//    update minions set age = age + 1
//    where id = minion_id;
//    end;
}