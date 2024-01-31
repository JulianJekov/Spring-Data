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

        String query = String.format("select * from %s %s limit 1", tableName, where != null ? "where " + where : "");
        ResultSet resultSet = statement.executeQuery(query);

        resultSet.next();
        E entity = table.getDeclaredConstructor().newInstance();

        fillEntity(table, resultSet, entity);
        return entity;
    }

    @Override
    public int delete(E entity) throws IllegalAccessException, SQLException {
        Field id = this.getId(entity.getClass());
        id.setAccessible(true);
        Object o = id.get(entity);
        String query = String.format(
                "delete from %s where id = %d", this.getTableName(entity.getClass()), (long)o);
        return connection.prepareStatement(query).executeUpdate();
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
            field.set(entity, resultSet.getDate(fieldName));
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
        if (!checkIfTableExist(entity.getClass())) {
            this.doCreate(entity.getClass());
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        if (this.checkForNewColumn(fields, entity.getClass())) {
            doAlter(entity.getClass());
        }

        String tableName = this.getTableName(entity.getClass());
        List<String> columnsList = this.getColumnsWithoutId(entity);
        List<String> columnsValueList = this.getColumnsValuesWithoutId(entity);

        String columns = String.join(",", columnsList);
        String columnsValues = String.join(",", columnsValueList);

        String query = String.format("insert into %s (%s) values (%s)", tableName, columns, columnsValues);
        return this.connection.prepareStatement(query).execute();
    }

    private <E> void doAlter(Class entity) throws SQLException {
        List<String> columnsToAdd = new ArrayList<>();
        Field[] fields = entity.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            if (!this.checkIfColumnExist(field, entity)) {
                columnsToAdd.add(this.getColumnName(field) + " " + this.getDBType(field));
            }
        }
        String query = String.format("alter table %s add %s", this.getTableName(entity), String.join(",", columnsToAdd));

        connection.prepareStatement(query).execute();
    }

    private <E> void doCreate(Class entity) throws SQLException {
        String query = String.format("create table %s (", getTableName(entity));
        Field[] fields = entity.getDeclaredFields();
        StringBuilder columnDefinition = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String dbType = this.getDBType(field);
            columnDefinition.append(this.getColumnName(field)).append(" ").append(dbType);

            if (field.isAnnotationPresent(Id.class)) {
                columnDefinition.append(" primary key auto_increment");
            }

            if (i < fields.length - 1) {
                columnDefinition.append(",").append(System.lineSeparator());
            }
        }
        query += columnDefinition + ")";
        connection.prepareStatement(query).execute();
    }

    private String getDBType(Field field) {
        String result = "";
        switch (field.getType().getSimpleName()) {
            case "int":
            case "Integer":
            case "long":
            case "Long":
                result = "int";
                break;
            case "String":
                result = "varchar(50)";
                break;
            case "LocalDate":
                result = "datetime";
                break;
        }
        return result;
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

    private String getColumnName(Field field) {

        if (field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class).name();
        }
        return field.getAnnotation(Id.class).name();
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

    private boolean checkIfTableExist(Class entity) throws SQLException {
        String query = String.format("select table_name from information_schema.tables where table_schema = 'soft_uni'" +
                "and table_name = '%s'", this.getTableName(entity));
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();


        return resultSet.next();
    }

    private boolean checkIfColumnExist(Field field, Class entity) throws SQLException {
        String query = String.format(
                "select table_name from information_schema.columns " +
                        "where table_schema = 'soft_uni' and table_name = '%s' and column_name = '%s'",
                this.getTableName(entity), this.getColumnName(field));
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();

        return resultSet.next();
    }

    private boolean checkForNewColumn(Field[] fields, Class entity) throws SQLException {

        for (Field field : fields) {
            if (!this.checkIfColumnExist(field, entity)) {
                return true;
            }
        }
        return false;
    }
}
