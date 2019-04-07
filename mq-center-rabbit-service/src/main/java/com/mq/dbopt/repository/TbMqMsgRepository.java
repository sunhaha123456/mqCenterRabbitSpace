package com.mq.dbopt.repository;

import com.mq.data.entity.TbMqMsg;
import com.mq.data.entity.TbUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TbMqMsgRepository extends CrudRepository<TbMqMsg, Long> {
    @Modifying
    @Query(value = "update tb_mq_msg set status = :newStatus, total_push_count = total_push_count + 1 where status = :oldStatus", nativeQuery = true)
    int updateForFailPush(@Param("newStatus") Integer newStatus, @Param("oldStatus") String oldStatus);

    @Modifying
    @Query(value = "update tb_mq_msg set status = :newStatus, total_push_count = total_push_count + 1 where status = :oldStatus", nativeQuery = true)
    int updateForSuccessPush(@Param("uname") String uname, @Param("upwd") String upwd);

}