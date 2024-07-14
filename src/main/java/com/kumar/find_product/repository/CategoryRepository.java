package com.kumar.find_product.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.kumar.find_product.model.Category;

public interface CategoryRepository {
    public boolean addCategory(Category category) throws DataAccessException;
    public boolean updateCategory(Category category) throws DataAccessException;
    public boolean deleteCategory(Category category) throws DataAccessException;
    public List<Category> getCategories(Integer shopID) throws DataAccessException;
}
