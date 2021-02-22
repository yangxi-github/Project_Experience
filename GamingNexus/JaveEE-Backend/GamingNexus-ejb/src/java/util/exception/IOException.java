/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author chenli
 */
public class IOException extends Exception {

    /**
     * Creates a new instance of <code>IOException</code> without detail
     * message.
     */
    public IOException() {
    }

    /**
     * Constructs an instance of <code>IOException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public IOException(String msg) {
        super(msg);
    }
}
