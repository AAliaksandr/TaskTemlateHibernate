package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS USER (id INTEGER NOT NULL AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age INTEGER, PRIMARY KEY (id));";
        methodDo(create);
    }

    @Override
    public void dropUsersTable() {
        String dell = "DROP TABLE database.user;";
        methodDo(dell);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null){
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            list = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null){
                session.close();
            }
        }
        return list;
    }


    @Override
    public void cleanUsersTable() {
        String clean = "TRUNCATE TABLE database.user;";
        methodDo(clean);

    }
    private void methodDo(String request) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery(request).executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null){
                session.close();
            }
        }
    }
}
