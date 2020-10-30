package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.*;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BasicRepositoryImpl<T> {
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    private final String modelName;

    public BasicRepositoryImpl(String modelName) {
        this.modelName = modelName;
    }

    protected abstract T toModel(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;

    public T find(long id) {
        return findByKeys(new Pair("id", id));
    }

    public List<T> findAll() {
        List<T> models = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + modelName + "` ORDER BY `id` DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    T model;
                    while ((model = toModel(statement.getMetaData(), resultSet)) != null) {
                        models.add(model);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + modelName + ".", e);
        }
        return models;
    }

    public long findCount() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM `" + modelName + "`")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return 0;
                    }
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("can't count " + modelName + "s.", e);
        }
    }

    protected T findByKeys(Pair... pairs) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            String params = Arrays.stream(pairs).map((el) -> String.format("`%s`=?", el.key))
                    .collect(Collectors.joining(" AND "));

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + modelName
                    + "` WHERE " + params)) {
                for (int i = 0; i < pairs.length; i++) {
                    statement.setObject(i + 1, pairs[i].val);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toModel(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + modelName + ".", e);
        }
    }

    protected static class Pair {
        final String key;
        final Object val;

        public Pair(String key, Object val) {
            this.key = key;
            this.val = val;
        }
    }
}
