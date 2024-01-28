package orm;

import orm.annotations.Column;
import orm.annotations.Entity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String tableName = this.getTableName(entity.getClass());
        String fieldList = this.getDBFieldsWithoutId(entity);
        String valueList = this.getValuesWithoutId(entity);

        String sql = String.format("insert into %s (%s) values (%s)", tableName, fieldList, valueList);
        return this.connection.prepareStatement(sql).execute();
    }

    private String getValuesWithoutId(E entity) throws IllegalAccessException {
        List<String> result = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.getAnnotation(Column.class) == null) {
                continue;
            }
            declaredField.setAccessible(true);
            Object value = declaredField.get(entity);
            result.add("\"" + value.toString() + "\"");

        }
        return String.join(",", result);
    }

    private String getDBFieldsWithoutId(E entity) {
        return Arrays.stream(entity
                        .getClass()
                        .getDeclaredFields())
                .filter(f -> f.getAnnotation(Column.class) != null)
                .map(f -> f.getAnnotation(Column.class).name())
                .collect(Collectors.joining(","));

    }

    private String getTableName(Class<?> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);

        if (annotation == null) {
            throw new ORMException("Provided class does not have Entity annotation");
        }

        return annotation.name();
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) {
        return null;
    }

    @Override
    public E findFirst(Class<E> entityType, String where) throws SQLException {
        String tableName = this.getTableName(entityType);
        String sql = String.format("select * from %s %s limit 1", tableName, where == null ? "" : "where " + where);

        ResultSet resultSet = this.connection.prepareStatement(sql).executeQuery();

        return this.createEntity(entityType, resultSet);
    }

    private E createEntity(Class<E> entityType, ResultSet resultSet) {
        return null;
    }


}
