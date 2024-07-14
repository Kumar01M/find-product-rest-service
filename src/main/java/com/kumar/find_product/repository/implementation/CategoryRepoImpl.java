package com.kumar.find_product.repository.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kumar.find_product.model.Category;
import com.kumar.find_product.repository.CategoryRepository;

@Repository
public class CategoryRepoImpl implements CategoryRepository {

    private JdbcTemplate jdbcTemplate;

    public CategoryRepoImpl(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> getCategories(Integer shopID) throws DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategories'");
    }

    @Override
    public boolean addCategory(Category category) throws DataAccessException {
        String insertQuery = "INSERT INTO category(`shopID`, `categoryName`, `categoryTags`) VALUES(?, ?, ?)";
        int result = jdbcTemplate.update(insertQuery, category.getShopID(), category.getCategoryName(), 
            category.getCategoryTags());
        return result == 1;
    }

    @Override
    public boolean updateCategory(Category category) throws DataAccessException {
        String updateQuery = "UPDATE category SET `categoryName` = ?, `categoryTags` = ? WHERE " +
            "`categoryID` = ? AND `shopID` = ? AND `deletedFlag` = 'N'";
        int result = jdbcTemplate.update(updateQuery, category.getCategoryName(), category.getCategoryTags(), 
            category.getCategoryID(), category.getShopID());
        return result == 1;
    }

    @Override
    public boolean deleteCategory(Category category) throws DataAccessException {
        String deleteQuery = "UPDATE category SET `deletedFlag` = 'Y' WHERE " +
            "`categoryID` = ? AND `shopID` = ? AND `deletedFlag` = 'N'";
        int result = jdbcTemplate.update(deleteQuery, category.getCategoryID(), category.getShopID());
        return result == 1;
    }

}
