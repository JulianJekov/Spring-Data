import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Connector.createConnection("root", "12345", "soft_uni");

        Connection connection = Connector.getConnection();

        EntityManager<User> userManager = new EntityManager<>(connection);
        User user = new User("Second", 30, new Date(), 1000);
        User user1 = userManager.findFirst(User.class, "id = 2");
        userManager.delete(user1);



    }
}
