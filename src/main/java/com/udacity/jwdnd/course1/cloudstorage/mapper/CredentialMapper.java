package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialsForUser(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int create(Credential credential);

    @Update("Update CREDENTIALS set url=#{url}, " +
            " username=#{username}, key=#{key}, password=#{password} where credentialId=#{credentialId}")
    int update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS where credentialId = #{credentialId}")
    int delete(@Param("credentialId") Integer credentialId);


}