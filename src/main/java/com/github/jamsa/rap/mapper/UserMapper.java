package com.github.jamsa.rap.mapper;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User, Integer> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_USER
     *
     * @mbggenerated Thu Aug 25 14:35:40 CST 2016
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_USER
     *
     * @mbggenerated Thu Aug 25 14:35:40 CST 2016
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_USER
     *
     * @mbggenerated Thu Aug 25 14:35:40 CST 2016
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_USER
     *
     * @mbggenerated Thu Aug 25 14:35:40 CST 2016
     */
    User selectByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_USER
     *
     * @mbggenerated Thu Aug 25 14:35:40 CST 2016
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_USER
     *
     * @mbggenerated Thu Aug 25 14:35:40 CST 2016
     */
    int updateByPrimaryKey(User record);

    /** @mbggenerated */
    List<User> selectByCondition(User condition);
}