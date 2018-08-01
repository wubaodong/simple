package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.plugin.PageRowBounds;

import java.util.Date;
import java.util.List;

public class RoleMapperTest extends BaseMapperTest {

    @Ignore
    @Test
    public void testSelectById() {
        SqlSession sqlSession = getSqlSession();

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);

            Assert.assertNotNull(role);
            Assert.assertEquals("管理员", role.getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectById2() {
        SqlSession sqlSession = getSqlSession();

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById2(1L);

            Assert.assertNotNull(role);
            Assert.assertEquals("管理员", role.getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectAll() {
        SqlSession sqlSession = getSqlSession();

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAll();

            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
            Assert.assertNotNull(roleList.get(0).getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testInsert() {
        SqlSession sqlSession = getSqlSession();

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("普通用户");
            sysRole.setEnabled(1);
            sysRole.setCreateBy(1L);
            sysRole.setCreateTime(new Date());

            int result = roleMapper.insert(sysRole);

            Assert.assertEquals(1, result);
            Assert.assertNull(sysRole.getId());
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
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("普通用户");
            sysRole.setEnabled(1);
            sysRole.setCreateBy(1L);
            sysRole.setCreateTime(new Date());

            int result = roleMapper.insert2(sysRole);

            Assert.assertEquals(1, result);
            Assert.assertNotNull(sysRole.getId());
            System.out.println(sysRole.getId());
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
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("普通用户");
            sysRole.setEnabled(1);
            sysRole.setCreateBy(1L);
            sysRole.setCreateTime(new Date());

            int result = roleMapper.insert3(sysRole);

            Assert.assertEquals(1, result);
            Assert.assertNotNull(sysRole.getId());
            System.out.println(sysRole.getId());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testUpdateById() {
        SqlSession sqlSession = getSqlSession();

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            role.setEnabled(0);
            int result = roleMapper.updateById(role);

            Assert.assertEquals(1, result);

            role = roleMapper.selectById(1L);
            Assert.assertEquals(0, (int) role.getEnabled());
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
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            Assert.assertNotNull(role);
            Assert.assertEquals(1, roleMapper.deleteById(1L));

            role = roleMapper.selectById(1L);
            Assert.assertNull(role);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectAllRoleAndPrivileges() {
        SqlSession sqlSession = getSqlSession();

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAllRoleAndPrivileges();
            System.out.println(roleList.size());
            for (SysRole role:
                 roleList) {
                System.out.println("role name: " + role.getRoleName());
                for (SysPrivilege privilege : role.getPrivilegeList()) {
                    System.out.println("\tprivilege name: " + privilege.getPrivilegeName());
                }
            }
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectRoleByUserId() {
        SqlSession sqlSession = getSqlSession();

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectRoleByUserId(1L);
            System.out.println(roleList.size());
            for (SysRole role:
                    roleList) {
                System.out.println("role name: " + role.getRoleName());
                for (SysPrivilege privilege : role.getPrivilegeList()) {
                    System.out.println("\tprivilege name: " + privilege.getPrivilegeName());
                }
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllByRowBounds() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);

            RowBounds rowBounds = new RowBounds(0, 1);
            List<SysRole> list = roleMapper.selectAll(rowBounds);
            for (SysRole role :
                    list) {
                System.out.println("Role name: " + role.getRoleName());
            }

            PageRowBounds pageRowBounds = new PageRowBounds(0, 1);
            list = roleMapper.selectAll(pageRowBounds);
            System.out.println("Total count: " + pageRowBounds.getTotal());
            for (SysRole role :
                    list) {
                System.out.println("Role name: " + role.getRoleName());
            }

            pageRowBounds = new PageRowBounds(1, 1);
            list = roleMapper.selectAll(pageRowBounds);
            System.out.println("Total count: " + pageRowBounds.getTotal());
            for (SysRole role :
                    list) {
                System.out.println("Role name: " + role.getRoleName());
            }
        } finally {
            sqlSession.close();
        }
    }
}
