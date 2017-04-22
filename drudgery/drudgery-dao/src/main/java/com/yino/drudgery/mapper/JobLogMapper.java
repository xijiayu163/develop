package com.yino.drudgery.mapper;

import com.yino.drudgery.po.JobLog;
import com.yino.drudgery.po.JobLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JobLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    int countByExample(JobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    int deleteByExample(JobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    int deleteByPrimaryKey(String jobLogUid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    int insert(JobLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    int insertSelective(JobLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    List<JobLog> selectByExample(JobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    JobLog selectByPrimaryKey(String jobLogUid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    int updateByExampleSelective(@Param("record") JobLog record, @Param("example") JobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    int updateByExample(@Param("record") JobLog record, @Param("example") JobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    int updateByPrimaryKeySelective(JobLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_log
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    int updateByPrimaryKey(JobLog record);
}