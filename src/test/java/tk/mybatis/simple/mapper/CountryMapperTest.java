package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import tk.mybatis.simple.model.Country;
import tk.mybatis.simple.model.CountryExample;

import java.util.List;

public class CountryMapperTest extends BaseMapperTest {

    @Ignore
    @Test
    public void testSelectAll() {
        SqlSession sqlSession = getSqlSession();
        try {
            List<Country> countryList = sqlSession.selectList("tk.mybatis.simple.mapper.CountryMapper.selectAll");
            printCountryList(countryList);
        } finally {
            sqlSession.close();
        }
    }

    private void printCountryList(List<Country> countryList) {
        for (Country country :
                countryList) {
            System.out.printf("%-4d%4s%4s\n", country.getId(), country.getCountryname(), country.getCountrycode());
        }
    }

    @Ignore
    @Test
    public void testExample() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);

            CountryExample example = new CountryExample();

            example.setOrderByClause("id desc, countryname asc");
            example.setDistinct(true);

            CountryExample.Criteria criteria = example.createCriteria();
            criteria.andIdGreaterThanOrEqualTo(1);
            criteria.andIdLessThan(4);
            criteria.andCountrycodeLike("%U%");

            CountryExample.Criteria or = example.or();
            or.andCountrynameEqualTo("中国");
            List<Country> countryList = countryMapper.selectByExample(example);
            printCountryList(countryList);
        } finally {
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testUpdateByExampleSelective() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);

            CountryExample example = new CountryExample();

            CountryExample.Criteria criteria = example.createCriteria();
            criteria.andIdGreaterThan(2);
            Country country = new Country();
            country.setCountryname("China");

            countryMapper.updateByExampleSelective(country, example);
            printCountryList(countryMapper.selectByExample(example));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Ignore
    @Test
    public void testDeleteByExample() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);

            CountryExample example = new CountryExample();
            CountryExample.Criteria criteria = example.createCriteria();
            criteria.andIdGreaterThan(2);

            countryMapper.deleteByExample(example);

            Assert.assertEquals(0, countryMapper.countByExample(example));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}
