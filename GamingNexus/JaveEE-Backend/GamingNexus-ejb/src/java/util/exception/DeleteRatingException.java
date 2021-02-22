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
public class DeleteRatingException extends Exception {

    /**
     * Creates a new instance of <code>DeleteRatingException</code> without
     * detail message.
     */
    public DeleteRatingException() {
    }

    /**
     * Constructs an instance of <code>DeleteRatingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteRatingException(String msg) {
        super(msg);
    }
}
