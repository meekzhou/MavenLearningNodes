package org.zttc.dao;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zttc.bean.User;
import org.zttc.util.AbstractDbUnitTestCase;
import org.zttc.util.EntitiesHelper;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by zhouweitao on 16/9/5.
 */
public class TestUserDao extends AbstractDbUnitTestCase {
    private IUserDao userDao;

    @Before
    public void setUp() throws DataSetException, IOException, SQLException {
        userDao = new UserDao();
        bakcupOneTable("t_user");
    }

    @Test
    public void testLoad() throws IOException, DatabaseUnitException, SQLException {
        System.out.println("test load ...");
        IDataSet ds = createDateSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
        User tu = userDao.load("admin");
        EntitiesHelper.assertUser(tu);
    }

    @Test
    public void testAdd() throws IOException, DatabaseUnitException, SQLException {
        System.out.println("test add ...");
        IDataSet ds = createDateSet("t_user");
        DatabaseOperation.TRUNCATE_TABLE.execute(dbunitCon,ds);
        User user = new User("admin", "123", "管理员");
        userDao.add(user);
        Assert.assertTrue(user.getId()>0);

        User tu = userDao.load("admin");
        EntitiesHelper.assertUser(tu, user);
    }

    @After
    public void tearDown() throws DatabaseUnitException, SQLException, IOException {
        resumeTable();
    }
}
