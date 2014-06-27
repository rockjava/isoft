package com.isoftframework.dao.orm.jpa.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RepositorySupport<T, ID extends Serializable> extends JpaRepository<T, ID>
{
     
}
