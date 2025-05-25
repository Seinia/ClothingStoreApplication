package com.fix.OnlineClothingStore.Repo;


import com.fix.OnlineClothingStore.Model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


    @Query("""
    select t from Token t inner join User u on t.user.id = u.id
    where t.user.id = :userId
    """)
    List<Token> findAllAccessTokensByUser(Integer userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Token t WHERE t.user.id = :userId")
    void deleteAllTokensByUser(Integer userId);

    Optional<Token> findByUserId(Integer userId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token > findByRefreshToken(String token);
}