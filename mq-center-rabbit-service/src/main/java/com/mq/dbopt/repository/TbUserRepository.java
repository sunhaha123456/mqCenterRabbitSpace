package com.mq.dbopt.repository;

import com.mq.data.entity.TbUser;
import org.springframework.data.repository.CrudRepository;

public interface TbUserRepository extends CrudRepository<TbUser, Long> {
//    @Query(value = "select count(1) from tb_menu where menu_name = :menuName and dir_level = :dirLevel and parent_id = :parentId", nativeQuery = true)
//    long countByMenuName(@Param(value = "menuName") String menuName, @Param("dirLevel") Integer dirLevel, @Param("parentId") Long parentId);
//
//    @Query(value = "select count(1) from tb_menu where menu_name = :menuName and id != :id and dir_level = :dirLevel and parent_id = :parentId", nativeQuery = true)
//    long countByMenuName(@Param("menuName") String menuName, @Param("id") Long id, @Param("dirLevel") Integer dirLevel, @Param("parentId") Long parentId);
//
//    @Query(value = "select * from tb_menu where dir_level = :dirLevel", nativeQuery = true)
//    List<TbMenu> listByDirLevel(@Param("dirLevel") Integer dirLevel);
}