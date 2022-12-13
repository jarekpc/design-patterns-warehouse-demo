package com.example.warehouse.dal;

import com.example.warehouse.Customer;
import com.example.warehouse.WarehouseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DbCustomerDao implements CustomerDao {

    // INFO: on first connection the in-memory DB will be initialized by init.sql located on the classpath.
    private static final String DEFAULT_JDBC_URL = "jdbc:h2:mem:warehouse;INIT=RUNSCRIPT FROM 'classpath:scripts/init.sql'";

    public DbCustomerDao() {
    }

    @Override
    public Collection<Customer> getCustomers() throws WarehouseException {
        try (Connection connection = DriverManager.getConnection(DEFAULT_JDBC_URL);
             Statement statement = connection.createStatement()) {
            List<Customer> customers = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery("SELECT * FROM customers")) {
                while (rs.next()) {
                    final int id = rs.getInt("id");
                    final String name = rs.getString("name");
                    customers.add(new Customer(id, name));
                }
            }
            return customers;
        } catch (SQLException ex) {
            throw new WarehouseException("There war an error while fetching data.",ex);
        }
    }

    @Override
    public Customer getCustomer(int id) {
        try (Connection connection = DriverManager.getConnection(DEFAULT_JDBC_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE id = ?")) {
            List<Customer> customers = new ArrayList<>();
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return toCustomer(rs);
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private Customer toCustomer(ResultSet rs) throws SQLException {
        final int id = rs.getInt("id");
        final String name = rs.getString("name");
        return new Customer(id, name);
    }
}
