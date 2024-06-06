package com.gjb.webwork.mapper;

import com.gjb.webwork.model.Author;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AuthorMapper {

    @Select("SELECT id, name, province, city, address, zip_code, user_id, created_at, updated_at " +
            "FROM spring_work.authors " +
            "WHERE user_id = #{user_id} " +
            "AND (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL) " +
            "LIMIT #{size} OFFSET #{offset}")
    List<Author> getAuthors(@Param("user_id") Long user_id, @Param("name") String name,
                            @Param("size") int size, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM spring_work.authors " +
            "WHERE user_id = #{user_id} " +
            "AND (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL)")
    int getAuthorCount(@Param("user_id") Long user_id, @Param("name") String name);

    @Select("SELECT id FROM spring_work.users WHERE username = #{username}")
    Long getUserIdByUsername(@Param("username") String username);

    @Insert("INSERT INTO spring_work.authors (user_id, created_at, updated_at, name, province, city, address, zip_code) " +
            "VALUES (#{user_id}, #{created_at}, #{updated_at}, #{name}, #{province}, #{city}, #{address}, #{zip_code})")
    void insertAuthor(Author author);

    /**
     * 根据ID查询作者信息
     * @param id 作者ID
     * @return 匹配的Author对象，若无匹配则返回null
     */
    @Select("SELECT id, user_id, created_at, updated_at, name, province, city, address, zip_code " +
            "FROM spring_work.authors " +
            "WHERE id = #{id}")
    Author getAuthorById(@Param("id") int id);

    @Update("UPDATE spring_work.authors SET name = #{name}, " +
            "province = #{province}, city = #{city}, " +
            "address = #{address}, zip_code = #{zip_code}, created_at = #{created_at}, " +
            "updated_at = #{updated_at} WHERE id = #{id}")
    void updateAuthor(Author author);

    @Delete("DELETE FROM spring_work.authors WHERE id = #{id}")
    void deleteAuthor(@Param("id") int id);
}
