package com.employees.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employees.dao.ContactDao;
import com.employees.model.Contact;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/contacts")
public class ContactController {

  private final ContactDao contactDao;

  public ContactController(ContactDao contactDao) {
    this.contactDao = contactDao;
  }

  /**
   * This function returns a list of all contacts in the database
   *
   * @return A list of contacts
   */
  @GetMapping
  public ResponseEntity<List<Contact>> findAll() throws SQLException {
    return ResponseEntity.ok(contactDao.findAll());
  }

  /**
   * The function takes a JSON object, converts it to a Contact object, saves it to the database,
   * and returns the newly created Contact object
   *
   * @param contact This is the object that will be created.
   * @return The response entity is being returned.
   */
  @PostMapping
  public ResponseEntity<Contact> create(@RequestBody Contact contact)
      throws URISyntaxException, SQLException {
    contactDao.save(contact);
    return ResponseEntity.created(new URI("/api/v1/contacts"))
        .body(contactDao.findContactSortByIdDesc());
  }

  /**
   * This function will return a contact with the given id
   *
   * @param id the id of the contact you want to find
   * @return A ResponseEntity object with a status code of 200 and a body containing the Contact
   *     object with the specified id.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Contact> findOne(@PathVariable int id) throws SQLException {
    return ResponseEntity.ok(contactDao.findOne(id));
  }

  /**
   * It updates the contact with the given id.
   *
   * @param id The id of the contact to update
   * @param contact The object that will be updated.
   * @return A ResponseEntity object is being returned.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Contact> update(@PathVariable int id, @RequestBody Contact contact)
      throws SQLException {
    contactDao.update(contact);
    return ResponseEntity.ok(contactDao.findOne(id));
  }

  /**
   * It deletes a contact from the database and returns a message to the user
   *
   * @param id The id of the contact to be deleted.
   * @return A ResponseEntity object is being returned.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> remove(@PathVariable int id) throws SQLException {
    contactDao.delete(id);
    return new ResponseEntity<>(Map.of("message", "Contact deleted successfully."), HttpStatus.OK);
  }
}
