package util.exception;


/**
 *
 * @author Yang Xi
 */
public class SaleTransactionAlreadyVoidedRefundedException extends Exception
{
    public SaleTransactionAlreadyVoidedRefundedException()
    {
    }
    
    
    
    public SaleTransactionAlreadyVoidedRefundedException(String msg)
    {
        super(msg);
    }
}