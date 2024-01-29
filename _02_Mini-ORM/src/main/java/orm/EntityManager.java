package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DBContext<E> {

    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        Field primaryKey = getId(entity.getClass());
        primaryKey.setAccessible(true);
        Object idValue = primaryKey.get(entity);

        if (idValue == null || (long) idValue <= 0) {
            return doInsert(entity);
        }

        return doUpdate(entity, idValue);
    }
    @Override
    public Iterable<E> find(Class<E> table)
            throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {

        return find(table, null);
    }
    @Override
    public Iterable<E> find(Class<E> table, String where)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Statement statement = connection.createStatement();
        String tableName = getTableName(table);

        String query = String.format("select * from %s %s", tableName, where != null ? "where" + where : "");
        ResultSet resultSet = statement.executeQuery(query);
        List<E> entities = new ArrayList<>();
        while (resultSet.next()) {
            E entity = table.getDeclaredConstructor().newInstance();
            fillEntity(table, resultSet, entity);
            entities.add(entity);
        }
        return entities;
    }
    @Override
    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }
    @Override
    public E findFirst(Class<E> table, String where)
            throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String tableName = getTableName(table);

        String query = String.format("select * from %s %s limit 1", tableName, where != null ? "where" + where : "");
        ResultSet resultSet = statement.executeQuery(query);

        resultSet.next();
        E entity = table.getDeclaredConstructor().newInstance();

        fillEntity(table, resultSet, entity);
        return entity;
    }
    private void fillEntity(Class<E> table, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Field[] declaredFields = Arrays.stream(table.getDeclaredFields()).toArray(Field[]::new);
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Column columnAnnotation = field.getAnnotation(Column.class);

            String fieldName = columnAnnotation == null ? field.getName() : columnAnnotation.name();

            fillFields(field, resultSet, entity, fieldName);
        }
    }
    private void fillFields(Field field, ResultSet resultSet, E entity, String fieldName) throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == int.class || field.getType() == long.class) {
            field.set(entity, resultSet.getInt(fieldName));
        } else if (field.getType() == String.class) {
            field.set(entity, resultSet.getString(fieldName));
        } else {
            field.set(entity, LocalDate.parse(resultSet.getString(fieldName)));
        }
    }
    private boolean doUpdate(E entity, Object id) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        List<String> columnsList = this.getColumnsWithoutId(entity);
        List<String> columnsValueList = this.getColumnsValuesWithoutId(entity);

        List<String> setStatements = new ArrayList<>();

        for (int i = 0; i < columnsList.size(); i++) {
            String statement = columnsList.get(i) + " = " + columnsValueList.get(i);
            setStatements.add(statement);
        }

        String setValues = String.join(",", setStatements);

        String query = String.format
                ("update %s set %s where id = %s", tableName, setValues, id);
        PreparedStatement statement = this.connection.prepareStatement(query);

        return statement.execute();
    }

    private boolean doInsert(E entity) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        List<String> columnsList = this.getColumnsWithoutId(entity);
        List<String> columnsValueList = this.getColumnsValuesWithoutId(entity);

        String columns = String.join(",", columnsList);
        String columnsValues = String.join(",", columnsValueList);

        String query = String.format("insert into %s (%s) values (%s)", tableName, columns, columnsValues);
        return this.connection.prepareStatement(query).execute();
    }
    private List<String> getColumnsValuesWithoutId(E entity) throws IllegalAccessException {
        List<String> values = new ArrayList<>();

        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }
            declaredField.setAccessible(true);
            Object fieldValue = declaredField.get(entity);
            values.add("\"" + fieldValue.toString() + "\"");
        }
        return values;
    }
    private List<String> getColumnsWithoutId(E entity) {
        return Arrays.stream(entity.getClass()
                        .getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .map(f -> f.getAnnotation(Column.class).name())
                .collect(Collectors.toList());
    }
    private String getTableName(Class<?> entity) {
        Entity tableName = entity.getAnnotation(Entity.class);

        if (tableName == null) {
            throw new ORMException("Provided class does not have Entity annotation");
        }
        return tableName.name();
    }
    private Field getId(Class<?> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }
}
