package et.press.ebook.ui.explore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import et.press.book.databinding.ItemSourceBinding;
import et.press.ebook.config.EpaSettings;
import et.press.ebook.models.DataSource;

import java.util.List;
import java.util.Locale;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.MyViewHolder> {
    private final List<DataSource> sources;
    private final OnSourceSelected listener;
    private final int currentSource;

    public SourceAdapter(List<DataSource> sources, OnSourceSelected listener) {
        this.sources = sources;
        this.listener = listener;
        this.currentSource = EpaSettings.get().getCurrentSource();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSourceBinding binding = ItemSourceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataSource source = sources.get(position);

        if (source.sourceId == currentSource) {
            holder.binding.active.setVisibility(View.VISIBLE);
        }

        holder.binding.sourceId.setText(String.valueOf(source.sourceId));
        holder.binding.sourceName.setText(source.name);
        holder.binding.sourceContent.setText(String.format(
                Locale.getDefault(),
                "%s • %s", source.lang
        ));
        Glide.with(holder.binding.sourceLogo.getContext())
                .load(source.logo)
                .into(holder.binding.sourceLogo);
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public interface OnSourceSelected {
        void select(DataSource source);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemSourceBinding binding;

        public MyViewHolder(@NonNull ItemSourceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.sourceLayout.setOnClickListener(v -> listener.select(sources.get(getAdapterPosition())));
        }
    }
}
