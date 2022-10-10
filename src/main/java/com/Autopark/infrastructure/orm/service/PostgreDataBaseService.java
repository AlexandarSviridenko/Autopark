package com.Autopark.infrastructure.orm.service;

import com.Autopark.Exception.NotFindInTableIdField;
import com.Autopark.infrastructure.core.Context;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;
import com.Autopark.infrastructure.orm.ConnectionFactory;
import com.Autopark.infrastructure.orm.annotations.Column;
import com.Autopark.infrastructure.orm.annotations.ID;
import com.Autopark.infrastructure.orm.annotations.Table;
import com.Autopark.infrastructure.orm.enums.SqlFieldType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class PostgreDataBaseService {
    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "   SELECT FROM information_schema.sequences \n" +
                    "   WHERE sequence_schema = 'public'\n" +
                    "   AND   sequence_name   = '%s'\n" +
                    ");";
    private static final String CREATE_ID_SEQ_PATTERN =
            "CREATE SEQUENCE %S\n" +
                    "   INCREMENT 1\n" +
                    "   START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "   SELECT FROM information_schema.tables \n" +
                    "   WHERE table_schema = 'public' \n" +
                    "   AND   table_name   = '%s'\n" +
                    ");";
    private static final String CREATE_TABLE_SQL_PATTERN =
            "CREATE TABLE %s (\n" +
                    "    %s integer PRIMARY KEY DEFAULT nextval('%s')" +
                    "    %s\n);";
    private static final String INSERT_SQL_PATTERN =
            "INSERT INTO %s(%s)\n" +
                    "   VALUES (%s)\n" +
                    "   RETURNING %s;";
    private static final String DELETE_ROW_SQL_PATTERN =
            "DELETE FROM %s\n" +
                    "   WHERE %s = %s\n" +
                    "   RETURNING *;";
    private static final String VEHICLE_IS_BROKEN_SQL_PATTERN =
            "SELECT FROM ORDERS\n" +
                    "   WHERE %s = %s\n" +
                    "   RETURNING *;";
    private static final String RETURN_BY_ID_SQL_PATTERN =
            "SELECT * FROM %s\n" +
                    "   WHERE %s = %s\n";
    private static final String RETURN_ALL_SQL_PATTERN =
            "SELECT * FROM %s\n";

    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private Context context;

    private Map<String, String> classToSql;
    private Map<String, String> insertPatternByClass;
    private Map<String, String> insertByClassPattern;

    public PostgreDataBaseService() {
    }

    @InitMethod
    public void init() throws NotFindInTableIdField {
        classToSql = Arrays.stream(SqlFieldType.values())
                .collect(Collectors.toMap(sqlFieldType -> sqlFieldType.getType().getName(),
                        SqlFieldType::getSqlType));

        insertPatternByClass = Arrays.stream(SqlFieldType.values())
                .collect(Collectors.toMap(sqlFieldType -> sqlFieldType.getType().getName(),
                        SqlFieldType::getInsertPattern));

        if (!isNameExists(CHECK_SEQ_SQL_PATTERN, SEQ_NAME)) {
            executeCreate(CREATE_ID_SEQ_PATTERN, SEQ_NAME);
        }

        Set<Class<?>> entities = context.getConfig()
                .getScanner()
                .getReflections()
                .getTypesAnnotatedWith(Table.class);

        checkEntityFields(entities);
        findOtherwiseCreateTables(entities);

        insertByClassPattern = new HashMap<>();
        entities.stream().forEach(entity -> {
            try {
                insertByClassPattern.put(entity.getName(), getInsertQuery(entity));
            } catch (NotFindInTableIdField e) {
                e.printStackTrace();
            }
        });
    }

    @SneakyThrows
    public Long save(Object obj) {
        Long id;

        Field idField = getIdField(obj.getClass().getDeclaredFields());
        Object[] values = Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return new Object();
                })
                .toArray();
        String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), values);
        id = executeInsert(sql, getIdFieldName(idField));

        setIdField(obj, idField, id);

        return id;
    }

    @SneakyThrows
    public <T> Optional<T> delete(Long id, Class<T> clazz) {
        checkTableAnnotation(clazz);

        String sql = String.format(DELETE_ROW_SQL_PATTERN, clazz.getDeclaredAnnotation(Table.class).name()
                , getColumnFieldName(clazz.getDeclaredFields())
                , id);

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return Optional.of(makeObject(resultSet, clazz));
            }
        }

        return Optional.empty();
    }

    @SneakyThrows
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        checkTableAnnotation(clazz);

        String sql = String.format(RETURN_BY_ID_SQL_PATTERN, clazz.getDeclaredAnnotation(Table.class).name(),getIdFieldName(clazz.getDeclaredFields()), id);

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return Optional.of(makeObject(resultSet, clazz));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @SneakyThrows
    public <T> Optional<T> getColumn(Long id, Class<T> clazz) {
        checkTableAnnotation(clazz);

        String sql = String.format(RETURN_BY_ID_SQL_PATTERN, clazz.getDeclaredAnnotation(Table.class).name(), getColumnFieldName(clazz.getDeclaredFields()), id);

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return Optional.of(makeObject(resultSet, clazz));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @SneakyThrows
    public <T> List<T> getAll(Class<T> clazz) {
        List<T> list = new ArrayList<>();

        checkTableAnnotation(clazz);

        String sql = String.format(RETURN_ALL_SQL_PATTERN, clazz.getDeclaredAnnotation(Table.class).name());

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(makeObject(resultSet, clazz));
            }
        }

        return list;
    }


    @SneakyThrows
    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) {
        Method method;

        T obj = clazz.getConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                String idName = field.getAnnotation(ID.class).name();
                method = getMethodForType(field.getType());
                setField(field, obj, method.invoke(resultSet, idName));
            }
            if (field.isAnnotationPresent(Column.class)) {
                String columnName = field.getAnnotation(Column.class).name();
                method = getMethodForType(field.getType());
                setField(field, obj, method.invoke(resultSet, columnName));
            }
        }
        return obj;
    }

    @SneakyThrows
    private Method getMethodForType(Class<?> type) {
        if (type.equals(Integer.class)) {
            return ResultSet.class.getMethod("getInt", String.class);
        } else {
            return ResultSet.class.getMethod("get" + type.getSimpleName(), String.class);
        }
    }

    @SneakyThrows
    private <T> void setField(Field field, T obj, Object value) {
        field.setAccessible(true);
        field.set(obj, value);
    }

    @SneakyThrows
    private Long executeInsert(String sql, String idFieldName) {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            resultSet.next();
            return resultSet.getLong(idFieldName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private boolean isNameExists(String checkSqlPattern, String name) {
        ResultSet resultSet = null;

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = String.format(checkSqlPattern, name);
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getBoolean("exists");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return false;
    }

    private void executeCreate(String pattern, String... params) {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = String.format(pattern, params);
            statement.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private <T> void checkTableAnnotation(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " hasn't got 'Table' annotation");
        }
    }

    private void checkEntityFields(Set<Class<?>> entities) {
        for (Class<?> entity : entities) {
            Field[] fields = entity.getDeclaredFields();

            if (!isCorrectID(fields)) {
                throw new IllegalStateException("Class " + entity.getName() + " doesn't contain " +
                        "correct 'ID' field");
            }

            if (!isCorrectColumnFields(fields)) {
                throw new IllegalStateException("Class " + entity.getName() + " contains fields 'Column'" +
                        " with identical names or primitive classes");
            }
        }
    }

    private boolean isCorrectColumnFields(Field[] fields) {
        Set<String> names = new HashSet<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                String name = field.getAnnotation(Column.class).name();

                if (!names.add(name) || field.getType().isPrimitive()) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isCorrectID(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(ID.class)) {
                if (field.getType().equals(Long.class)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void findOtherwiseCreateTables(Set<Class<?>> entities) throws NotFindInTableIdField {
        for (Class<?> entity : entities) {
            if (!isNameExists(CHECK_TABLE_SQL_PATTERN, entity.getDeclaredAnnotation(Table.class).name())) {
                createTable(entity);
            }
        }
    }

    private void createTable(Class<?> entity) throws NotFindInTableIdField {
        Field[] declaredFields = entity.getDeclaredFields();

        String tableName = entity.getDeclaredAnnotation(Table.class).name();
        String idField = getIdFieldName(declaredFields);
        String fields = getFieldsQuery(declaredFields);

        executeCreate(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, fields);
    }

    private String getInsertQuery(Class<?> entity) throws NotFindInTableIdField {
        Field[] declaredFields = entity.getDeclaredFields();

        String tableName = entity.getDeclaredAnnotation(Table.class).name();
        String idField = getIdFieldName(declaredFields);
        String insertFields = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getDeclaredAnnotation(Column.class).name())
                .reduce((name1, name2) -> name1 + ", " + name2).get();

        String values = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> insertPatternByClass.get(field.getType().getName()))
                .reduce((name1, name2) -> name1 + ", " + name2).get();

        return String.format(INSERT_SQL_PATTERN, tableName, insertFields, values, idField);
    }

    private String getFieldsQuery(Field[] fields) {
        StringBuilder strBuilder = new StringBuilder();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getDeclaredAnnotation(Column.class);

                strBuilder.append(buildColumnQuery(column, field.getType().getName()));
            }
        }

        return strBuilder.toString();
    }

    private StringBuilder buildColumnQuery(Column column, String className) {
        StringBuilder strBuilder = new StringBuilder();
        String sqlType = classToSql.get(className);

        strBuilder.append(",\n\t");
        strBuilder.append(column.name());
        strBuilder.append(" ").append(sqlType);
        strBuilder.append(column.nullable() ? "" : " NOT NULL");
        strBuilder.append(column.unique() ? " UNIQUE" : "");

        return strBuilder;
    }

    private String getIdFieldName(Field... fields) throws NotFindInTableIdField {
        for (Field field : fields) {
            if (field.isAnnotationPresent(ID.class)) {
                return field.getAnnotation(ID.class).name();
            }
        }

        throw new NotFindInTableIdField("Table hasn't got 'ID' field");
    }

    private String getColumnFieldName(Field... fields) throws NotFindInTableIdField {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                return field.getAnnotation(Column.class).name();
            }
        }

        throw new NotFindInTableIdField("Table hasn't got 'ID' field");
    }

    private Field getIdField(Field[] fields) throws NotFindInTableIdField {
        for (Field field : fields) {
            if (field.isAnnotationPresent(ID.class)) {
                return field;
            }
        }

        throw new NotFindInTableIdField("Table hasn't got 'ID' field");
    }

    private void setIdField(Object obj, Field idField, Long id) {
        try {
            idField.setAccessible(true);
            idField.set(obj, id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
