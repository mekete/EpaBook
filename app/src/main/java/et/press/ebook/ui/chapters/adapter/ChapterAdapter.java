package et.press.ebook.ui.chapters.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import et.press.book.databinding.ItemChapterBinding;
import et.press.ebook.models.Chapter;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.MyViewHolder> {
    private final List<Chapter> items;
    private final OnChapterItemClickListener listener;

    public ChapterAdapter(List<Chapter> items, OnChapterItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChapterBinding binding = ItemChapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chapter item = items.get(position);
        holder.binding.chapterName.setText(item.chapterName);
        if (item.updated != null && item.updated.length() > 0)
            holder.binding.updated.setText(item.updated);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnChapterItemClickListener {
        void onChapterItemClick(Chapter item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemChapterBinding binding;

        public MyViewHolder(@NonNull ItemChapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.chapterItemLayout.setOnClickListener(v ->
                    listener.onChapterItemClick(items.get(getAdapterPosition())));
        }
    }
}
