package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.*;

public class UserMapperTest extends BaseMapperTest {

    @Ignore
    @Test
    public void testSelectById() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals("admin", user.getUserName());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectAll() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAll();
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectRolesByUserId() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> roleList = userMapper.selectRolesByUserId(1L);
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testInsert() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test1");
            sysUser.setUserPassword("123456");
            sysUser.setUserEmail("test@mybatis.tk");
            sysUser.setUserInfo("test info");
            sysUser.setHeadImg(new byte[] {1, 2, 3});
            sysUser.setCreateTime(new Date());

            int result = userMapper.insert(sysUser);

            Assert.assertEquals(1, result);
            Assert.assertNull(sysUser.getId());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testInsert2() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test1");
            sysUser.setUserPassword("123456");
            sysUser.setUserEmail("test@mybatis.tk");
            sysUser.setUserInfo("test info");
            sysUser.setHeadImg(new byte[] {1, 2, 3});
            sysUser.setCreateTime(new Date());

            int result = userMapper.insert2(sysUser);

            Assert.assertEquals(1, result);
            Assert.assertNotNull(sysUser.getId());
            System.out.println(sysUser.getId());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testInsert3() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test1");
            sysUser.setUserPassword("123456");
            sysUser.setUserEmail("test@mybatis.tk");
            sysUser.setUserInfo("test info");
            sysUser.setHeadImg(new byte[] {1, 2, 3});
            sysUser.setCreateTime(new Date());

            int result = userMapper.insert3(sysUser);

            Assert.assertEquals(1, result);
            Assert.assertNotNull(sysUser.getId());
            System.out.println(sysUser.getId());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testUpdate() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("admin", user.getUserName());
            user.setUserName("admin_test");
            user.setUserEmail("test@mybatis.tk");

            int result = userMapper.updateById(user);
            Assert.assertEquals(1, result);

            user = userMapper.selectById(1L);
            Assert.assertEquals("admin_test", user.getUserName());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testDeleteById() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user1 = userMapper.selectById(1L);
            Assert.assertNotNull(user1);
            int result = userMapper.deleteById(1L);
            Assert.assertEquals(1, result);
            Assert.assertNull(userMapper.selectById(1L));

            SysUser user2 = userMapper.selectById(1001L);
            Assert.assertNotNull(user2);
            Assert.assertEquals(1, userMapper.deleteById(user2));
            Assert.assertNull(userMapper.selectById(1001L));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectRolesByUserIdAndRoleEnabled() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> roleList = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectRolesByUserAndRole() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysUser user = userMapper.selectById(1L);
            SysRole role = roleMapper.selectById(1L);

            List<SysRole> roleList = userMapper.selectRolesByUserAndRole(user, role);
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectByUser() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            SysUser query = new SysUser();
            List<SysUser> userList = userMapper.selectByUser(query);
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);

            query.setUserName("ad");
            userList = userMapper.selectByUser(query);
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);

            query.setUserName(null);
            query.setUserEmail("test@mybatis.tk");
            userList = userMapper.selectByUser(query);
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);

            query.setUserName("ad");
            query.setUserEmail("test@mybatis.tk");
            userList = userMapper.selectByUser(query);
            Assert.assertNotNull(userList);
            Assert.assertEquals(0, userList.size());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testUpdateByIdSelective() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setId(1L);
            user.setUserEmail("test@mybatis.tk");
            int result = userMapper.updateByIdSelective(user);
            Assert.assertEquals(1, result);
            user = userMapper.selectById(1L);
            Assert.assertEquals("admin", user.getUserName());
            Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectByIdOrUserName() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            SysUser query = new SysUser();
            query.setId(1L);
            query.setUserName("admin");
            SysUser user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);

            query.setId(null);
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);

            query.setUserName(null);
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNull(user);
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectByIdList() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            List<Long> idList = new ArrayList<Long>();
            idList.add(1L);
            idList.add(1001L);

            List<SysUser> userList = userMapper.selectByIdList(idList);
            Assert.assertEquals(2, userList.size());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testInsertList() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            List<SysUser> userList = new ArrayList<SysUser>();
            for (int i = 0; i < 2; i++) {
                SysUser user = new SysUser();
                user.setUserName("test" + i);
                user.setUserPassword("123456");
                user.setUserEmail("test@mybatis.tk");
                userList.add(user);
            }

            int result = userMapper.insertList(userList);

            for (SysUser user: userList
                 ) {
                System.out.println(user.getId());
            }

            Assert.assertEquals(2, result);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testUpdateByMap() {
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 1L);
            map.put("user_email", "test@mybatis.tk");
            map.put("user_password", "12435678");

            userMapper.updateByMap(map);

            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectUserAndRoleById() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getSysRole());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectUserAndRoleById2() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById2(1001L);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getSysRole());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectUserAndRoleById3() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById3(1001L);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getSysRole());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectUserAndRoleById4() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById4(1001L);
            Assert.assertNotNull(user);
            Assert.assertEquals("test", user.getUserName());
            Assert.assertNotNull(user.getSysRole());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectUserAndRoleById5() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById5(1001L);
            Assert.assertNotNull(user);
            Assert.assertEquals("test", user.getUserName());
            Assert.assertNotNull(user.getSysRole());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectUserAndRoleByIdSelect() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleByIdSelect(1001L);
            Assert.assertNotNull(user);
            Assert.assertEquals("test", user.getUserName());
            System.out.println("invoke user.equals(null)");
            user.equals(null);
            System.out.println("invoke user.getSysRole()");
            Assert.assertNotNull(user.getSysRole());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectAllUserAndRoles() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAllUserAndRoles();
            System.out.println("user size: " + userList.size());
            for (SysUser user :
                    userList) {
                System.out.println("user name: " + user.getUserName());
                for (SysRole role :
                        user.getRoleList()) {
                    System.out.println("\trole name: " + role.getRoleName());
                    for (SysPrivilege privilege :
                            role.getPrivilegeList()) {
                        System.out.println("\t\tprivilege name: " + privilege.getPrivilegeName());
                    }
                }
            }
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectAllUserAndRoles2() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAllUserAndRoles2();
            System.out.println("user size: " + userList.size());
            for (SysUser user :
                    userList) {
                System.out.println("user name: " + user.getUserName());
                for (SysRole role :
                        user.getRoleList()) {
                    System.out.println("\trole name: " + role.getRoleName());
                    for (SysPrivilege privilege :
                            role.getPrivilegeList()) {
                        System.out.println("\t\tprivilege name: " + privilege.getPrivilegeName());
                    }
                }
            }
        } finally {
            sqlSession.close();
        }
    }
}
