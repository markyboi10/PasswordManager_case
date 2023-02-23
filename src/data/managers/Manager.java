package data.managers;

import java.io.File;
import java.io.IOException;

/**
 * Interface for the vault manager
 */
public interface Manager {

    /**
     * Gets the JSON of this manager
     *
     * @return string JSON of this file formatted
     */
    public abstract String getJSON();

    /**
     * Loads JSON from a {@code file} into this manager
     *
     * @param file file to be loaded from
     */
    public abstract void loadJSON(File file);

    /**
     * Loader method for this.Manager
     * @throws java.io.IOException
     */
    public abstract void init() throws IOException;

    /**
     * Updates a file with formatted JSON from an object
     *
     * @param file
     */
    public abstract void sendJSON(File file);

} // End interface 'Manager'
