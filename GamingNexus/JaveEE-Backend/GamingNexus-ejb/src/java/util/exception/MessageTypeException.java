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
public class MessageTypeException extends Exception {

    /**
     * Creates a new instance of <code>MessageTypeException</code> without
     * detail message.
     */
    public MessageTypeException() {
    }

    /**
     * Constructs an instance of <code>MessageTypeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MessageTypeException(String msg) {
        super(msg);
    }
}
