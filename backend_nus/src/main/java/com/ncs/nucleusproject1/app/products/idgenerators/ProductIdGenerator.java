package com.ncs.nucleusproject1.app.products.idgenerators;/*package com.ncs.nucleusproject1.app.orders.idgenerators;

/*@author: Shannon Heng, 14 October 2023*/
/*modified on 14 Nov by Shannon to include seller id*/

import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object){

        String prefix = "P";
        JdbcConnectionAccess con = session.getJdbcConnectionAccess();

        try {
            JdbcConnectionAccess jdbcConnectionAccess = session.getJdbcConnectionAccess();
            Connection connection = jdbcConnectionAccess.obtainConnection();
            Statement statement = connection.createStatement();
            String query = "select count(pdtid) as Id from products";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int id=resultSet.getInt(1)+1001;
                String generatedId = prefix + id;
                return generatedId;
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }}

