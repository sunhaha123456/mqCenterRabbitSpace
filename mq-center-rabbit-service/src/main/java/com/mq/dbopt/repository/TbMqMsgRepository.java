package com.mq.dbopt.repository;

import com.mq.data.entity.TbMqMsg;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TbMqMsgRepository extends CrudRepository<TbMqMsg, Long> {
    @Modifying
    @Query(value = "update tb_mq_msg set status = 1, total_push_count = total_push_count + 1 where id = :id and status = 1", nativeQuery = true)
    int updateForFailPush(@Param("id")Long id);

    @Modifying
    @Query(value = "update tb_mq_msg set status = 2, total_push_count = total_push_count + 1 where id = :id and status = 1", nativeQuery = true)
    int updateForSuccessPush(@Param("id")Long id);

}