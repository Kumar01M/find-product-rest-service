package com.kumar.find_product.repository.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.kumar.find_product.dto.GetProductsDTO;
import com.kumar.find_product.model.Product;
import com.kumar.find_product.repository.ProductRepository;

@Repository
public class ProductRepoImpl implements ProductRepository {

    private JdbcTemplate jdbcTemplate;

    public ProductRepoImpl(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addProduct(Product product) throws DataAccessException {
        String insertQuery = "INSERT INTO product(`shopID`, `categoryID`, `productName`, " +
            "`price`, `quantity`, `unit`) VALUES(?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(insertQuery, product.getShopID(), product.getCategoryID(), product.getProductName(), 
        product.getPrice(), product.getQuantity(), product.getUnit());
        return result == 1;
    }

    @Override
    public boolean updateProduct(Product product) throws DataAccessException {
        String updateQuery = "UPDATE product SET `productName` = ?, `price` = ? ," +
        "`quantity` = ?, `unit` = ?, `categoryID` = ? WHERE `shopID` = ? AND `deletedFlag` = 'N";
        int result = jdbcTemplate.update(updateQuery, product.getProductName(), product.getPrice(), 
        product.getQuantity(), product.getUnit(), product.getCategoryID(), 
        product.getShopID());
        return result == 1;
    }

    @Override
    public GetProductsDTO getProducts(Object... argObjects) throws DataAccessException, Exception {
        String sql = buildGetQuery(argObjects);
        argObjects = getNonNullValues(argObjects);
        List<GetProductsDTO.InnerProductDTO> productsList = new ArrayList<>();
        int totalCount = 0;
        Set<String> categories = new HashSet<>();
        
        productsList = jdbcTemplate.query(sql, 
            new RowMapper<GetProductsDTO.InnerProductDTO>() {
                @Override
                @Nullable
                public GetProductsDTO.InnerProductDTO mapRow(ResultSet arg0, int arg1) throws SQLException {
                    Product product = new Product();
                    product.setProductID(arg0.getInt("productID"));
                    product.setCategoryID(arg0.getInt("categoryID"));
                    product.setShopID(arg0.getInt("shopID"));
                    product.setProductName(arg0.getString("productName"));
                    product.setPrice(arg0.getBigDecimal("price"));
                    product.setQuantity(arg0.getBigDecimal("quantity"));
                    product.setUnit(arg0.getString("unit"));

                    GetProductsDTO.InnerProductDTO object = new GetProductsDTO.InnerProductDTO();
                    object.setProduct(product);
                    object.setCategoryName(arg0.getString("categoryName"));
                    object.setCategoryTags(arg0.getString("categoryTags"));
                    object.setShopName(arg0.getString("shopName"));
                    object.setShopAddress(arg0.getString("shopAddress"));
                    object.setTotalCount(arg0.getInt("totalCount"));
                    // categories.add(arg0.getString("categoryName"));
                    // suspecting error due to `categories` field (it is local used inside anonymous inner class)                    
                    return object;
                }
            }
            , argObjects);

            int filteredCount = productsList.size();
            if (filteredCount > 0) {
                totalCount = productsList.get(0).getTotalCount();
            }
            for (int index = 0; index < filteredCount; index++) {
                categories.add(productsList.get(index).getCategoryName());
            }
        return new GetProductsDTO(productsList, filteredCount, totalCount, categories);
    }

    //passing values for query
    private Object[] getNonNullValues(Object[] argObjects) {
        List<Object> list = new ArrayList<>();
        for (int index = 4; index < argObjects.length; index++) {// categoryName, shopID, categoryID
            if (argObjects[index] != null) {
                list.add(argObjects[index]);
            }
        }
        if (argObjects[3] != null) {//search
            int keys = 5;
            for (int i = 1; i <= keys; i++) {
                list.add("%" + argObjects[3] + "%");
            }
        }
        list.add(argObjects[2]);//sort
        list.add(argObjects[0]);//start
        list.add(argObjects[1]);//lenth
        return list.toArray();
    }

    private String buildGetQuery(Object[] argObjects) {
        String search = (argObjects[3] == null) ? "" : " AND (productName LIKE ? OR categoryName LIKE ? "
        + " OR shopName LIKE ? OR categoryTags LIKE ? OR shopAddress LIKE ? ) ";
        String categoryName = (argObjects[4] == null) ? "" : " AND categoryName = ? ";
        String shopID = (argObjects[5] == null) ? "" : " AND product.shopID = ? ";
        String categoryID = (argObjects[6] == null) ? "" : " AND product.categoryID = ? ";

        String selectQuery = "select * from ("
        + " SELECT "
        + "    product.productID,"
        + "    product.shopID,"
        + "    product.categoryID,"
        + "    productName,"
        + "    price,"
        + "    quantity,"
        + "    unit,"
        + "    shopName,"
        + "    shopAddress,"
        + "    categoryName,"
        + "    categoryTags,"
        + "    count(1) over() as totalCount"
        + " FROM"
        + "    product"
        + "        JOIN"
        + "    shop USING (shopID)"
        + "        JOIN"
        + "    category USING (categoryID)"
        + " WHERE"
        + "    product.deletedFlag = 'N'"
        + categoryName
        + shopID
        + categoryID
        + search
        + " ) sub "
        + " ORDER BY ?"
        + " LIMIT ?, ?";
        return selectQuery;
    }

    public String getOrderByColumns(Object text) {
        return null; //TODO
    }

}
