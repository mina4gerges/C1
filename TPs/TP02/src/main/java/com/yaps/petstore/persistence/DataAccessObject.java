package com.yaps.petstore.persistence;

import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.DataAccessException;
import com.yaps.petstore.exception.ObjectNotFoundException;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class uses a HashTable to store objects in it and serializes the hashmap.
 */
public class DataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private Map _hashmap = new HashMap();
    private String _hashmapFileName;

    // ======================================
    // =            Constructors            =
    // ======================================
    /**
     * @param hashmapFileName name of the file saved on disk
     * @throws DataAccessException     is thrown if there's a persistent problem
     */
    public DataAccessObject(final String hashmapFileName) {
    	_hashmapFileName = hashmapFileName;
        // Loads the Hastable
        loadHastable(_hashmapFileName);
    }
    
    /**
     * @throws DataAccessException     is thrown if there's a persistent problem
     */
    public void finalize() {
    	saveHastableOnDisk(_hashmapFileName);
    }
    
    /**
     * This method gets an available id.
     *
     * @return int an available Id (not used already by any domain object)
     */
    public int getUniqueId() {
    	String s = null;
    	int result = 0;
    	do {
    		result = (int) (Math.random() * 100000);
    		s = Integer.toString(result);
    		try {
				select(s);
			} catch (ObjectNotFoundException e) {
				return result;
			}
    	} while ( true );
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method gets an object from the HashMap.
     *
     * @param id                Object identifier to be found in the hastable
     * @return Object the object with all its attributs set
     * @throws ObjectNotFoundException is thrown if the object id not found in the hastable
     */
    public Object select(final String id) throws ObjectNotFoundException {

        // If the given id doesn't exist we throw a ObjectNotFoundException
        if (!_hashmap.containsKey(id)) {
            throw new ObjectNotFoundException();
        }

        return _hashmap.get(id);
    }

    /**
     * This method return all the objects that are in the serialized hashmap.
     *
     * @return collection of objects
     * @throws ObjectNotFoundException is thrown if the collection is empty
     */
    public Collection selectAll() throws ObjectNotFoundException {

        // If the hastable is empty we throw a DataNotFoundException
        if (_hashmap.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        return _hashmap.values();
    }

    /**
     * This method inserts an object into a HashMap and serializes the Hastable on disk.
     *
     * @param object            Object to be inserted into the hastable
     * @param id                identifier of the object to be inserted
     * @throws DuplicateKeyException is thrown when an identical object is already in the hastable
     */
    public void insert(final Object object, final String id) throws DuplicateKeyException {

        // If the given id already exists we cannot insert the new object
        if (_hashmap.containsKey(id)) {
            throw new DuplicateKeyException();
        }

        // Puts the object into the hastable
        _hashmap.put(id, object);
    }

    /**
     * This method updates an object of the HashMap and serializes the Hastable on disk.
     *
     * @param object            Object to be updated from the hastable
     * @param id                identifier of the object to be updated
     * @throws ObjectNotFoundException is thrown if the object id not found in the hastable
     * @throws DuplicateKeyException is thrown when an identical object is already in the hastable
     */
    public void update(final Object object, final String id) throws ObjectNotFoundException, DuplicateKeyException {
        remove(id);
        insert(object, id);
    }

    /**
     * This method deletes an object from the HashMap and serializes the Hastable on disk.
     *
     * @param id                object identifier to be deleted from the hastable
     * @throws ObjectNotFoundException is thrown if the object id not found in the hastable
     */
    public void remove(final String id) throws ObjectNotFoundException {

        // If the given id does'nt exist we cannot remove the object from the hashmap
        if (!_hashmap.containsKey(id)) {
            throw new ObjectNotFoundException();
        }

        // The object is removed from the hastable
        _hashmap.remove(id);
    }

    /**
     * This method checks that the identifier is valid.
     *
     * @param id identifier to check
     * @throws CheckException if the id is invalid
     */
    void checkId(final String id) throws CheckException {
        if (id == null || "".equals(id))
            throw new CheckException("Invalid id");
    }
    // ======================================
    // =            Private methods         =
    // ======================================
    // This method loads the hastable in the file saved on disk
    private void loadHastable(final String hashmapFileName) throws DataAccessException {

        try {
            final FileInputStream fin = new FileInputStream(hashmapFileName);
            final ObjectInputStream in = new ObjectInputStream(fin);

            // Reads the values from the file
            _hashmap = (HashMap) in.readObject();

            in.close();
        } catch (FileNotFoundException e) {
            // The first call to this method, the file doesn't exist so nothing is done
        } catch (Exception e) {
            throw new DataAccessException("Cannot load " + hashmapFileName + " !!!", e);
        }
    }

    // This method saves the hastable on disk
    private void saveHastableOnDisk(final String hashmapFileName) throws DataAccessException {

        try {
            final FileOutputStream fout = new FileOutputStream(hashmapFileName);
            final ObjectOutputStream out = new ObjectOutputStream(fout);

            // Saves the hashmap to disk
            out.writeObject(_hashmap);

            out.close();
        } catch (IOException e) {
            throw new DataAccessException("Cannot save " + hashmapFileName + " !!!", e);
        }
    }
}
