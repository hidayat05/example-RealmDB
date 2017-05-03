package maskips.id.realmapps.adapters;

import android.content.Context;

import io.realm.RealmResults;
import maskips.id.realmapps.model.Book;

/**
 * @author nurhidayat
 * @since 5/3/17.
 */

public class RealmBooksAdapter extends RealmModelAdapter<Book>{

    public RealmBooksAdapter(Context context, RealmResults<Book> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
