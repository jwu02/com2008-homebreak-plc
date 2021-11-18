package database;

import models.Address;

import java.sql.*;

import static database.OpenConnection.getConnection;

public class InsertAddress {
    public static int insertAddress(Address addressToInsert) throws SQLException {
        try (Connection con = getConnection()) {
            String query = "SELECT AddressID FROM Addresses WHERE House=? AND Postcode=? LIMIT 1";
            PreparedStatement statement = con.prepareStatement(query);
            statement.clearParameters();
            statement.setString(1, addressToInsert.getHouse());
            statement.setString(2, addressToInsert.getPostcode());

            ResultSet resultSet = statement.executeQuery();

            // insert new address if address doesn't exist already otherwise return AddressID of existing address
            if (!resultSet.isBeforeFirst()) {
                query = "INSERT INTO Addresses VALUES (null, ?, ?, ?, ?)";

                statement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                statement.clearParameters();
                statement.setString(1, addressToInsert.getHouse());
                statement.setString(2, addressToInsert.getStreet());
                statement.setString(3, addressToInsert.getPlace());
                statement.setString(4, addressToInsert.getPostcode());

                int insertedRows = statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to create address, no AddressID returned.");
                    }
                }
            } else {
                resultSet.next();

                return resultSet.getInt("AddressID");
            }
        }
    }
}
