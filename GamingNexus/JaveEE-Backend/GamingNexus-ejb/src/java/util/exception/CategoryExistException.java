/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author 63216
 */
public class CategoryExistException extends Exception {

    /**
     * Creates a new instance of <code>CategoryExistException</code> without
     * detail message.
     */
    public CategoryExistException() {
    }

    /**
     * Constructs an instance of <code>CategoryExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CategoryExistException(String msg) {
        super(msg);
    }
}
