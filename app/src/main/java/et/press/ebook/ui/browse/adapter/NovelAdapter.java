package et.press.ebook.ui.browse.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import et.press.book.R;
import et.press.book.databinding.ItemNovelBinding;
import et.press.ebook.models.Book;

import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.MyViewHolder> {
    private final List<Book> items;
    private final OnNovelItemClickListener listener;

    public NovelAdapter(List<Book> items, OnNovelItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public List<Book> getItems() {
        return items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNovelBinding binding = ItemNovelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book item = items.get(position);
        holder.binding.novelName.setText("Title:::"+item.bookName);

        Glide.with(holder.binding.novelCover.getContext())
                .load(item.coverUrl)
                .placeholder(R.drawable.ic_discord) // Set your placeholder image resource
                .error(R.drawable.ic_android) // Set your error image resource
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        e.logRootCauses("String");
                        Log.e("TAG", ">>>>>onLoadFailed: ", e);
                        return false; // Return false to allow the error placeholder to be displayed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                         return false;
                    }
                })
                .into(holder.binding.novelCover)
        ;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnNovelItemClickListener {
        void onNovelItemClick(Book item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemNovelBinding binding;

        public MyViewHolder(@NonNull ItemNovelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.novelCoverLayout.setOnClickListener(v ->
                    listener.onNovelItemClick(items.get(getAdapterPosition())));
        }
    }
}
