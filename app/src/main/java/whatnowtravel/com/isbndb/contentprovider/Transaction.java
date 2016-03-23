package whatnowtravel.com.isbndb.contentprovider;

import whatnowtravel.com.isbndb.IBuilder;

/**
 * Created by annadel.prete on 05/03/2016.
 */
public class Transaction {
    private int status;
    private long id;
    private String summary;
    private String word;

    private Transaction(){

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    public static class TransationBuilder implements IBuilder<Transaction> {
        private int status;
        private long id;
        private String summary;
        private String word;


        public TransationBuilder(){
            this.status = 0;
            this.id = -1;
            this.summary = "";
            this.word = "";
        }

        @Override
        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.setStatus(status);
            transaction.setId(id);
            transaction.setWord(word);
            transaction.setSummary(summary);
            return transaction;
        }

        public TransationBuilder status(int status){
            this.status = status;
            return this;
        }
        public TransationBuilder id(long status){
            this.id = id;
            return this;
        }
        public TransationBuilder summary(String summary){
            this.summary = summary;
            return this;
        }
        public TransationBuilder word(String word){
            this.word = word;
            return this;
        }

    }
}
