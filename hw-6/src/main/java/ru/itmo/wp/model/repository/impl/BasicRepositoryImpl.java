package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BasicRepositoryImpl<T> {
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    private final String modelName;

    public BasicRepositoryImpl(String modelName) {
        this.modelName = modelName;
    }

    protected abstract T toModel(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;

    public T find(long id) {
        return findByKeys(new KeyTrinity("id", KeyType.LONG, id));
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

    protected T findByKeys(KeyTrinity... keys) {
        int keyCount = keys.length;
        try (Connection connection = DATA_SOURCE.getConnection()) {
            StringBuilder str = new StringBuilder();
            for (KeyTrinity trinity : keys) {
                if (str.length() == 0) {
                    str.append("`").append(trinity.key).append("`=?");
                } else {
                    str.append(" AND `").append(trinity.key).append("`=?");
                }
            }
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + modelName
                    + "` WHERE " + str.toString())) {
                for (int i = 0; i < keys.length; i++) {
                    switch (keys[i].keyType) {
                        case STRING:
                            statement.setString(i + 1, (String) keys[i].val);
                            break;
                        case LONG:
                            statement.setLong(i + 1, (Long) keys[i].val);
                            break;
                        default:
                            throw new RepositoryException("Unsupported key type");
                    }
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toModel(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + modelName + ".", e);
        }
    }

    protected static class KeyTrinity {
        final String key;
        final KeyType keyType;
        final Object val;

        public KeyTrinity(String key, KeyType keyType, Object val) {
            this.key = key;
            this.keyType = keyType;
            this.val = val;
        }
    }

    protected enum KeyType {
        STRING, LONG;
    }
}
