/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.uwindsor.group.CARE.db;

import java.util.List;

public interface DBAdapter<T> {

    /**
     * 
     * @return the list of column name. The first name in the list should be considered as the primary key
     */
    public List<String> getPropertyNames();

    /**
     *
     * @param key: The value of the primary key.
     * @param data: The list of the rest of the attributes other than the primary key
     * @return: If the record is created successfully a reference to the object representing that entry should be returned
     * @throws Exception
     */
    public T create(Object key, Object... data) throws Exception;

    /**
     * 
     * @param key: The value of the primary key
     * @return A reference to the object implementing the entry corresponding to the key.
     * @throws Exception
     */
    public T read(Object key) throws Exception;

    /**
     *
     * @param primaryKey: The value of the primary key to the record that needs update
     * @param keyToChange: The attribute name that needs to be changed
     * @param newValue: The new value for the attribute
     * @return true if the update is successful, false otherwise.
     * @throws Exception
     */
    public boolean update(Object primaryKey, Object keyToChange, Object newValue) throws Exception;

    /**
     * 
     * @param key: The key value of the record to be deleted
     * @return true upon successful deletion, false otherwise
     * @throws Exception
     */
    public boolean delete(Object key) throws Exception;

    /**
     * Persists all updates and modification to the underlying database or file system.
     * @throws Exception
     */
    public void commit() throws Exception;

    /**
     *
     * @return total number of records under this adapter
     */
    public int size() ;


    /**
     * 
     * @return A List of all the records under this adapter.
     */
    public List<T> getAllRecords() ;
}
