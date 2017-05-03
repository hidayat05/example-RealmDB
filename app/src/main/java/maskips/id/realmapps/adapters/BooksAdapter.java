package maskips.id.realmapps.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmResults;
import maskips.id.realmapps.R;
import maskips.id.realmapps.app.Prefs;
import maskips.id.realmapps.model.Book;
import maskips.id.realmapps.realm.RealmController;

/**
 * @author nurhidayat
 * @since 5/3/17.
 */

public class BooksAdapter extends RealmRecyclerViewAdapter<Book> {

    private final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public BooksAdapter(Context context) {
        this.context = context;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        realm = RealmController.getInstance().getRealm();

        final Book book = getItem(position);

        final CardViewHolder viewHolder = (CardViewHolder) holder;

        viewHolder.textTitle.setText(book.getTitle());
        viewHolder.textAuthor.setText(book.getAuthor());
        viewHolder.textDescription.setText(book.getDescription());

        if (book.getImageUrl() != null) {
            Glide.with(context)
                    .load(book.getImageUrl().replace("https", "http"))
                    .asBitmap()
                    .fitCenter()
                    .into(viewHolder.imageBackground);
        }

        viewHolder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                RealmResults<Book> realmResults = realm.where(Book.class).findAll();
                realm.beginTransaction();
                String title = realmResults.get(position).getTitle();
                realmResults.remove(position);
                realm.commitTransaction();

                if (realmResults.size() == 0) {
                    Prefs.with(context).setPreLoad(false);
                }
                notifyDataSetChanged();
                Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_item, null);
                final EditText editTitle = (EditText) content.findViewById(R.id.title);
                final EditText editAuthor = (EditText) content.findViewById(R.id.author);
                final EditText editThumbnail = (EditText) content.findViewById(R.id.thumbnail);

                editTitle.setText(book.getTitle());
                editAuthor.setText(book.getAuthor());
                editThumbnail.setText(book.getImageUrl());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Edit Book")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RealmResults<Book> results = realm.where(Book.class).findAll();

                                realm.beginTransaction();
                                results.get(position).setAuthor(editAuthor.getText().toString());
                                results.get(position).setTitle(editTitle.getText().toString());
                                results.get(position).setImageUrl(editThumbnail.getText().toString());

                                realm.commitTransaction();

                                notifyDataSetChanged();
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        if (getRealmBaseAdapter() != null) {
            return getRealmBaseAdapter().getCount();
        }
        return 0;
    }


    private class CardViewHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView textTitle;
        TextView textAuthor;
        TextView textDescription;
        ImageView imageBackground;

        CardViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_books);
            textTitle = (TextView) itemView.findViewById(R.id.text_books_title);
            textAuthor = (TextView) itemView.findViewById(R.id.text_books_author);
            textDescription = (TextView) itemView.findViewById(R.id.text_books_description);
            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
        }
    }
}
