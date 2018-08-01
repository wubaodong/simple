package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;

import java.util.List;

public class PrivilegeMapperTest extends BaseMapperTest {

    @Ignore
    @Test
    public void testSelectById() {
        SqlSession sqlSession = getSqlSession();

        try {
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            SysPrivilege privilege = privilegeMapper.selectById(1L);

            Assert.assertNotNull(privilege);
            Assert.assertEquals("用户管理", privilege.getPrivilegeName());
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testSelectPrivilegeByRoleId() {
        SqlSession sqlSession = getSqlSession();

        try {
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            List<SysPrivilege> privilegeList = privilegeMapper.selectPrivilegeByRoleId(1L);

            Assert.assertNotNull(privilegeList);
            Assert.assertTrue(privilegeList.size() > 0);
            for (SysPrivilege privilege :
                    privilegeList) {
                System.out.println("privilege name: " + privilege.getPrivilegeName());
            }
        } finally {
            sqlSession.close();
        }
    }
}
