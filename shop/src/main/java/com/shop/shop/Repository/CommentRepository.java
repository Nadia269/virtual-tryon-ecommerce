package com.shop.shop.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.shop.Model.CommentModel;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {}
