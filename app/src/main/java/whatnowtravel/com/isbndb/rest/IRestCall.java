package whatnowtravel.com.isbndb.rest;

import java.util.List;

import retrofit2.Callback;


public interface IRestCall<B> {

    public void onSuccess(List<B> response);

    public void onFailure(String message);
}
