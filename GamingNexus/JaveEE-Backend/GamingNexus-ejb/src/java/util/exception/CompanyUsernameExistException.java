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
public class CompanyUsernameExistException extends Exception {

    /**
     * Creates a new instance of <code>CompanyUsernameExistException</code>
     * without detail message.
     */
    public CompanyUsernameExistException() {
    }

    /**
     * Constructs an instance of <code>CompanyUsernameExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CompanyUsernameExistException(String msg) {
        super(msg);
    }
}
