package com.bacchoterra.memoriav3.repo

import com.bacchoterra.memoriav3.dao.CategoryDao
import com.bacchoterra.memoriav3.model.Category

class CategoryRepo(private val mDao:CategoryDao) {


    val getAllCats = this.mDao.getAll()


    suspend fun insert(category:Category){
        mDao.insert(category)
    }

    suspend fun delete(category:Category){
        mDao.delete(category)
    }

    suspend fun update(category:Category){
        mDao.update(category)
    }

    suspend fun deleteAll(){
        mDao.deleteAll()
    }




}