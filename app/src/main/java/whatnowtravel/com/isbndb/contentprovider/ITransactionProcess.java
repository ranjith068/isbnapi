package whatnowtravel.com.isbndb.contentprovider;

/**
 * Created by annadel.prete on 27/02/2016.
 */
public interface ITransactionProcess {
    public String startTransaction(String word);
    public void processTransaction(String uid);
    public void finishTransaction(String uid, String result);
    public Transaction getLast();
    public Transaction getAllToBeProcessed();
}
