package com.wook.chap.domain.repository;

import com.wook.chap.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query("select u from Url u where u.shortURL=:shortsUrl")
    Optional<Url> findByShortsUrl(@Param("shortsUrl")String shortsUrl);

}
