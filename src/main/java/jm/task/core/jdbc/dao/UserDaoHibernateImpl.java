package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

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
        Session session =  sessionFactory.getCurrentSession();
        Transaction transaction  = session.beginTransaction();
        try {
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session =  sessionFactory.getCurrentSession();
        Transaction transaction  = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Session session =  sessionFactory.getCurrentSession();
        Transaction transaction  = session.beginTransaction();
        try {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            list = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();

        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
                e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String clean = "TRUNCATE TABLE database.user;";
        methodDo(clean);
    }

    private void methodDo(String request) {
        Session session =  sessionFactory.getCurrentSession();
        Transaction transaction  = session.beginTransaction();
        try {
            session.createSQLQuery(request).executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
