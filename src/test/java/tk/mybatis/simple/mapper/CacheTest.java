package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class CacheTest extends BaseMapperTest {

    @Ignore
    @Test
    public void testL1Cache() {
        SqlSession sqlSession = getSqlSession();
        SysUser user1  = null;

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            user1 = userMapper.selectById(1L);
            user1.setUserName("New Name");

            SysUser user2 = userMapper.selectById(1L);

            Assert.assertEquals("New Name", user2.getUserName());

            Assert.assertEquals(user1, user2);
        } finally {
            sqlSession.close();
        }
        System.out.println("open new sqlSession");
        sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user2 = userMapper.selectById(1L);
            Assert.assertNotEquals("New Name", user2.getUserName());
            Assert.assertNotEquals(user1, user2);

            userMapper.deleteById(2L);

            SysUser user3 = userMapper.selectById(1L);
            Assert.assertNotEquals(user2, user3);
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testL2Cache() {
        SqlSession sqlSession = getSqlSession();
        SysRole role1 = null;

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            role1 = roleMapper.selectById(1L);
            role1.setRoleName("New Name");

            SysRole role2 = roleMapper.selectById(1L);
            Assert.assertEquals("New Name", role2.getRoleName());
            Assert.assertEquals(role1, role2);
        } finally {
            sqlSession.close();
        }

        System.out.println("open new sqlSession");

        sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role2 = roleMapper.selectById(1L);

            Assert.assertEquals("New Name", role2.getRoleName());
            Assert.assertNotEquals(role1, role2);

            SysRole role3 = roleMapper.selectById(1L);
            Assert.assertEquals("New Name", role3.getRoleName());
            Assert.assertNotEquals(role2, role3);
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testDirtyData() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            Assert.assertEquals("普通用户", user.getSysRole().getRoleName());
            System.out.println("角色名: " + user.getSysRole().getRoleName());
        } finally {
            sqlSession.close();
        }

        System.out.println("open new sqlSession");
        sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(2L);
            role.setRoleName("脏数据");
            roleMapper.updateById(role);

            sqlSession.commit();
        } finally {
            sqlSession.close();
        }

        System.out.println("open new sqlSession");
        sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            SysRole role = roleMapper.selectById(2L);
            Assert.assertNotEquals("普通用户", user.getSysRole().getRoleName());
            Assert.assertEquals("脏数据", role.getRoleName());
            System.out.println("角色名: " + user.getSysRole().getRoleName());

            role.setRoleName("普通用户");
            roleMapper.updateById(role);

            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
