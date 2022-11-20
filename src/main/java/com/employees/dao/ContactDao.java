package com.employees.dao;

import java.sql.SQLException;
import java.util.List;

import com.employees.model.Contact;

public interface ContactDao {

  void save(Contact contact) throws SQLException;

  void update(Contact contact) throws SQLException;

  void delete(int id) throws SQLException;

  List<Contact> findAll() throws SQLException;

  Contact findOne(int id) throws SQLException;

  Contact findContactSortByIdDesc() throws  SQLException;
}
