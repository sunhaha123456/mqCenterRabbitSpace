package com.mq.dbopt.repository;

import com.mq.data.entity.TbUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TbUserRepository extends CrudRepository<TbUser, Long> {
    @Query(value = "select * from tb_user where uname = :uname and upwd = :upwd", nativeQuery = true)
    List<TbUser> listByUnameAndPwd(@Param("uname") String uname, @Param("upwd") String upwd);
}